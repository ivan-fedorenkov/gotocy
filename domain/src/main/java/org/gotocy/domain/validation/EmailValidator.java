package org.gotocy.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Validates email addresses.
 * Unit test: EmailValidatorTest
 *
 * @author ifedorenkov
 */
public class EmailValidator implements Validator {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
		Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private final String fieldName;

	public EmailValidator(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String email = (String) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, email);
		if (!errors.hasFieldErrors(fieldName) && !VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches())
			errors.rejectValue(fieldName, ValidationConstraints.INVALID_EMAIL);
	}

}
