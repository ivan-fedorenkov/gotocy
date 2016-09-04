package org.gotocy.config;

import org.gotocy.service.AmazonAssetsManager;
import org.gotocy.service.AssetsManager;
import org.gotocy.service.FileSystemAssetsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author ifedorenkov
 */
@Configuration
public class AssetsManagerConfig {

	@Bean
	@Profile(Profiles.PROD)
	public S3Properties s3Properties() {
		return new S3Properties();
	}

	@Bean
	@Profile(Profiles.PROD)
	public AssetsManager prodAssetsManager() {
		return new AmazonAssetsManager(s3Properties());
	}

	@Bean
	@Profile(Profiles.DEV)
	public AssetsManager devAssetsManager() {
		return new FileSystemAssetsManager("/storage/", "/tmp/gtc_assets");
	}

	@Bean
	@Profile(Profiles.TEST)
	public AssetsManager testAssetsManager() {
		return new FileSystemAssetsManager("/storage/", "/tmp/gtc_assets");
	}

}
