package org.gotocy.service;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * An adapter interface to deal with various backend asset providers.
 * Managers are responsible only for the actual underlying data and are not meant to deal with database layer.
 *
 * @author ifedorenkov
 */
public interface AssetsManager {

	/**
	 * Returns public url of the given asset.
	 *
	 * @param asset which url is to be returned
	 * @return public url of the given asset
	 */
	Optional<String> getPublicUrl(Asset asset);

	/**
	 * Returns the new asset instance with loaded underlying object.
	 */
	<T extends Asset> Optional<T> getAsset(Supplier<T> factory, String assetKey);

	/**
	 * Saves the underlying object of the given {@link Asset}.
	 * TODO: return true/false instead of exception?
	 *
	 * @throws IOException when something goes wrong
	 */
	void saveAsset(Asset asset) throws IOException;

	/**
	 * Removes the underlying object of the given {@link Asset}.
	 * TODO: return true/false instead of exception?
	 *
	 * @throws IOException when something goes wrong
	 */
	void deleteAsset(Asset asset) throws IOException;

	/**
	 * Determines if the given asset exists in the underlying storage.
	 */
	boolean exists(Asset asset);

}
