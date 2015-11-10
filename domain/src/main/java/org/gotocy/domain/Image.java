package org.gotocy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("image")
public class Image extends Asset {

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
	 * Returns an image asset key for the given image size.
	 */
	public String getKeyForSize(ImageSize imageSize) {
		int nextToLastSlash = getKey().lastIndexOf('/') + 1;
		return getKey().substring(0, nextToLastSlash) + imageSize.name() + '/' + getKey().substring(nextToLastSlash);
	}

}
