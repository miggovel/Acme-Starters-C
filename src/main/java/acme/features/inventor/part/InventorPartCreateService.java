
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.entities.part.PartKind;
import acme.realms.inventor.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Invention invention = this.repository.findInventionByIdAndInventorId(inventionId, inventorId);

		this.part = super.newObject(Part.class);
		this.part.setInvention(invention);
		this.part.setKind(PartKind.CORE);
	}

	@Override
	public void authorise() {
		boolean status = this.part.getInvention() != null && this.part.getInvention().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		// 1. Evaluación estática del modelo
		super.validateObject(this.part);

		// 2. Aserción dinámica de la divisa
		if (this.part.getCost() != null) {
			boolean isValidCurrency = "EUR".equals(this.part.getCost().getCurrency());
			super.state(isValidCurrency, "cost", "inventor.part.form.error.invalid-currency");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		choices = SelectChoices.from(PartKind.class, this.part.getKind());
		tuple.put("inventionId", this.part.getInvention().getId());
		tuple.put("draftMode", this.part.getInvention().getDraftMode());
		tuple.put("kinds", choices);
	}
}
