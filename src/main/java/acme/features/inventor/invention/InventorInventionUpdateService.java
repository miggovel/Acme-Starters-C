
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionUpdateService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		// Hecho: Se exige una consulta segura que garantice la propiedad del registro.
		this.invention = this.repository.findOneByIdAndInventorId(id, inventorId);
	}

	@Override
	public void authorise() {
		// Se autoriza estrictamente si el invento existe y permanece en modo borrador.
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

		// La condición de validez formal: nulo o idéntico identificador
		boolean isUnique = existing == null || existing.getId() == this.invention.getId();

		super.state(isUnique, "ticker", "inventor.invention.form.error.duplicate-ticker");
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
