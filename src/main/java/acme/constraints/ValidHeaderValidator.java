package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MessageHelper;
import acme.client.helpers.StringHelper;

@Validator
public class ValidHeaderValidator extends AbstractValidator<ValidHeader, String> {

	@Override
	protected void initialise(final ValidHeader annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;
		String message;

		result = value != null && !StringHelper.isBlank(value) && value.length() >= 1 && value.length() <= 75;

		if (!result) {
			message = MessageHelper.getMessage("acme.validation.length.message", 1, 75);
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		return result;
	}

}
