package org.gotocy.service;

import org.gotocy.domain.Asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author ifedorenkov
 */
public class FileSystemAssetsManager extends AbstractAssetsManager {

	private final String assetsDirPath;

	public FileSystemAssetsManager(String assetsDirPath) {
		this.assetsDirPath = assetsDirPath;
	}

	@Override
	public Optional<String> getPublicUrl(Asset asset) {
		if (exists(asset)) {
			return getUrl(asset.getKey());
		} else {
			getLogger().error("Failed to generate public url for {}. Underlying object not found.", asset);
			return Optional.empty();
		}
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
				getLogger().error("Failed to load underlying object for asset key '{}'", assetKey, e);
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

	@Override
	public boolean exists(Asset asset) {
		return Files.isReadable(Paths.get(assetsDirPath, asset.getKey()));
	}

	private Optional<String> getUrl(String assetKey) {
		return Optional.of("/fs_assets?key=" + assetKey);
	}

}
