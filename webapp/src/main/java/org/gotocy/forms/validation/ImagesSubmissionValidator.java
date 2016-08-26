package org.gotocy.forms.validation;

import org.gotocy.domain.validation.ValidationConstraints;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Validator of images submission. Can restrict max number, max size and allowed content type.
 *
 * @author ifedorenkov
 */
public class ImagesSubmissionValidator implements Validator {

	private static final String ALLOWED_IMAGE_CONTENT_TYPE = "image/jpeg";
	private static final String ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY = "jpeg";
	private static final String FIELD_NAME = "images";

	private final int maxAllowedImages;
	private final long maxAllowedFileSize;
	private final long maxAllowedFileSizeMb;

	public ImagesSubmissionValidator(int maxAllowedImages, int maxAllowedFileSizeKb) {
		this.maxAllowedImages = maxAllowedImages;
		maxAllowedFileSize = maxAllowedFileSizeKb * 1024L; // bytes
		maxAllowedFileSizeMb = maxAllowedFileSizeKb / 1024L; // mbytes
	}

	@Override
	public boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException("Must be used explicitly as an internal sub-validator.");
	}

	@Override
	public void validate(Object target, Errors errors) {
		List<MultipartFile> images = (List<MultipartFile>) target;
		if (!images.isEmpty()) {
			if (maxAllowedImages == 0) {
				errors.rejectValue(FIELD_NAME, ValidationConstraints.REACHED_LIMIT);
			} else if (images.size() > maxAllowedImages) {
				errors.rejectValue(FIELD_NAME, ValidationConstraints.MAX_SIZE, new Object[]{maxAllowedImages}, null);
			}
			for (MultipartFile image : images) {
				if (!ALLOWED_IMAGE_CONTENT_TYPE.equalsIgnoreCase(image.getContentType())) {
					// if any of images violates the supported content type then reject all of them
					errors.rejectValue(FIELD_NAME, ValidationConstraints.CONTENT_TYPE,
						new Object[]{ALLOWED_IMAGE_CONTENT_TYPE_USER_FRIENDLY}, null);
					break;
				}

				if (image.getSize() > maxAllowedFileSize) {
					// if any of images exceeds the maximum size then reject all of them
					errors.rejectValue(FIELD_NAME, ValidationConstraints.MAX, new Object[]{maxAllowedFileSizeMb}, null);
					break;
				}
			}
		}
	}

}
