package org.gotocy.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.StorageClass;
import org.gotocy.config.S3Properties;
import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Assets manager that utilizes Amazon S3 Client as a backend storage provider.
 *
 * @author ifedorenkov
 */
public class AmazonAssetsManager extends AbstractAssetsManager {

	private AmazonS3Client s3Client;
	private final S3Properties s3Properties;

	public AmazonAssetsManager(S3Properties s3Properties) {
		s3Client = new AmazonS3Client(new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey()),
			new ClientConfiguration().withProtocol(Protocol.HTTP));
		s3Client.setRegion(Region.EU_Ireland.toAWSRegion());

		this.s3Properties = s3Properties;
	}

	@Override
	public Optional<String> getPublicUrl(Asset asset) {
		return Optional.of("http://" + s3Properties.getBucket() + "/" + asset.getKey());
	}

	@Override
	public <T extends Asset> Optional<T> getAsset(Supplier<T> factory, String assetKey) {
		Optional<T> result = Optional.empty();
		try (InputStream in = s3Client.getObject(s3Properties.getBucket(), assetKey).getObjectContent()) {
			T asset = factory.get();
			asset.setBytes(StreamUtils.copyToByteArray(in));
			result = Optional.of(asset);
		} catch (AmazonClientException | IOException e) {
			getLogger().error("Failed to load underlying object for asset key '{}'", assetKey, e);
		}
		return result;
	}

	@CacheEvict(cacheNames = "asset_exists_cache", key="asset.key")
	@Override
	public void saveAsset(Asset asset) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(asset.getSize());
		metadata.setContentType(asset.getContentType());
		try (InputStream in = new ByteArrayInputStream(asset.getBytes())) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(s3Properties.getBucket(), asset.getKey(), in, metadata);
			s3Client.putObject(putObjectRequest.withStorageClass(getStorageClass(asset)));
		} catch (AmazonClientException e) {
			getLogger().error("Failed to save underlying object of {}", asset, e);
			throw new IOException(e);
		}
	}

	@CacheEvict(cacheNames = "asset_exists_cache", key="#asset.key")
	@Override
	public void deleteAsset(Asset asset) throws IOException {
		try {
			s3Client.deleteObject(s3Properties.getBucket(), asset.getKey());
		} catch (AmazonClientException e) {
			getLogger().error("Failed to delete underlying object of {}", asset, e);
			throw new IOException(e);
		}
	}

	@Cacheable(cacheNames = "asset_exists_cache", key="#asset.key")
	@Override
	public boolean exists(Asset asset) {
		// Dirty, yet simple solution
		try {
			s3Client.getObjectMetadata(s3Properties.getBucket(), asset.getKey());
			return true;
		} catch (AmazonClientException e) {
			return false;
		}
	}

	private Optional<String> generatePresignedUrl(Asset asset) {
		Optional<String> url = Optional.empty();
		try {
			url = Optional.of(s3Client.generatePresignedUrl(s3Properties.getBucket(), asset.getKey(),
				s3Properties.getExpirationDate(), HttpMethod.GET).toString());
		} catch (AmazonClientException e) {
			getLogger().error("Failed to generate presigned url for asset key '{}'", asset.getKey(), e);
		}
		return url;
	}

	/**
	 * Returns the appropriate default storage class for the given asset.
	 * Currently only images are supported:
	 * - resized images should be stored with {@link StorageClass#ReducedRedundancy} class
	 * - original images should be stored with {@link StorageClass#StandardInfrequentAccess} class
	 */
	private static StorageClass getStorageClass(Asset asset) {
		if (asset instanceof Image) {
			return asset.getKey().startsWith(Image.RESIZED_IMAGE_KEY_PREFIX) ?
				StorageClass.ReducedRedundancy : StorageClass.StandardInfrequentAccess;
		} else {
			return StorageClass.Standard;
		}
	}

}
