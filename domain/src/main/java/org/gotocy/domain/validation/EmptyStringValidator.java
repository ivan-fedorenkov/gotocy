package org.gotocy.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validates that a string is not null or empty and contains at least one non whitespace character.
 *
 * @author ifedorenkov
 */
public class EmptyStringValidator implements FieldValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(String field, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, ValidationConstraints.NOT_EMPTY);
	}

}
