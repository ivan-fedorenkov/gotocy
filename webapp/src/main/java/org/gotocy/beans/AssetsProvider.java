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
	 * Returns an asset url by using the given asset key.
	 */
	String getUrl(String assetKey);

	/**
	 * Returns an {@link Asset} url.
	 */
	String getUrl(Asset asset);

	/**
	 * Returns an image {@link Image} url for specified {@link ImageSize}.
	 */
	String getImageUrl(Image image, ImageSize size);

	/**
	 * Loads the underlying object by using the specified object key.
	 * Returns the same {@link Asset} instance with populated underlying object set.
	 * TODO: think about refactoring, so it wouldn't be necessary to use the instanceof - may be a common converters
	 * TODO: from input stream to data types and Asset would return the appropriate converter.
	 */
	<T extends Asset> T loadUnderlyingObject(T asset);

}
