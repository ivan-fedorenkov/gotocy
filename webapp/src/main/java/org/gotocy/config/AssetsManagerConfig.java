package org.gotocy.config;

import org.gotocy.beans.AmazonAssetsManager;
import org.gotocy.beans.AssetsManager;
import org.gotocy.beans.FileSystemAssetsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author ifedorenkov
 */
@Configuration
public class AssetsManagerConfig {

	@Bean
	@Profile(Profiles.HEROKU_PROD)
	public S3Properties s3Properties() {
		return new S3Properties();
	}

	@Bean
	@Profile(Profiles.HEROKU_PROD)
	public AssetsManager prodAssetsManager() {
		return new AmazonAssetsManager(s3Properties());
	}

	@Bean
	@Profile(Profiles.LOCAL_DEV)
	public AssetsManager devAssetsManager() {
		return new FileSystemAssetsManager("/home/killer/tmp/gtc_assets");
	}

}
