package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @author ifedorenkov
 */
@ConfigurationProperties(prefix = "gotocy.s3")
@Getter
@Setter
public class S3Properties {

	public static final String PREFIX = "gotocy.s3";

	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 60 minutes

	@NotEmpty
	private String accessKey;

	@NotEmpty
	private String secretKey;

	@NotEmpty
	private String bucket;

	@NotEmpty
	private String region;

	public Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	}

}
