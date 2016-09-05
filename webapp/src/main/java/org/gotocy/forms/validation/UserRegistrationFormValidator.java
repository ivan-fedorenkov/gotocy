package org.gotocy.forms.validation;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.validation.EmailValidator;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.UserRegistrationForm;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Validator of the {@link UserRegistrationForm}.
 * Unit test: UserRegistrationFormValidatorTest#testRejections
 *
 * @author ifedorenkov
 */
@Component
public class UserRegistrationFormValidator implements Validator {

	public static final String NON_MATCHING_PASSWORDS =
		"org.gotocy.validation.constraints.UserRegistrationFormValidator.NonMatchingPasswords.message";
	public static final String PASSWORD_TOO_SHORT =
		"org.gotocy.validation.constraints.UserRegistrationFormValidator.PasswordTooShort.message";

	private static final int MINIMUM_PASSWORD_LENGTH = 5;

	private final UserService userService;
	private final EmailValidator emailValidator;

	@Autowired
	public UserRegistrationFormValidator(UserService userService) {
		this.userService = userService;
		emailValidator = new EmailValidator("email");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegistrationForm form = (UserRegistrationForm)  target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", form.getPassword());

		emailValidator.validate(form.getEmail(), errors);

		if (!errors.hasFieldErrors("password") && form.getPassword().length() < MINIMUM_PASSWORD_LENGTH)
			errors.rejectValue("password", PASSWORD_TOO_SHORT, new Object[] {MINIMUM_PASSWORD_LENGTH}, null);

		if (!errors.hasFieldErrors("password") && !Objects.equals(form.getPassword(), form.getConfirmPassword()))
			errors.rejectValue("password", NON_MATCHING_PASSWORDS);

		if (!errors.hasErrors()) {
			// Username must be unique
			GtcUser existingUser = userService.findByUsername(form.getEmail());
			if (existingUser != null)
				errors.rejectValue("email", ValidationConstraints.NON_UNIQUE, null);
		}
	}
}
