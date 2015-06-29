package org.gotocy.beans;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.gotocy.domain.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Assets provider that utilizes Amazon S3 as a backend storage.
 * TODO: handle the Amazon exceptions
 * TODO: unit tests
 *
 * @author ifedorenkov
 */
@Component
public class AmazonAssetsProvider extends AmazonS3Client implements AssetsProvider {

	private final S3Configuration config;

	@Autowired
	public AmazonAssetsProvider(S3Configuration config) {
		super(new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey()));
		this.config = config;
	}

	@Override
	public String getUrl(Asset asset) {
		return generatePresignedUrl(asset.getAssetKey(), HttpMethod.GET);
	}

	@Override
	public String getImageUrl(Asset asset, ImageSize size) {
		String assetKey;
		switch (size) {
		case THUMBNAIL:
			if (exists(assetKey = getAssetKeyForSize(asset, ImageSize.THUMBNAIL)))
				return generatePresignedUrl(assetKey, HttpMethod.GET);
		case SMALL:
			if (exists(assetKey = getAssetKeyForSize(asset, ImageSize.SMALL)))
				return generatePresignedUrl(assetKey, HttpMethod.GET);
		case MEDIUM:
			if (exists(assetKey = getAssetKeyForSize(asset, ImageSize.MEDIUM)))
				return generatePresignedUrl(assetKey, HttpMethod.GET);
		default: // Fall back to default - original
			return getUrl(asset);
		}
	}

	private String generatePresignedUrl(String assetKey, HttpMethod method) {
		return generatePresignedUrl(config.getBucket(), assetKey, config.getExpirationDate(), method).toString();
	}

	private boolean exists(String assetKey) {
		// Dirty, yet simple solution
		try {
			getObjectMetadata(config.getBucket(), assetKey);
			return true;
		} catch (AmazonS3Exception e) {
			return false;
		}
	}

	private static String getAssetKeyForSize(Asset asset, ImageSize imageSize) {
		String assetKey = asset.getAssetKey();
		int nextToLastSlash = assetKey.lastIndexOf('/') + 1;
		return assetKey.substring(0, nextToLastSlash) + imageSize.name() + '_' + assetKey.substring(nextToLastSlash);

	}


}
