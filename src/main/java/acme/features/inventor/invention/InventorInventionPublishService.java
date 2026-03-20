package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.inventor.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();

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
		super.validateObject(this.invention);

		boolean isUnique = this.isUniqueTicker(this.invention.getTicker(), this.invention.getId());
		super.state(isUnique, "ticker", "inventor.invention.form.error.duplicate-ticker");

		int componentsCount = this.repository.countPartsByInventionId(this.invention.getId());
		boolean hasParts = componentsCount > 0;
		super.state(hasParts, "*", "inventor.invention.form.error.no-parts");

		boolean startIsFuture = MomentHelper.isFuture(this.invention.getStartMoment());
		super.state(startIsFuture, "startMoment", "inventor.invention.form.error.start-moment-future");

		boolean endIsFuture = MomentHelper.isFuture(this.invention.getEndMoment());
		super.state(endIsFuture, "endMoment", "inventor.invention.form.error.end-moment-future");

		boolean hasValidInterval = this.invention.getStartMoment() != null && this.invention.getEndMoment() != null && MomentHelper.isAfter(this.invention.getEndMoment(), this.invention.getStartMoment());
		super.state(hasValidInterval, "endMoment", "inventor.invention.form.error.invalid-interval");
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

	private boolean isUniqueTicker(final String ticker, final int currentId) {
		if (ticker == null || ticker.isBlank())
			return true;

		try {
			Invention existing = this.repository.findOneByTicker(ticker);
			return existing == null || existing.getId() == currentId;
		} catch (final RuntimeException ex) {
			return false;
		}
	}

}
