package org.gotocy.service;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.gotocy.utils.ImageConverter;
import org.gotocy.utils.StringKeyGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ifedorenkov
 */
@Service
public class AssetsServiceImpl implements AssetsService {
	private static final Logger logger = LoggerFactory.getLogger(AssetsService.class);

	private static final int RANDOM_ASSET_KEY_LENGTH = 8;

	private final AssetsManager assetsManager;
	private final StringKeyGenerator assetsKeyGenerator;
	private final Executor executor;

	@Autowired
	public AssetsServiceImpl(AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
		assetsKeyGenerator = new StringKeyGeneratorImpl(RANDOM_ASSET_KEY_LENGTH);
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public Optional<String> getPublicUrl(Asset asset) {
		return assetsManager.getPublicUrl(asset);
	}

	@Override
	public Optional<String> getPublicUrl(Image image, ImageSize size) {
		Optional<String> url = Optional.empty();
		Image sizedImage = image.getSized(size);
		if (assetsManager.exists(sizedImage)) {
			url = getPublicUrl(sizedImage);
		} else if (assetsManager.exists(image)) {
			executor.execute(() -> {
				Optional<Image> originalImage = assetsManager.getAsset(() -> image, image.getKey());

				if (originalImage.isPresent()) {
					Optional<Image> resizedImage = ImageConverter.convertToSize(originalImage.get(), size);
					// Successfully resized
					if (resizedImage.isPresent()) {
						try {
							assetsManager.saveAsset(resizedImage.get());
						} catch (IOException ioe) {
							logger.error("Failed to save resized image {}", resizedImage.get());
						}
					}
				}
			});
			url = getPublicUrl(image);
		}
		return url;
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
					asset.setKey(assetsKeyGenerator.generateKey() + (index++) + "." + asset.getFileExtension());

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

	@Override
	public <T extends Asset> void deleteAssets(Collection<T> assets) {
		if (assets == null || assets.isEmpty())
			return;

		// Prepare a list of assets to be deleted
		List<Asset> toDelete = new ArrayList<>();
		for (T asset : assets) {
			if (asset instanceof Image) {
				Image image = (Image) asset;
				for (ImageSize imageSize : ImageSize.values()) {
					Image sized = image.getSized(imageSize);
					if (assetsManager.exists(sized))
						toDelete.add(sized);
				}
			}
			toDelete.add(asset);
		}

		// Delete assets
		for (Asset asset : toDelete) {
			try {
				assetsManager.deleteAsset(asset);
			} catch (IOException ioe) {
				logger.error("Failed to delete asset: {}.", asset.getKey(), ioe);
			}
		}
	}

}
