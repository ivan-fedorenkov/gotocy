package org.gotocy.service;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.gotocy.utils.ImageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Abstract base class for asset managers. Implements common logic.
 *
 * @author ifedorenkov
 */
public abstract class AbstractAssetsManager implements AssetsManager, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(AssetsManager.class);

	private final Executor executor = Executors.newSingleThreadExecutor();

	@Getter
	@Setter
	@Autowired
	private ApplicationContext applicationContext;

	private AssetsManager assetsManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.assetsManager = applicationContext.getBean(AssetsManager.class);
	}

	@Override
	public Optional<String> getPublicUrl(Image image, ImageSize size) {
		Optional<String> url = Optional.empty();
		Image sizedImage = image.getSized(size);
		if (assetsManager.exists(sizedImage)) {
			url = getPublicUrl(sizedImage);
		} else if (assetsManager.exists(image)) {

			executor.execute(() -> {
				Optional<Image> originalImage = getAsset(() -> image, image.getKey());

				if (originalImage.isPresent()) {
					Optional<Image> resizedImage = ImageConverter.convertToSize(originalImage.get(), size);
					// Successfully resized
					if (resizedImage.isPresent()) {
						try {
							saveAsset(resizedImage.get());
						} catch (IOException ioe) {
							logger.error("Failed to save resized image {}", resizedImage.get());
						}
					}
				}
			});

			url = getPublicUrl(image);
		}
		return url;
	}

	protected Logger getLogger() {
		return logger;
	}

}
