
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.entities.part.PartKind;
import acme.realms.inventor.Inventor;

@Service
public class InventorPartShowService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		// Se verifica la existencia y la propiedad del invento asociado
		status = this.part != null && this.part.getInvention().getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		// 1. Desvinculamos los atributos físicos hacia el objeto Tuple
		Tuple tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");

		// 2. Cargamos las opciones del enumerado PartKind
		choices = SelectChoices.from(PartKind.class, this.part.getKind());

		// 3. Inyectamos los metadatos necesarios para la lógica del form.jsp
		tuple.put("inventionId", this.part.getInvention().getId());
		tuple.put("draftMode", this.part.getInvention().getDraftMode());
		tuple.put("kinds", choices);
	}
}
