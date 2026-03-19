
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		// Hecho: Se recupera el registro validando la propiedad mediante identificador compuesto.
		this.invention = this.repository.findOneByIdAndInventorId(id, inventorId);
	}

	@Override
	public void authorise() {
		// Hecho: La autorización requiere estrictamente que el objeto exista y sea un borrador.
		boolean status = this.invention != null && this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		Invention existing = this.repository.findOneByTicker(this.invention.getTicker());

		// Invariante 1: Unicidad del ticker
		boolean isUnique = existing == null || existing.getId() == this.invention.getId();
		super.state(isUnique, "ticker", "inventor.invention.form.error.duplicate-ticker");

		// Invariante 2: Cardinalidad mínima de componentes (|P| >= 1)
		int componentsCount = this.repository.countPartsByInventionId(this.invention.getId());
		boolean hasParts = componentsCount > 0;
		super.state(hasParts, "*", "inventor.invention.form.error.no-parts");

		// Invariante 3: Proyección temporal a futuro (t > t_0)
		boolean startIsFuture = acme.client.helpers.MomentHelper.isFuture(this.invention.getStartMoment());
		super.state(startIsFuture, "startMoment", "inventor.invention.form.error.start-moment-future");

		boolean endIsFuture = acme.client.helpers.MomentHelper.isFuture(this.invention.getEndMoment());
		super.state(endIsFuture, "endMoment", "inventor.invention.form.error.end-moment-future");
	}

	@Override
	public void execute() {
		// Transición de estado irreversible en el contexto de la aplicación
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
