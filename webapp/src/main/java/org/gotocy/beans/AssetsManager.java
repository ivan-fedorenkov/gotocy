package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.io.IOException;
import java.util.Optional;

/**
 * An adapter interface to deal with various backend asset providers.
 *
 * @author ifedorenkov
 */
public interface AssetsManager {

	/**
	 * @param asset which url is to be returned
	 * @return public url of the given asset
	 */
	Optional<String> getPublicUrl(Asset asset);

	/**
	 * @param image which url is to be returned
	 * @param size image size hint, note that it is just a hint and it is not guarantied that the returned
	 *             url is referencing the image of the specific size
	 * @return public url of the given image
	 */
	Optional<String> getImagePublicUrl(Image image, ImageSize size);

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

	default void deleteUnderlyingObject(Asset asset) throws IOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
