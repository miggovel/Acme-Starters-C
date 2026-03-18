
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;

public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	apr;
	private Part				part;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.part = this.apr.findPartById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
	}

}
