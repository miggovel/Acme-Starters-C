
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		// Prevención de vulnerabilidades IDOR recuperando la entidad por identificador dual
		this.invention = this.repository.findOneByIdAndInventorId(id, inventorId);
	}

	@Override
	public void authorise() {
		boolean status = this.invention != null && this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		// Hecho: Las operaciones de eliminación no exigen validación de restricciones del modelo.
	}

	@Override
	public void execute() {
		// 1. Borrado en cascada de los componentes dependientes (piezas)
		Collection<Part> parts = this.repository.findPartsByInventionId(this.invention.getId());
		this.repository.deleteAll(parts);

		// 2. Destrucción de la entidad principal
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
