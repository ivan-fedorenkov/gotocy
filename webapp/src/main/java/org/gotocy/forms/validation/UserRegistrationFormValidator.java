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

		if (!Objects.equals(form.getPassword(), form.getConfirmPassword()))
			errors.rejectValue("password", NON_MATCHING_PASSWORDS);

		// Username must be unique
		GtcUser existingUser = userService.findByUsername(form.getEmail());
		if (existingUser != null)
			errors.rejectValue("email", ValidationConstraints.NON_UNIQUE, null);
	}
}
