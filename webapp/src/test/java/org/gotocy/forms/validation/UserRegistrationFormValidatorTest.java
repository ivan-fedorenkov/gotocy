package org.gotocy.forms.validation;

import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.UserRegistrationForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author ifedorenkov
 */
public class UserRegistrationFormValidatorTest {

	private static final String FORM_OBJECT_NAME = "userRegistrationForm";
	private static final String EMPTY_STRING = "";
	private static final String ANY_STRING = "any string";

	@Test
	public void testRejections() {
		UserRegistrationForm form = new UserRegistrationForm();
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		UserRegistrationFormValidator.INSTANCE.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setEmail(EMPTY_STRING);
		form.setPassword(EMPTY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		UserRegistrationFormValidator.INSTANCE.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setEmail(ANY_STRING);
		form.setPassword(ANY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		UserRegistrationFormValidator.INSTANCE.validate(form, errors);
		Assert.assertEquals(UserRegistrationFormValidator.NON_MATCHING_PASSWORDS,
			errors.getFieldError("password").getCode());
		Assert.assertEquals(UserRegistrationFormValidator.NON_MATCHING_PASSWORDS,
			errors.getFieldError("repeatPassword").getCode());

		form.setEmail(ANY_STRING);
		form.setPassword(ANY_STRING);
		form.setRepeatPassword(ANY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		UserRegistrationFormValidator.INSTANCE.validate(form, errors);
		Assert.assertFalse(errors.hasErrors());

	}

}
