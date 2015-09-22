package org.gotocy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("image")
public class Image extends Asset<byte[]> {

	public Image() {
	}

	public Image(String key) {
		super(key);
	}

	/**
	 * Returns an image asset key for the given image size.
	 */
	public String getKeyForSize(ImageSize imageSize) {
		int nextToLastSlash = getKey().lastIndexOf('/') + 1;
		return getKey().substring(0, nextToLastSlash) + imageSize.name() + '/' + getKey().substring(nextToLastSlash);
	}

}
