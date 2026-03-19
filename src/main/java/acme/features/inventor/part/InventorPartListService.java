
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.realms.inventor.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Collection<Part>		parts;
	private Invention				invention;


	@Override
	public void load() {
		int inventionId;
		inventionId = super.getRequest().getData("inventionId", int.class);

		this.invention = this.repository.findInventionById(inventionId);
		this.parts = this.repository.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {

		boolean status;
		status = this.invention != null && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		// 1. Desvinculamos la lista de piezas como siempre
		super.unbindObjects(this.parts, "name", "cost", "kind", "description");

		showCreate = this.invention.getDraftMode();

		// Pasamos las variables globales a la vista
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("showCreate", showCreate);

	}
}
