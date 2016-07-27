package org.gotocy.forms.validation;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.Property;
import org.gotocy.domain.validation.PropertyValidator;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.UserPropertyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Validator of the user property form. Basically it just delegates all the validation logic
 * to the {@link org.gotocy.domain.validation.PropertyValidator}.
 *
 * TODO: unit test
 *
 * @author ifedorenkov
 */
@Component
public class UserPropertyFormValidator implements Validator {

	public static final String ALLOWED_IMAGE_CONTENT_TYPE = "image/jpeg";
	public static final String ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY = "jpeg";

	private final int maxAllowedImages;
	private final int maxAllowedFileSize;
	private final int maxAllowedFileSizeMb;

	@Autowired
	public UserPropertyFormValidator(ApplicationProperties applicationProperties) {
		maxAllowedImages = applicationProperties.getUserPropertyForm().getMaxFileCount();
		maxAllowedFileSize = applicationProperties.getUserPropertyForm().getMaxFileSize() * 1024; // bytes
		maxAllowedFileSizeMb = applicationProperties.getUserPropertyForm().getMaxFileSize() / 1024; // mbytes
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPropertyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserPropertyForm form = (UserPropertyForm) target;
		PropertyValidator.INSTANCE.validate(form.mergeWithProperty(new Property()), errors);

		List<MultipartFile> images = form.getImages();
		if (!images.isEmpty()) {
			if (images.size() > maxAllowedImages)
				errors.rejectValue("images", ValidationConstraints.MAX_SIZE, new Object[]{maxAllowedImages}, null);
			for (MultipartFile image : images) {
				if (!ALLOWED_IMAGE_CONTENT_TYPE.equalsIgnoreCase(image.getContentType())) {
					// if any of images violates the supported content type then reject all of them
					errors.rejectValue("images", ValidationConstraints.CONTENT_TYPE,
						new Object[]{ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY}, null);
					break;
				}

				if (image.getSize() > maxAllowedFileSize) {
					// if any of images exceeds the maximum size then reject all of them
					errors.rejectValue("images", ValidationConstraints.MAX, new Object[]{maxAllowedFileSizeMb}, null);
					break;
				}
			}
		}
	}

}
