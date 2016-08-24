package org.gotocy.service;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.util.Collection;
import java.util.Optional;

/**
 * Service that is responsible for assets management.
 *
 * @author ifedorenkov
 */
public interface AssetsService {

	/**
	 * Returns public url of the given asset.
	 *
	 * @param asset which url is to be returned
	 * @return public url of the given asset
	 */
	Optional<String> getPublicUrl(Asset asset);

	/**
	 * Special method to return public url of an image asset in the specified size.
	 *
	 * @param image which url is to be returned
	 * @param size image size hint, note that it is just a hint and it is not guarantied that the returned
	 *             url is referencing the image of the specific size
	 * @return public url of the given image
	 */
	Optional<String> getPublicUrl(Image image, ImageSize size);

	/**
	 * Creates the given assets with the appropriate key.
	 *
	 * @param prefix to be used for asset keys
	 * @param assets to be created
	 * @param generateNames flag that triggers assets name generation (random)
	 * @param <T> type of asset
	 * @return collection of created assets
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	<T extends Asset> Collection<T> createAssets(String prefix, Collection<T> assets, boolean generateNames)
		throws ServiceMethodExecutionException;

	/**
	 * Removes the given assets. If an asset could not be deleted for some reason then no exception is raising but
	 * an error message goes to log.
	 *
	 * @param assets to be removed
	 * @param <T> type of asset
	 */
	<T extends Asset> void deleteAssets(Collection<T> assets);

}
