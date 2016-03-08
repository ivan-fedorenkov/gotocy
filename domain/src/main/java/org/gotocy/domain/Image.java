package org.gotocy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("image")
public class Image extends Asset {

	public static final String RESIZED_IMAGE_KEY_PREFIX = "resized_images";

	public Image() {
	}

	public Image(String key) {
		super(key);
	}

	@Override
	public String getContentType() {
		// TODO: image could be in different format, currently we support only jpeg images
		return "image/jpeg";
	}

	/**
	 * Returns an image asset instance sized to the given {@link ImageSize}.
	 */
	public Image getSized(ImageSize size) {
		int nextToLastSlash = getKey().lastIndexOf('/') + 1;
		return new Image(RESIZED_IMAGE_KEY_PREFIX + "/" + getKey().substring(0, nextToLastSlash) + size.name() +
			"/" + getKey().substring(nextToLastSlash));
	}

}
