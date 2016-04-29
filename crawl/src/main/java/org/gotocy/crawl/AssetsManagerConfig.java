package org.gotocy.crawl;

import org.gotocy.config.S3Properties;
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
	@Profile("prod")
	public S3Properties s3Properties() {
		return new S3Properties();
	}

	@Bean
	@Profile("prod")
	public AssetsManager prodAssetsManager() {
		return new AmazonAssetsManager(s3Properties());
	}

	@Profile("local-dev")
	@Bean
	public AssetsManager localDevAssetsManager() {
		return new FileSystemAssetsManager("/home/killer/tmp/gtc_assets");
	}


}
