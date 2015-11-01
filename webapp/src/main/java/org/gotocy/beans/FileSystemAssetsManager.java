package org.gotocy.beans;

import org.gotocy.domain.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
				if (asset instanceof PanoXml) {
					((PanoXml) asset).setObject(new String(Files.readAllBytes(assetPath), Charset.forName("UTF-8")));
				} else if (asset instanceof Image) {
					((Image) asset).setObject(Files.readAllBytes(assetPath));
				} else if (asset instanceof PdfFile) {
					((PdfFile) asset).setObject(Files.readAllBytes(assetPath));
				}
			} catch (IOException ioe) {
				// TODO: log error
			}
		}
		return asset;
	}

	@Override
	public void saveUnderlyingObject(Asset asset, InputStream in) throws IOException {
		Path assetPath = Paths.get(assetsDirPath, asset.getKey());
		Files.createDirectories(assetPath.getParent());
		Files.copy(in, assetPath, StandardCopyOption.REPLACE_EXISTING);
	}
}
