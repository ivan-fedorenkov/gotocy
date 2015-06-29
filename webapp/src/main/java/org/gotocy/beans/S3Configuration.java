package org.gotocy.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ifedorenkov
 */
@Component
@ConfigurationProperties(prefix = "gotocy.s3")
public class S3Configuration {

	private static final long EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes

	@NotNull
	private String accessKey;

	@NotNull
	private String secretKey;

	@NotNull
	private String bucket;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	}
}
