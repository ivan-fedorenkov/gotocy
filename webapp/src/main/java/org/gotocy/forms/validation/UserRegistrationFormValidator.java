package org.gotocy.forms.validation;

import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.UserRegistrationForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Validator of the {@link UserRegistrationForm}.
 * Unit test: UserRegistrationFormValidatorTest#testRejections
 *
 * @author ifedorenkov
 */
public class UserRegistrationFormValidator implements Validator {

	public static final UserRegistrationFormValidator INSTANCE = new UserRegistrationFormValidator();

	/**
	 * TODO: add to messages
	 */
	public static final String NON_MATCHING_PASSWORDS =
		"org.gotocy.validation.constraints.UserRegistrationFormValidator.NonMatchingPasswords.message";

	private UserRegistrationFormValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegistrationForm form = (UserRegistrationForm)  target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", form.getEmail());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", form.getPassword());

		if (!Objects.equals(form.getPassword(), form.getRepeatPassword())) {
			errors.rejectValue("password", NON_MATCHING_PASSWORDS);
			errors.rejectValue("repeatPassword", NON_MATCHING_PASSWORDS);
		}
	}
}
