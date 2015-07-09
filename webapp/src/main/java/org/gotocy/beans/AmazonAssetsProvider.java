package org.gotocy.beans;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Region;
import org.gotocy.config.S3Configuration;
import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Assets provider that utilizes Amazon S3 as a backend storage.
 * TODO: handle the Amazon exceptions
 *
 * @author ifedorenkov
 */
@Component
public class AmazonAssetsProvider extends AmazonS3Client implements AssetsProvider {

	private final S3Configuration config;

	@Autowired
	public AmazonAssetsProvider(S3Configuration config) {
		super(new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey()),
			new ClientConfiguration().withProtocol(Protocol.HTTP));

		setRegion(Region.EU_Ireland.toAWSRegion());

		this.config = config;
	}

	@Override
	public String getUrl(Asset asset) {
		return generatePresignedUrl(asset.getKey(), HttpMethod.GET);
	}

	@Override
	public String getImageUrl(Image image, ImageSize size) {
		String key;
		switch (size) {
		case THUMBNAIL:
			if (exists(key = image.getKeyForSize(ImageSize.THUMBNAIL)))
				return generatePresignedUrl(key, HttpMethod.GET);
		case SMALL:
			if (exists(key = image.getKeyForSize(ImageSize.SMALL)))
				return generatePresignedUrl(key, HttpMethod.GET);
		case MEDIUM:
			if (exists(key = image.getKeyForSize(ImageSize.MEDIUM)))
				return generatePresignedUrl(key, HttpMethod.GET);
		default: // Fall back to default - original
			return getUrl(image);
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

}
