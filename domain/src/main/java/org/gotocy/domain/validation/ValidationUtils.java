package org.gotocy.domain.validation;

import org.springframework.validation.Errors;

/**
 * A number of utility methods responsible for fields validation.
 *
 * @author ifedorenkov
 */
public class ValidationUtils {

	private ValidationUtils() {
	}

	public static void rejectIfNull(Errors errors, String field, Object fieldValue) {
		if (fieldValue == null)
			errors.rejectValue(field, ValidationConstraints.NOT_EMPTY);
	}

	public static void rejectIfNegativeOrZero(Errors errors, String field, int fieldValue) {
		if (fieldValue <= 0)
			errors.rejectValue(field, ValidationConstraints.POSITIVE_INT);
	}

	public static void rejectIfEmptyOrWhitespace(Errors errors, String field, String fieldValue) {
		if (fieldValue == null || fieldValue.trim().isEmpty())
			errors.rejectValue(field, ValidationConstraints.NOT_EMPTY);
	}

}
