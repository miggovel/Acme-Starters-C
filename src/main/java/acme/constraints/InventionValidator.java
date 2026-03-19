
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

	// Inyección forzada de tu repositorio de actor
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

		// 1. Verificación de unicidad utilizando tu método: findOneByTicker
		{
			boolean uniqueInvention;
			Invention existingInvention;

			existingInvention = this.repository.findOneByTicker(invention.getTicker());
			uniqueInvention = existingInvention == null || existingInvention.equals(invention);

			super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
		}

		// 2. Verificaciones para inventos publicados
		if (Boolean.FALSE.equals(invention.getDraftMode())) {

			// Verificación de cardinalidad utilizando tu método: countPartsByInventionId
			{
				boolean hasInventionAtLeastOnePart;

				hasInventionAtLeastOnePart = this.repository.countPartsByInventionId(invention.getId()) >= 1;

				super.state(context, hasInventionAtLeastOnePart, "*", "acme.validation.invention.parts.message");
			}

			// Verificación temporal de fechas
			{
				boolean startMomentIsPreviousToEndMoment;

				startMomentIsPreviousToEndMoment = MomentHelper.isBefore(invention.getStartMoment(), invention.getEndMoment());

				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.invention.invalidMomentInterval.message");
			}
			isValid = !super.hasErrors(context);
		}

		return isValid;
	}
}
