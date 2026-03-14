
package acme.features.any.spokesperson;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	// Internal state ----------------------------------------------------------
	private AnySpokespersonRepository	repository;

	private Spokesperson				spokesperson;

	// AbstractService interface ------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.spokesperson = this.repository.findSpokespersonById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.spokesperson != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "identity.fullName", "cv", "achievements", "licensed");
	}

}
