
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.invention = this.repository.findOneByIdAndInventorId(id, inventorId);

	}

	@Override
	public void authorise() {
		boolean auth = this.invention != null && this.invention.getDraftMode();
		super.setAuthorised(auth);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

		super.validateObject(this.invention);

		Invention existing = this.repository.findOneByTicker(this.invention.getTicker());

		boolean esUnico = existing == null || existing.getId() == this.invention.getId();
		super.state(esUnico, "ticker", "inventor.invention.form.error.duplicate-ticker");

		boolean masDeUnComponente = this.repository.countPartsByInventionId(this.invention.getId()) > 0;
		super.state(masDeUnComponente, "*", "inventor.invention.form.error.no-parts");

	}

	@Override
	public void execute() {

		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
