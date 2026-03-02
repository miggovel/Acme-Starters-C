
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

public class SponsorshipValidator implements ConstraintValidator<ValidSponsorship, Sponsorship> {

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		if (sponsorship == null)
			return true;

		boolean valid = true;

		context.disableDefaultConstraintViolation();

		//At least one donation
		if (sponsorship.getDonations() == null || sponsorship.getDonations().isEmpty()) {
			context.buildConstraintViolationWithTemplate("Sponsorship must have at least one donation").addPropertyNode("donations").addConstraintViolation();
			valid = false;
		}

		//startMoment and endMoment in future + valid interval
		Date now = new Date();

		Date start = sponsorship.getStartMoment();
		Date end = sponsorship.getEndMoment();

		if (start != null && start.before(now)) {
			context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		if (end != null && end.before(now)) {
			context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
			valid = false;
		}

		// start < end
		if (start != null && end != null && start.after(end)) {
			context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		//Only Euros accepted in donations
		if (sponsorship.getDonations() != null)
			for (Donation donation : sponsorship.getDonations())
				if (donation.getMoney() != null && !"EUR".equals(donation.getMoney().getCurrency())) {

					context.buildConstraintViolationWithTemplate("Only Euros are accepted").addPropertyNode("donations").addConstraintViolation();

					valid = false;
					break;
				}

		return valid;
	}
}
