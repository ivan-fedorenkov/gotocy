package org.gotocy.forms.validation;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.UserRegistrationForm;
import org.gotocy.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
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
		UserService mockUserService = Mockito.mock(UserService.class);
		Mockito.when(mockUserService.findByUsername(Mockito.anyString())).thenReturn(null);
		UserRegistrationFormValidator formValidator = new UserRegistrationFormValidator(mockUserService);

		UserRegistrationForm form = new UserRegistrationForm();
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setName(EMPTY_STRING);
		form.setEmail(EMPTY_STRING);
		form.setPassword(EMPTY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_STRING);
		form.setPassword(ANY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(UserRegistrationFormValidator.NON_MATCHING_PASSWORDS,
			errors.getFieldError("password").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_STRING);
		form.setPassword(ANY_STRING);
		form.setConfirmPassword(ANY_STRING);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertFalse(errors.hasErrors());
	}

	@Test
	public void testRejectionOnExistingUser() {
		UserService mockUserService = Mockito.mock(UserService.class);
		Mockito.when(mockUserService.findByUsername(Mockito.anyString())).thenReturn(new GtcUser());
		UserRegistrationFormValidator formValidator = new UserRegistrationFormValidator(mockUserService);

		UserRegistrationForm form = new UserRegistrationForm();
		form.setName(ANY_STRING);
		form.setEmail(ANY_STRING);
		form.setPassword(ANY_STRING);
		form.setConfirmPassword(ANY_STRING);
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NON_UNIQUE, errors.getFieldError("email").getCode());
	}

}
