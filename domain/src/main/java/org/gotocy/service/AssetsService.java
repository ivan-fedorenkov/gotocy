package org.gotocy.service;

import org.gotocy.domain.Asset;

import java.util.Collection;

/**
 * Service that is responsible for assets management.
 *
 * @author ifedorenkov
 */
public interface AssetsService {

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

}
