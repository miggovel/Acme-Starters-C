
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionCreateService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		Inventor inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();

		this.invention = super.newObject(Invention.class);
		this.invention.setDraftMode(true); // Siempre nace como borrador
		this.invention.setInventor(inventor);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		Invention existing = this.repository.findOneByTicker(this.invention.getTicker());

		// La condición de validez formal para creación: conjunto vacío estricto
		boolean isUnique = existing == null;

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
