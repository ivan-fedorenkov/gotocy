package org.gotocy.beans;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.gotocy.domain.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

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
		super(new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey()));
		this.config = config;
	}

	@Override
	public URL getUrl(Asset asset) {
		return generatePresignedUrl(config.getBucket(), asset.getAssetKey(), config.getExpirationDate(),
			HttpMethod.GET);
	}

}
