package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MessageHelper;
import acme.client.helpers.StringHelper;

@Validator
public class ValidTickerValidator extends AbstractValidator<ValidTicker, String> {

	private static final String TICKER_PATTERN = "^[A-Z]{2}[0-9]{2}-\\w{5,10}$";

	@Override
	protected void initialise(final ValidTicker annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;
		String message;

		result = value != null && !StringHelper.isBlank(value) && StringHelper.matches(value, TICKER_PATTERN);

		if (!result) {
			message = MessageHelper.getMessage("acme.validation.pattern.message", TICKER_PATTERN);
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		return result;
	}

}
