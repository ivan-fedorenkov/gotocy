package org.gotocy.forms.validation.user.profile;

import org.gotocy.domain.validation.EmailValidator;
import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.user.profile.ContactsForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link ContactsForm}.
 *
 * @author ifedorenkov
 */
@Component
public class ContactsFormValidator implements Validator {

	private final EmailValidator emailValidator;

	public ContactsFormValidator() {
		emailValidator = new EmailValidator("email");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ContactsForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ContactsForm form = (ContactsForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		emailValidator.validate(form.getEmail(), errors);
	}

}
