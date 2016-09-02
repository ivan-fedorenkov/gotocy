package org.gotocy.forms.validation;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.UserRegistrationForm;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

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
	public static final String INVALID_EMAIL =
		"org.gotocy.validation.constraints.UserRegistrationFormValidator.InvalidEmail.message";
	public static final String PASSWORD_TOO_SHORT =
		"org.gotocy.validation.constraints.UserRegistrationFormValidator.PasswordTooShort.message";

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
		Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final int MINIMUM_PASSWORD_LENGTH = 5;

	private final UserService userService;

	@Autowired
	public UserRegistrationFormValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegistrationForm form = (UserRegistrationForm)  target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", form.getEmail());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", form.getPassword());

		if (!errors.hasFieldErrors("email") && !VALID_EMAIL_ADDRESS_REGEX.matcher(form.getEmail()).matches())
			errors.rejectValue("email", INVALID_EMAIL);

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
