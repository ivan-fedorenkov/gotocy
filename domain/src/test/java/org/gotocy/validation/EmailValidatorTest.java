package org.gotocy.validation;

import org.gotocy.domain.validation.EmailValidator;
import org.gotocy.domain.validation.ValidationConstraints;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

/**
 * @author ifedorenkov
 */
public class EmailValidatorTest {

	private static final String OBJECT_NAME = "object";
	private static final String FIELD_NAME = "email";

	private static EmailValidator emailValidator;

	@BeforeClass
	public static void init() {
		emailValidator = new EmailValidator(FIELD_NAME);
	}

	@Test
	public void shouldRejectNull() {
		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), OBJECT_NAME);
		emailValidator.validate(null, bindingResult);
		Assert.assertEquals(1, bindingResult.getFieldErrorCount(FIELD_NAME));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, bindingResult.getFieldError(FIELD_NAME).getCode());
	}

	@Test
	public void shouldRejectEmpty() {
		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), OBJECT_NAME);
		emailValidator.validate("", bindingResult);
		Assert.assertEquals(1, bindingResult.getFieldErrorCount(FIELD_NAME));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, bindingResult.getFieldError(FIELD_NAME).getCode());
	}

	@Test
	public void shouldRejectNonEmail() {
		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), OBJECT_NAME);
		emailValidator.validate("anything", bindingResult);
		Assert.assertEquals(1, bindingResult.getFieldErrorCount(FIELD_NAME));
		Assert.assertEquals(ValidationConstraints.INVALID_EMAIL, bindingResult.getFieldError(FIELD_NAME).getCode());
	}

	@Test
	public void shouldAcceptEmail() {
		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), OBJECT_NAME);
		emailValidator.validate("support@gotocy.com", bindingResult);
		Assert.assertFalse(bindingResult.hasFieldErrors(FIELD_NAME));
	}

}
