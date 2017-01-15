package org.gotocy.forms.validation;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.forms.PropertySubmissionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the user property form.
 *
 * TODO: unit test
 *
 * @author ifedorenkov
 */
@Component
public class PropertySubmissionFormValidator implements Validator {

	private final ImagesSubmissionValidator imagesSubmissionValidator;

	@Autowired
	public PropertySubmissionFormValidator(ApplicationProperties applicationProperties) {
		imagesSubmissionValidator = new ImagesSubmissionValidator(
			applicationProperties.getPropertyImagesConstraintsForUser().getMaxFileCount(),
			applicationProperties.getPropertyImagesConstraintsForUser().getMaxFileSizeKb());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PropertySubmissionForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PropertySubmissionForm form = (PropertySubmissionForm) target;
//		PropertyValidator.INSTANCE.validate(form.mergeWithProperty(new Property()), errors);
		imagesSubmissionValidator.validate(form.getImages(), errors);
	}

}
