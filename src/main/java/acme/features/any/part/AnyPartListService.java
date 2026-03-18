
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	apr;
	private Collection<Part>	ps;
	private Invention			invention;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("invetionId", int.class);

		this.invention = this.apr.findInventionById(inventionId);
		this.ps = this.apr.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.ps, "name", "cost", "kind", "description");
	}
}
