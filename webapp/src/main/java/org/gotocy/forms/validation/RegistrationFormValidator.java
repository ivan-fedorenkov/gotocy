package org.gotocy.forms.validation;

import org.gotocy.domain.validation.ValidationUtils;
import org.gotocy.forms.RegistrationForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link RegistrationForm}.
 *
 * @author ifedorenkov
 */
public class RegistrationFormValidator implements Validator {

	public static final RegistrationFormValidator INSTANCE = new RegistrationFormValidator();

	private RegistrationFormValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationForm form = (RegistrationForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", form.getName());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", form.getEmail());
		ValidationUtils.rejectIfNull(errors, "businessForm", form.getPropertiesCount());
		ValidationUtils.rejectIfNegativeOrZero(errors, "propertiesCount", form.getPropertiesCount());
	}

}
