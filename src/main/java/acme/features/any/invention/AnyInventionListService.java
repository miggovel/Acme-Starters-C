
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyInventionRepository	inventionRepository;

	private Collection<Invention>	publishedInventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.publishedInventions = this.inventionRepository.findPublishedInvention();
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.publishedInventions, //
			"ticker", "name", "description", //
			"startMoment", "endMoment", "moreInfo", "cost");
	}

}
