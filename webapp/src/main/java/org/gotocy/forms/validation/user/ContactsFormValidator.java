package org.gotocy.forms.validation.user;

import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.user.ContactsForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link org.gotocy.forms.user.ContactsForm}.
 *
 * @author ifedorenkov
 */
@Component
public class ContactsFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ContactsForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ContactsForm form = (ContactsForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", form.getEmail());
	}

}
