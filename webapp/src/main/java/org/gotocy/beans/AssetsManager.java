package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.io.IOException;
import java.io.InputStream;

/**
 * An adapter interface to deal with various backend asset providers.
 *
 * @author ifedorenkov
 */
public interface AssetsManager {

	/**
	 * Returns {@link Asset} url.
	 */
	String getUrl(Asset asset);

	/**
	 * Returns {@link Image} url for specified {@link ImageSize}.
	 */
	String getImageUrl(Image image, ImageSize size);

	/**
	 * Loads the underlying object by using the specified object key.
	 * Returns the same {@link Asset} instance with populated underlying object set.
	 */
	<T extends Asset> T loadUnderlyingObject(T asset);

	/**
	 * Saves the underlying object of the given {@link Asset}.
	 * @param asset which underlying object is to be saved
	 * @throws IOException in case of any IO errors
	 */
	void saveUnderlyingObject(Asset asset) throws IOException;

}
