package org.gotocy.forms.validation;

import org.gotocy.domain.Property;
import org.gotocy.domain.validation.PropertyValidator;
import org.gotocy.forms.UserPropertyForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the user property form. Basically it just delegates all the validation logic
 * to the {@link org.gotocy.domain.validation.PropertyValidator}.
 * Singleton, thread safe.
 *
 * @author ifedorenkov
 */
public class UserPropertyFormValidator implements Validator {

	public static final UserPropertyFormValidator INSTANCE = new UserPropertyFormValidator();

	private UserPropertyFormValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPropertyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserPropertyForm form = (UserPropertyForm) target;
		PropertyValidator.INSTANCE.validate(form.mergeWithProperty(new Property()), errors);
	}

}
