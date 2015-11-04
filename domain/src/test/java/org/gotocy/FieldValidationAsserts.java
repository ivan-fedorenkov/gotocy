package org.gotocy;

import org.gotocy.domain.validation.ValidationConstraints;
import org.junit.Assert;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;

import java.util.function.Function;

/**
 * Common field validation asserts.
 *
 * @author ifedorenkov
 */
public class FieldValidationAsserts {

	private static final String ANY_STRING = "any_string";
	private static final Integer POSITIVE_INT = 1;
	private static final Integer ANY_INT = 1;
	private static final Integer ZERO = 0;
	private static final Integer NEGATIVE_INT = -1;
	private static final Double ANY_DOUBLE = 1D;
	private static final Boolean ANY_BOOLEAN = Boolean.FALSE;

	public static <T> void shouldNotBeNull(T entity, String field, Function<T, Errors> validator) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);

		// Bad case
		wrapper.setPropertyValue(field, null);
		assertHasNotEmptyFieldError(validator.apply(entity), field);
	}

	public static <T> void shouldBePositiveInt(T entity, String field, Function<T, Errors> validator) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);

		// Bad cases
		wrapper.setPropertyValue(field, ZERO);
		assertHasPositiveIntFieldError(validator.apply(entity), field);
		wrapper.setPropertyValue(field, NEGATIVE_INT);
		assertHasPositiveIntFieldError(validator.apply(entity), field);

		// Good case
		wrapper.setPropertyValue(field, POSITIVE_INT);
		assertHasNoFieldError(validator.apply(entity), field);
	}

	public static <T> void shouldNotBeEmptyOrWhitespace(T entity, String field, Function<T, Errors> validator) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);

		// Bad cases
		wrapper.setPropertyValue(field, null);
		assertHasNotEmptyFieldError(validator.apply(entity), field);
		wrapper.setPropertyValue(field, "");
		assertHasNotEmptyFieldError(validator.apply(entity), field);
		wrapper.setPropertyValue(field, " ");
		assertHasNotEmptyFieldError(validator.apply(entity), field);

		// Good case
		wrapper.setPropertyValue(field, ANY_STRING);
		assertHasNoFieldError(validator.apply(entity), field);
	}

	private static void assertHasNotEmptyFieldError(Errors errors, String field) {
		Assert.assertTrue(errors.hasFieldErrors(field));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError(field).getCode());
	}

	private static void assertHasPositiveIntFieldError(Errors errors, String field) {
		Assert.assertTrue(errors.hasFieldErrors(field));
		Assert.assertEquals(ValidationConstraints.POSITIVE_INT, errors.getFieldError(field).getCode());
	}

	private static void assertHasNoFieldError(Errors errors, String field) {
		Assert.assertFalse(errors.hasFieldErrors(field));
	}

}
