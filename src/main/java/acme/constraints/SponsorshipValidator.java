
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;

@Validator
@Component
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;

		if (sponsorship == null)
			return true;

		// Validación de intervalo de fechas en el futuro
		Date now = new Date();
		Date start = sponsorship.getStartMoment();
		Date end = sponsorship.getEndMoment();

		if (start != null)
			super.state(context, MomentHelper.isAfter(start, now), "startMoment", "acme.validation.sponsorship.start-moment.future");

		if (end != null)
			super.state(context, MomentHelper.isAfter(end, now), "endMoment", "acme.validation.sponsorship.end-moment.future");

		if (start != null && end != null)
			super.state(context, MomentHelper.isBefore(start, end), "startMoment", "acme.validation.sponsorship.start-before-end");

		// Validación de monthsActive
		double months = sponsorship.monthsActive();
		super.state(context, months >= 0, "startMoment", "acme.validation.sponsorship.months-active");

		//  Validación de dinero total (solo Euros)
		if (sponsorship.totalMoney() != null) {
			boolean isEuro = "EUR".equals(sponsorship.totalMoney().getCurrency());
			super.state(context, isEuro, "*", "acme.validation.sponsorship.only-euros");

			boolean hasAtLeastOneDonation = sponsorship.totalMoney().getAmount() > 0;
			super.state(context, hasAtLeastOneDonation, "*", "acme.validation.sponsorship.at-least-one-donation");
		}

		//  Resultado final
		return !super.hasErrors(context);
	}
}
