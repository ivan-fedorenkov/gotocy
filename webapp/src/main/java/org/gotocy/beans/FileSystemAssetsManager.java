package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.Executor;

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
	public Optional<String> getImagePublicUrl(Image image, ImageSize size) {
		String imageKey = image.getKeyForSize(size);
		return exists(imageKey) ? getUrl(imageKey) : getPublicUrl(image);
	}

	@Override
	public <T extends Asset> T loadUnderlyingObject(T asset) {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		if (Files.isReadable(assetPath)) {
			try {
				asset.setBytes(Files.readAllBytes(assetPath));
			} catch (IOException e) {
				logger.error("Failed to load underlying object for {}", asset, e);
			}
		}
		return asset;
	}

	@Override
	public void saveUnderlyingObject(Asset asset) throws IOException {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		Files.createDirectories(assetPath.getParent());
		Files.write(assetPath, asset.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
			StandardOpenOption.TRUNCATE_EXISTING);
	}

	private Optional<String> getUrl(String assetKey) {
		return Optional.of("/fs_assets?key=" + assetKey);
	}

	private boolean exists(String assetKey) {
		return Files.isReadable(Paths.get(assetsDirPath, assetKey));
	}

}
