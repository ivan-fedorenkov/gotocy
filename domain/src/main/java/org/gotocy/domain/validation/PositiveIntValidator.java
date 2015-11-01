package org.gotocy.domain.validation;

import org.springframework.validation.Errors;

/**
 * Validates that the given int value is greater then zero.
 *
 * @author ifedorenkov
 */
public class PositiveIntValidator implements IntFieldValidator {

	@Override
	public void validate(String field, int fieldValue, Errors errors) {
		if (fieldValue < 1)
			errors.rejectValue(field, ValidationConstraints.POSITIVE_INT);
	}

}
