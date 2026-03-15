
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Collection<Sponsorship>		sponsorships;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		// Solo cargamos sponsorships publicados (draftMode = false)
		this.sponsorships = this.repository.findPublishedSponsorships();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		// Se incluyen los campos más importantes y el nombre del sponsor
		super.unbindObjects(this.sponsorships, "ticker", "name", "startMoment", "endMoment", "monthsActive", "totalMoney", "sponsor.identity.fullName");
	}

}
