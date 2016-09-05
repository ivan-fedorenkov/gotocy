package org.gotocy.forms.validation.user.profile;

import org.gotocy.domain.validation.EmailValidator;
import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.user.profile.ProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link ProfileForm}.
 *
 * @author ifedorenkov
 */
@Component
public class ProfileFormValidator implements Validator {

	private final EmailValidator emailValidator;

	public ProfileFormValidator() {
		emailValidator = new EmailValidator("email");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProfileForm form = (ProfileForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		emailValidator.validate(form.getEmail(), errors);
	}

}
