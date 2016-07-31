package org.gotocy.forms.validation.user;

import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.user.ContactsForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author ifedorenkov
 */
public class ContactsFormValidatorTest {

	private static final String FORM_OBJECT_NAME = "contactsForm";
	private static final String EMPTY_STRING = "";
	private static final String ANY_STRING = "any string";

	@Test
	public void testRejections() {
		ContactsFormValidator formValidator = new ContactsFormValidator();

		ContactsForm form = new ContactsForm();
		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());

		form.setName(EMPTY_STRING);
		form.setEmail(EMPTY_STRING);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("name").getCode());
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError("email").getCode());

		form.setName(ANY_STRING);
		form.setEmail(ANY_STRING);
		errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		formValidator.validate(form, errors);
		Assert.assertFalse(errors.hasErrors());
	}

}
