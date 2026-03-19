
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.part.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	partRepository;

	private Part				targetPart;


	@Override
	public void load() {
		final int partId = super.getRequest().getData("id", int.class);
		this.targetPart = this.partRepository.findPartById(partId);
	}

	@Override
	public void authorise() {
		// Verificamos que exista y que el invento al que pertenece no sea un borrador
		final boolean conditionMet = this.targetPart != null && !this.targetPart.getInvention().getDraftMode();
		super.setAuthorised(conditionMet);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.targetPart, "name", "description", "cost", "kind");
	}
}
