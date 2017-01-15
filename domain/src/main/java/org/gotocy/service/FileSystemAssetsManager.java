package org.gotocy.service;

import org.gotocy.domain.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	private static final int BUFFER_SIZE = 2048;

	private final String assetsClassPathRoot;
	private final String assetsDirPath;

	public FileSystemAssetsManager(String assetsClassPathRoot, String assetsDirPath) {
		this.assetsClassPathRoot = assetsClassPathRoot;
		this.assetsDirPath = assetsDirPath;
	}

	@Override
	public Optional<String> getPublicUrl(Asset asset) {
		if (exists(asset)) {
			return getUrl(asset.getKey());
		} else {
			logger.error("Failed to generate public url for {}. Underlying object not found.", asset);
			return Optional.empty();
		}
	}

	@Override
	public <T extends Asset> Optional<T> getAsset(Supplier<T> factory, String assetKey) {
		Optional<T> result = Optional.empty();
		Path assetPath = Paths.get(assetsDirPath, assetKey);
		try {
			if (Files.isReadable(assetPath)) {
				try {
					T asset = factory.get();
					asset.setBytes(Files.readAllBytes(assetPath));
					result = Optional.of(asset);
				} catch (IOException e) {
					logger.error("Failed to load underlying object for asset key '{}'", assetKey, e);
				}
			} else {
				try (BufferedInputStream bis =
						 new BufferedInputStream(getClass().getResourceAsStream(assetsClassPathRoot + assetKey));
					 ByteArrayOutputStream bos = new ByteArrayOutputStream())
				{
					byte[] buffer = new byte[BUFFER_SIZE];
					int bytesRead;
					while ((bytesRead = bis.read(buffer)) != -1)
						bos.write(buffer, 0, bytesRead);
					T asset = factory.get();
					asset.setBytes(bos.toByteArray());
					result = Optional.of(asset);
				}
			}
		} catch (IOException ioe) {
			logger.error("Failed to load underlying object for asset key '{}'", assetKey, ioe);
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
		boolean exists = Files.isReadable(Paths.get(assetsDirPath, asset.getKey()));
		if (!exists) {
			try (InputStream is = getClass().getResourceAsStream(assetsClassPathRoot + asset.getKey())) {
				exists = is != null;
			} catch (IOException ignore) {
			}
		}
		return exists;
	}

	private Optional<String> getUrl(String assetKey) {
		return Optional.of("/fs_assets?key=" + assetKey);
	}

}
