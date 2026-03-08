
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.internal.util.StringHelper;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MessageHelper;

@Validator
public class ValidTextValidator extends AbstractValidator<ValidText, String> {

	@Override
	protected void initialise(final ValidText annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;
		String message;

		result = value != null && !StringHelper.isBlank(value) && value.length() >= 1 && value.length() <= 255;

		if (!result) {
			message = MessageHelper.getMessage("acme.validation.length.message", 1, 255);
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		return result;
	}

}
