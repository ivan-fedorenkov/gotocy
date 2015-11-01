package org.gotocy.domain.validation;

import org.springframework.validation.Errors;

/**
 * Primitive int field validator. Special case of the {@link FieldValidator}.
 *
 * @author ifedorenkov
 */
public interface IntFieldValidator {

	/**
	 * Validates the given field's value.
	 */
	void validate(String field, int fieldValue, Errors errors);


}
