
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;
	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();

	}

	@Override
	public void authorise() {
		boolean status = this.invention != null && this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void execute() {
		Collection<Part> parts = this.repository.findPartsByInventionId(this.invention.getId());
		this.repository.deleteAll(parts);
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
