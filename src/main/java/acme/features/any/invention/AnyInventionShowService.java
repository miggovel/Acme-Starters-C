
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	inventionRepository;

	private Invention				currentInvention;


	@Override
	public void load() {
		final int reqId = super.getRequest().getData("id", int.class);
		this.currentInvention = this.inventionRepository.findInventionById(reqId);
	}

	@Override
	public void authorise() {
		// Condición estricta: El invento debe existir y NO estar en modo borrador.
		final boolean isAvailable = this.currentInvention != null && !this.currentInvention.getDraftMode();
		super.setAuthorised(isAvailable);
	}

	@Override
	public void unbind() {
		final Tuple exportTuple = super.unbindObject(this.currentInvention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "cost", "monthsActive");
		exportTuple.put("inventorId", this.currentInvention.getInventor().getId());
	}

}
