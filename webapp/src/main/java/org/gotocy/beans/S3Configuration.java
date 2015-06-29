package org.gotocy.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ifedorenkov
 */
public class S3Configuration {

	private static final long EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
	private static final String BUCKET = "gotocy-assets";

	public String getBucket() {
		return BUCKET;
	}

	public Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	}
}
