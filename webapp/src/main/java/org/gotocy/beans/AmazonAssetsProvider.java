package org.gotocy.beans;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.gotocy.config.S3Properties;
import org.gotocy.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Assets provider that utilizes Amazon S3 as a backend storage.
 * TODO: handle the Amazon exceptions
 *
 * @author ifedorenkov
 */
@Component
public class AmazonAssetsProvider extends AmazonS3Client implements AssetsProvider {

	private final S3Properties properties;

	@Autowired
	public AmazonAssetsProvider(S3Properties properties) {
		super(new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey()),
			new ClientConfiguration().withProtocol(Protocol.HTTP));

		setRegion(Region.EU_Ireland.toAWSRegion());

		this.properties = properties;
	}

	@Override
	public String getUrl(String assetKey) {
		return generatePresignedUrl(assetKey, HttpMethod.GET);
	}

	@Override
	public String getUrl(Asset asset) {
		return getUrl(asset.getKey());
	}

	@Override
	public String getImageUrl(Image image, ImageSize size) {
		String key =image.getKeyForSize(size);
		// Fall back to original if key can't be found.
		// TODO: log error
		return exists(key) ? generatePresignedUrl(key, HttpMethod.GET) : getUrl(image);
	}

	@Override
	public <T extends Asset> T loadUnderlyingObject(T asset) {
		try {
			// Load the object input stream
			S3ObjectInputStream is = getObject(properties.getBucket(), asset.getKey()).getObjectContent();

			// Set the underlying object in the given asset instance
			if (asset instanceof PanoXml) {
				((PanoXml) asset).setObject(IOUtils.toString(is));
			} else if (asset instanceof Image) {
				((Image) asset).setObject(IOUtils.toByteArray(is));
			} else if (asset instanceof PdfFile) {
				((PdfFile) asset).setObject(IOUtils.toByteArray(is));
			}
		} catch (IOException ignore) {
			// TODO: log IOException
		}
		return asset;
	}

	private String generatePresignedUrl(String assetKey, HttpMethod method) {
		return generatePresignedUrl(properties.getBucket(), assetKey, properties.getExpirationDate(), method).toString();
	}

	private boolean exists(String assetKey) {
		// Dirty, yet simple solution
		try {
			getObjectMetadata(properties.getBucket(), assetKey);
			return true;
		} catch (AmazonS3Exception e) {
			return false;
		}
	}

}
