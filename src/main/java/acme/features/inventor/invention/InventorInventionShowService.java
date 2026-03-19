
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	//	@Override
	//	public void load() {
	//		int inventionId;
	//
	//		inventionId = super.getRequest().getData("id", int.class);
	//		this.invention = this.repository.findInventionById(inventionId);
	//	}
	//
	//	@Override
	//	public void authorise() {
	//		boolean status;
	//
	//		status = super.getRequest().getPrincipal().hasRealm(this.invention.getInventor());
	//
	//		super.setAuthorised(status);
	//	}
	@Override
	public void load() {
		// Protección contra ausencia de parámetro o inyección léxica en la URL
		try {
			if (super.getRequest().hasData("id", int.class)) {
				int inventionId = super.getRequest().getData("id", int.class);
				this.invention = this.repository.findInventionById(inventionId);
			}
		} catch (final Exception oops) {
			// El estado permanece inmutable: this.invention = null
		}
	}

	@Override
	public void authorise() {
		boolean status;

		// Cortocircuito lógico: Si this.invention es nulo, no se evalúa getInventor() 
		// y status computa automáticamente como false, disparando el Intrusion Alert.
		status = this.invention != null && super.getRequest().getPrincipal().hasRealm(this.invention.getInventor());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "cost", "monthsActive");
		//		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "cost");

	}

	//	@Override
	//	public void unbind() {
	//		Tuple tuple;
	//		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "cost");
	//	}

}
