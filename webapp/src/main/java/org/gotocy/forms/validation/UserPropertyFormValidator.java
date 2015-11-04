package org.gotocy.forms.validation;

import org.gotocy.domain.Property;
import org.gotocy.domain.validation.PropertyValidator;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.UserPropertyForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Validator of the user property form. Basically it just delegates all the validation logic
 * to the {@link org.gotocy.domain.validation.PropertyValidator}.
 * Singleton, thread safe.
 *
 * TODO: unit test
 *
 * @author ifedorenkov
 */
public class UserPropertyFormValidator implements Validator {

	public static final UserPropertyFormValidator INSTANCE = new UserPropertyFormValidator();

	public static final int MAX_ALLOWED_IMAGES = 10;
	public static final int MAX_ALLOWED_SIZE_MB = 3;
	public static final long MAX_ALLOWED_SIZE = MAX_ALLOWED_SIZE_MB * 1024 * 1024; // in bytes

	public static final String ALLOWED_IMAGE_CONTENT_TYPE = "image/jpeg";
	public static final String ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY = "jpeg";


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

		List<MultipartFile> images = form.getImages();
		if (!images.isEmpty()) {
			if (images.size() > MAX_ALLOWED_IMAGES)
				errors.rejectValue("images", ValidationConstraints.MAX_SIZE, new Object[]{MAX_ALLOWED_IMAGES}, null);
			for (MultipartFile image : images) {
				if (!ALLOWED_IMAGE_CONTENT_TYPE.equalsIgnoreCase(image.getContentType())) {
					// if any of images violates the supported content type then reject all of them
					errors.rejectValue("images", ValidationConstraints.CONTENT_TYPE,
						new Object[]{ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY}, null);
					break;
				}

				if (image.getSize() > MAX_ALLOWED_SIZE) {
					// if any of images exceeds the maximum size then reject all of them
					errors.rejectValue("images", ValidationConstraints.MAX, new Object[]{MAX_ALLOWED_SIZE_MB}, null);
					break;
				}
			}
		}
	}

}
