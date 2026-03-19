
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;

public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state -----------------------------------------------------

	@Autowired
	private CampaignRepository repo;

	// ConstraintValidator interface --------------------------------------


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (campaign == null)
			result = true;
		else {
			{
				boolean uniqueCampaign;
				Campaign existingCampaign;

				existingCampaign = this.repo.findCampaignByTicker(campaign.getTicker());
				uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

				super.state(context, uniqueCampaign, "ticker", "acme.validation.campaign.duplicated-ticker.message");
			}
			{
				boolean correctTimeInterval;

				if (campaign.getStartMoment() != null && campaign.getEndMoment() != null) {
					correctTimeInterval = MomentHelper.isAfter(campaign.getEndMoment(), campaign.getStartMoment());
					super.state(context, correctTimeInterval, "endMoment", "acme.validation.campaign.timeInterval.message");
				}
			}

			// Publication constraints
			if (!campaign.getDraftMode()) {
				boolean hasMilestones;
				boolean validIntervalInFuture;

				hasMilestones = this.repo.countMilestonesByCampaignId(campaign.getId()) > 0;
				super.state(context, hasMilestones, "*", "acme.validation.campaign.publish.minimum-milestones.message");

				validIntervalInFuture = campaign.getStartMoment() != null && campaign.getEndMoment() != null && MomentHelper.isAfter(campaign.getEndMoment(), campaign.getStartMoment()) && MomentHelper.isFuture(campaign.getStartMoment())
					&& MomentHelper.isFuture(campaign.getEndMoment());
				super.state(context, validIntervalInFuture, "*", "acme.validation.campaign.publish.interval.message");

			}
			result = !super.hasErrors(context);
		}
		return result;

	}

}
