package org.gotocy.beans;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gotocy.config.S3Properties;
import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Assets provider that utilizes Amazon S3 as a backend storage.
 *
 * @author ifedorenkov
 */
public class AmazonAssetsManager extends AmazonS3Client implements AssetsManager {

	private static final Log log = LogFactory.getLog(AmazonAssetsManager.class);

	private final S3Properties properties;

	public AmazonAssetsManager(S3Properties properties) {
		super(new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey()),
			new ClientConfiguration().withProtocol(Protocol.HTTP));

		setRegion(Region.EU_Ireland.toAWSRegion());

		this.properties = properties;
	}

	@Override
	public String getUrl(Asset asset) {
		return getUrl(asset.getKey());
	}

	@Override
	public String getImageUrl(Image image, ImageSize size) {
		String key = image.getKeyForSize(size);
		// Fall back to original if key can't be found.
		return exists(key) ? getUrl(key) : getUrl(image);
	}

	@Override
	public <T extends Asset> T loadUnderlyingObject(T asset) {
		try (InputStream in = getObject(properties.getBucket(), asset.getKey()).getObjectContent()) {
			asset.setBytes(IOUtils.toByteArray(in));
		} catch (AmazonClientException | IOException e) {
			log.error("Failed to load asset's underlying object for key: " + asset.getKey(), e);
		}
		return asset;
	}

	@Override
	public void saveUnderlyingObject(Asset asset) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(asset.getSize());
		metadata.setContentType(asset.getContentType());
		try (InputStream in = new ByteArrayInputStream(asset.getBytes())) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), asset.getKey(), in, metadata);
			putObject(putObjectRequest.withStorageClass(StorageClass.Standard));
		} catch (AmazonClientException e) {
			throw new IOException(e);
		}
	}

	public String getUrl(String assetKey) {
		return generatePresignedUrl(properties.getBucket(), assetKey, properties.getExpirationDate(), HttpMethod.GET)
			.toString();
	}

	private boolean exists(String assetKey) {
		// Dirty, yet simple solution
		try {
			getObjectMetadata(properties.getBucket(), assetKey);
			return true;
		} catch (AmazonClientException e) {
			return false;
		}
	}

}
