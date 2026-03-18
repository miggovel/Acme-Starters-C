
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	r;
	private Collection<Invention>	invs;


	@Override
	public void load() {
		this.invs = this.r.findPublishedInvention();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true); // true porque lo puede ver todo el mundo -> hay que comprobar que no liste las que estan en borrador
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.invs, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "cost");
	}
}
