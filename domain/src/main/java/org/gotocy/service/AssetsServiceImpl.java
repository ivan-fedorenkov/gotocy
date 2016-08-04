package org.gotocy.service;

import org.gotocy.domain.Asset;
import org.gotocy.utils.StringKeyGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author ifedorenkov
 */
@Service
public class AssetsServiceImpl implements AssetsService {
	private static final Logger logger = LoggerFactory.getLogger(AssetsService.class);

	private static final int RANDOM_ASSET_KEY_LENGTH = 8;

	private final AssetsManager assetsManager;
	private final StringKeyGenerator assetsKeyGenerator;

	@Autowired
	public AssetsServiceImpl(AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
		assetsKeyGenerator = new StringKeyGeneratorImpl(RANDOM_ASSET_KEY_LENGTH);
	}

	@Override
	public <T extends Asset> Collection<T> createAssets(String prefix, Collection<T> assets, boolean generateKeys)
		throws ServiceMethodExecutionException
	{
		if (assets == null || assets.isEmpty())
			return Collections.emptyList();

		Collection<T> createdAssets = new ArrayList<>(assets.size());
		try {
			int index = 0;
			for (T asset : assets) {
				if (generateKeys || asset.getKey() == null || asset.getKey().trim().isEmpty())
					asset.setKey(assetsKeyGenerator.generateKey() + (index++));

				asset.setKey(prefix + "/" + asset.getKey());
				assetsManager.saveAsset(asset);
				createdAssets.add(asset);
			}
		} catch (NullPointerException | IOException | DataAccessException e) {
			// Log error
			logger.error("Failed to create assets.", e);

			// Clean up created objects
			try {
				for (Asset asset : createdAssets)
					assetsManager.deleteAsset(asset);
			} catch (DataAccessException | IOException ee) {
				logger.error("Failed to clean up resources.", ee);
			}

			// Rethrow so that the user would be notified appropriately (this is kind of critical error)
			throw new ServiceMethodExecutionException(e);
		}
		return createdAssets;
	}

}
