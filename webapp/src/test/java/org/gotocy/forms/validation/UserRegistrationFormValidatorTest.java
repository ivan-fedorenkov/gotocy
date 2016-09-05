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
	private static final String ANY_VALID_EMAIL = "anything@mail.xyz";
	private static final String INVALID_EMAIL = ANY_STRING;
	private static final String ANY_VALID_PASSWORD = "at_least_five_symbols";
	private static final String TOO_SHORT_PASSWORD = "1234";

	@Test
	public void testRejections() {
		UserService mockUserService = Mockito.mock(UserService.class);
		Mockito.when(mockUserService.findByUsername(Mockito.anyString())).thenReturn(null);
		UserRegistrationFormValidator formValidator = new UserRegistrationFormValidator(mockUserService);

		UserRegistrationForm form = new UserRegistrationForm();
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(1, errors.getFieldErrorCount("name"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(1, errors.getFieldErrorCount("email"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(1, errors.getFieldErrorCount("password"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setName(EMPTY_STRING);
		form.setEmail(EMPTY_STRING);
		form.setPassword(EMPTY_STRING);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(1, errors.getFieldErrorCount("name"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(1, errors.getFieldErrorCount("email"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());
		Assert.assertEquals(1, errors.getFieldErrorCount("password"));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("password").getCode());

		form.setName(ANY_STRING);
		form.setEmail(INVALID_EMAIL);
		form.setPassword(TOO_SHORT_PASSWORD);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(1, errors.getFieldErrorCount("email"));
		Assert.assertEquals(ValidationConstraints.INVALID_EMAIL, errors.getFieldError("email").getCode());
		Assert.assertEquals(1, errors.getFieldErrorCount("password"));
		Assert.assertEquals(UserRegistrationFormValidator.PASSWORD_TOO_SHORT,
			errors.getFieldError("password").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_VALID_EMAIL);
		form.setPassword(ANY_VALID_PASSWORD);
		errors = new BeanPropertyBindingResult(form,  FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(1, errors.getFieldErrorCount("password"));
		Assert.assertEquals(UserRegistrationFormValidator.NON_MATCHING_PASSWORDS,
			errors.getFieldError("password").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_VALID_EMAIL);
		form.setPassword(ANY_VALID_PASSWORD);
		form.setConfirmPassword(ANY_VALID_PASSWORD);
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
		form.setEmail(ANY_VALID_EMAIL);
		form.setPassword(ANY_VALID_PASSWORD);
		form.setConfirmPassword(ANY_VALID_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NON_UNIQUE, errors.getFieldError("email").getCode());
	}

}
