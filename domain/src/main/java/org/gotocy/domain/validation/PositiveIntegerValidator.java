package org.gotocy.domain.validation;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * Validates that an integer is greater or equal to zero.
 *
 * @author ifedorenkov
 */
public class PositiveIntegerValidator implements FieldValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Integer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(String field, Errors errors) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (value != null && ((Integer) value) < 0)
			errors.rejectValue(field, ValidationConstraints.NOT_NEGATIVE);
	}

}
