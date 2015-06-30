package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

/**
 * An adapter interface to deal with various backend asset providers.
 *
 * @author ifedorenkov
 */
public interface AssetsProvider {

	/**
	 * Returns an {@link Asset} url.
	 */
	String getUrl(Asset asset);

	/**
	 * Returns an image {@link Image} url for specified {@link ImageSize}.
	 */
	String getImageUrl(Image image, ImageSize size);

}
