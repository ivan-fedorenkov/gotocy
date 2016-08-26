package org.gotocy.forms.validation.user.property;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Property;
import org.gotocy.forms.user.property.ImagesSubmissionForm;
import org.gotocy.forms.validation.ImagesSubmissionValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link ImagesSubmissionForm}.
 *
 * @author ifedorenkov
 */
public class ImagesSubmissionFormValidator implements Validator {

	private static final ImagesSubmissionValidator MASTER_FORM_VALIDATOR =
		new ImagesSubmissionValidator(Integer.MAX_VALUE, Integer.MAX_VALUE);

	private final ImagesSubmissionValidator validator;

	public ImagesSubmissionFormValidator(GtcUser user, Property property, ApplicationProperties applicationProperties) {
		validator = user.isMaster() ? MASTER_FORM_VALIDATOR :
			new ImagesSubmissionValidator(getMaxFileCount(property, applicationProperties),
				getMaxFileSizeKb(applicationProperties));

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ImagesSubmissionForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		validator.validate(((ImagesSubmissionForm) target).getImages(), errors);
	}

	private static int getMaxFileCount(Property property, ApplicationProperties applicationProperties) {
		int totalAllowed = applicationProperties.getPropertyImagesConstraintsForUser().getMaxFileCount();
		int currentCount = property.getImages().size();
		return currentCount >= totalAllowed ? 0 : totalAllowed - currentCount;
	}

	private static int getMaxFileSizeKb(ApplicationProperties applicationProperties) {
		return applicationProperties.getPropertyImagesConstraintsForUser().getMaxFileSizeKb();
	}

}
