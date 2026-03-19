
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	partRepository;

	private Collection<Part>	partList;
	private Invention			parentInvention;


	@Override
	public void load() {
		final int parentId = super.getRequest().getData("inventionId", int.class);

		this.parentInvention = this.partRepository.findInventionById(parentId);
		this.partList = this.partRepository.findPartsByInventionId(parentId);
	}

	@Override
	public void authorise() {
		// Solo autorizamos si el informe existe y YA ESTÁ PUBLICADO (no es borrador)
		final boolean isReady = this.parentInvention != null && !this.parentInvention.getDraftMode();
		super.setAuthorised(isReady);
	}
	@Override
	public void unbind() {
		super.unbindObjects(this.partList, "name", "cost", "kind", "description");
	}
}
