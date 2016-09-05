package org.gotocy.forms.validation.user.profile;

import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.user.profile.ProfileForm;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author ifedorenkov
 */
public class ProfileFormValidatorTest {

	private static final String FORM_OBJECT_NAME = "contactsForm";
	private static final String EMPTY_STRING = "";
	private static final String ANY_STRING = "any string";

	private static final String VALID_NAME = ANY_STRING;
	private static final String VALID_EMAIL = "support@gotocy.com";

	private static ProfileFormValidator validator;

	@BeforeClass
	public static void init() {
		validator = new ProfileFormValidator();
	}

	@Test
	public void testRejections() {
		ProfileForm form = new ProfileForm();
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		validator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());

		form.setName(EMPTY_STRING);
		form.setEmail(EMPTY_STRING);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		validator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_STRING);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		validator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.INVALID_EMAIL, errors.getFieldError("email").getCode());
	}

	@Test
	public void acceptValidForm() {
		ProfileForm form = new ProfileForm();
		form.setName(VALID_NAME);
		form.setEmail(VALID_EMAIL);
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		Assert.assertFalse(errors.hasErrors());
	}

}
