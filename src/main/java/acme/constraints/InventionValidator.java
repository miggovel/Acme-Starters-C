package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.invention.Invention;
import acme.features.inventor.invention.InventorInventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private InventorInventionRepository repository;


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;
		boolean isValid = true;

		if (invention == null)
			return true;

		{
			boolean uniqueInvention = this.isUniqueTicker(invention);
			super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
		}

		if (Boolean.FALSE.equals(invention.getDraftMode())) {
			{
				boolean hasInventionAtLeastOnePart = this.repository.countPartsByInventionId(invention.getId()) >= 1;
				super.state(context, hasInventionAtLeastOnePart, "*", "acme.validation.invention.parts.message");
			}

			{
				boolean startMomentIsPreviousToEndMoment = MomentHelper.isBefore(invention.getStartMoment(), invention.getEndMoment());
				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.invention.invalidMomentInterval.message");
			}

			isValid = !super.hasErrors(context);
		}

		return isValid;
	}

	private boolean isUniqueTicker(final Invention invention) {
		if (invention.getTicker() == null || invention.getTicker().isBlank())
			return true;

		try {
			Invention existing = this.repository.findOneByTicker(invention.getTicker());
			return existing == null || existing.equals(invention);
		} catch (final RuntimeException ex) {
			return false;
		}
	}

}
