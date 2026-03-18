
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(inventionId);
	}

	@Override
	public void authorise() {
		boolean auth = super.getRequest().getPrincipal().hasRealm(this.invention.getInventor());
		super.setAuthorised(auth);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "cost", "monthsActive");

	}

}
