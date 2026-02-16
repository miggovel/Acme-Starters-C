
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.entities.inventions.Invention;

public class InventionValidator implements ConstraintValidator<ValidInvention, Invention> {

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {

		if (invention == null)
			return true;

		boolean valid = true;

		// Comprobar que tenga al menos una sección
		// quitar lo de la tactica 
		if (invention.getParts().isEmpty()) {
			context.buildConstraintViolationWithTemplate("Invention must have at least one part").addPropertyNode("parts").addConstraintViolation();
			valid = false;
		}

		// Comprobar que startMoment < endMoment y ambos en el futuro
		Date now = new Date(); // momento actual
		Date start = invention.getStartMoment();
		Date end = invention.getEndMoment();

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

		return valid;

	}
}
