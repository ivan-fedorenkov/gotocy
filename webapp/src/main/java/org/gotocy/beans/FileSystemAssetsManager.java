package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.gotocy.service.AssetsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author ifedorenkov
 */
public class FileSystemAssetsManager implements AssetsManager {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemAssetsManager.class);

	private final String assetsDirPath;

	public FileSystemAssetsManager(String assetsDirPath) {
		this.assetsDirPath = assetsDirPath;
	}

	@Override
	public Optional<String> getPublicUrl(Asset asset) {
		if (exists(asset.getKey())) {
			return getUrl(asset.getKey());
		} else {
			logger.error("Failed to generate public url for {}. Underlying object not found.", asset);
			return Optional.empty();
		}
	}

	@Override
	public Optional<String> getPublicUrl(Image image, ImageSize size) {
		String imageKey = image.getKeyForSize(size);
		return exists(imageKey) ? getUrl(imageKey) : getPublicUrl(image);
	}

	@Override
	public <T extends Asset> Optional<T> getAsset(Supplier<T> factory, String assetKey) {
		Optional<T> result = Optional.empty();
		Path assetPath = Paths.get(assetsDirPath, assetKey);
		if (Files.isReadable(assetPath)) {
			try {
				T asset = factory.get();
				asset.setBytes(Files.readAllBytes(assetPath));
				result = Optional.of(asset);
			} catch (IOException e) {
				logger.error("Failed to load underlying object for asset key '{}'", assetKey, e);
			}
		}
		return result;
	}

	@Override
	public void saveAsset(Asset asset) throws IOException {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		Files.createDirectories(assetPath.getParent());
		Files.write(assetPath, asset.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
			StandardOpenOption.TRUNCATE_EXISTING);
	}

	@Override
	public void deleteAsset(Asset asset) throws IOException {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		Files.delete(assetPath);
	}

	private Optional<String> getUrl(String assetKey) {
		return Optional.of("/fs_assets?key=" + assetKey);
	}

	private boolean exists(String assetKey) {
		return Files.isReadable(Paths.get(assetsDirPath, assetKey));
	}

}
