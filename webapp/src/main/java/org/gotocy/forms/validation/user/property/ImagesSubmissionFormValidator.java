package org.gotocy.forms.validation.user.property;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Property;
import org.gotocy.forms.user.property.ImagesSubmissionForm;
import org.gotocy.forms.validation.ImagesSubmissionValidator;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link ImagesSubmissionForm}.
 *
 * @author ifedorenkov
 */
@Component
public class ImagesSubmissionFormValidator implements Validator {

	private final ApplicationProperties applicationProperties;
	private final PropertyService propertyService;

	@Autowired
	public ImagesSubmissionFormValidator(ApplicationProperties applicationProperties, PropertyService propertyService) {
		this.applicationProperties = applicationProperties;
		this.propertyService = propertyService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ImagesSubmissionForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GtcUser user = (GtcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Master is allowed to change anything
		if (user.isMaster())
			return;

		ImagesSubmissionForm form = (ImagesSubmissionForm) target;
		Property property = propertyService.findOne(form.getId());
		ImagesSubmissionValidator validator = new ImagesSubmissionValidator(
			getMaxFileCount(property, applicationProperties), getMaxFileSizeKb(applicationProperties));
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
