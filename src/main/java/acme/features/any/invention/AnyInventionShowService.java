
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	r;
	private Invention				inv;


	@Override
	public void load() { //carga de datos de la base de datos - query
		int id;
		id = super.getRequest().getData("id", int.class);
		this.inv = this.r.findInventionById(id);
	}

	@Override
	public void authorise() {// salto de la plaquita de que no se puede

		boolean check;

		check = this.inv != null && !this.inv.getDraftMode();
		super.setAuthorised(check);

	}

	@Override
	public void unbind() {
		Tuple invention = super.unbindObject(this.inv, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "cost");
		invention.put("inventorID", this.inv.getInventor().getId());
	}

}
