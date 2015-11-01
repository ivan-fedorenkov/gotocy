package org.gotocy.domain.validation;

import org.springframework.validation.Errors;

import java.util.Optional;

/**
 * Base interface for field validators.
 *
 * @author ifedorenkov
 */
public interface FieldValidator {

	/**
	 * Returns <code>true</code> if the given class is supported by the validator implementation.
	 */
	boolean supports(Class<?> clazz);

	/**
	 * Validates the given field's value.
	 */
	void validate(String field, Object fieldValue, Errors errors);

}
