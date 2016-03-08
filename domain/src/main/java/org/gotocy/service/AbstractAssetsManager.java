package org.gotocy.service;

import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.gotocy.utils.ImageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Abstract base class for asset managers. Implements common logic.
 *
 * @author ifedorenkov
 */
public abstract class AbstractAssetsManager implements AssetsManager {

	private static final Logger logger = LoggerFactory.getLogger(AssetsManager.class);

	@Override
	public Optional<String> getPublicUrl(Image image, ImageSize size) {
		Optional<String> url = Optional.empty();
		Image sizedImage = image.getSized(size);
		if (exists(sizedImage)) {
			url = getPublicUrl(sizedImage);
		} else if (exists(image)) {
			Optional<Image> originalImage = getAsset(() -> image, image.getKey());
			if (originalImage.isPresent()) {
				Optional<Image> resizedImage = ImageConverter.convertToSize(originalImage.get(), size);
				// Successfully resized
				if (resizedImage.isPresent()) {
					try {
						saveAsset(resizedImage.get());
						url = getPublicUrl(resizedImage.get());
					} catch (IOException ioe) {
						logger.error("Failed to save resized image {}", resizedImage.get());
					}
				}
			}

			// Last chance to generate public url if resize failed for some reason
			if (!url.isPresent())
				url = getPublicUrl(image);
		}
		return url;
	}

	protected Logger getLogger() {
		return logger;
	}

}
