package org.gotocy.domain.validation;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * Validates that a field is not null.
 *
 * @author ifedorenkov
 */
public class NullValidator implements FieldValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(String field, Errors errors) {
		Assert.notNull(errors, "Errors object must not be null");
		if (errors.getFieldValue(field) == null)
			errors.rejectValue(field, ValidationConstraints.NOT_EMPTY);
	}
}
