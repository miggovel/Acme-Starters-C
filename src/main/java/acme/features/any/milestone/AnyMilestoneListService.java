
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyMilestoneRepository	repository;

	private Campaign				campaign;
	private Collection<Milestone>	milestone;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestone = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		int campaignId;
		Campaign campaign;
		boolean status;

		campaignId = super.getRequest().getData("campaignId", int.class);
		campaign = this.repository.findCampaignById(campaignId);

		status = campaign != null && !campaign.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestone, "title", "effort", "kind", "achievements");
		super.unbindGlobal("campaignId", this.campaign.getId());
	}

}
