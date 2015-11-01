package org.gotocy.beans;

import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author ifedorenkov
 */
public class FileSystemAssetsManager implements AssetsManager {

	private final String assetsDirPath;

	public FileSystemAssetsManager(String assetsDirPath) {
		this.assetsDirPath = assetsDirPath;
	}

	@Override
	public String getUrl(String assetKey) {
		return "/fs_assets?key=" + assetKey;
	}

	@Override
	public String getUrl(Asset asset) {
		return getUrl(asset.getKey());
	}

	@Override
	public String getImageUrl(Image image, ImageSize size) {
		String imageKey = image.getKeyForSize(size);
		Path imagePath = Paths.get(assetsDirPath, imageKey);
		if (Files.isReadable(imagePath))
			return getUrl(imageKey);
		return getUrl(image.getKey());
	}

	@Override
	public <T extends Asset> T loadUnderlyingObject(T asset) {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		if (Files.isReadable(assetPath)) {
			try {
				asset.setBytes(Files.readAllBytes(assetPath));
			} catch (IOException ioe) {
				// TODO: log error
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
}
