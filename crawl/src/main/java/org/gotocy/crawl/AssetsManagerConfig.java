package org.gotocy.crawl;

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

	@Profile("local-dev")
	@Bean
	public AssetsManager localDevAssetsManager() {
		return new FileSystemAssetsManager("/home/killer/tmp/gtc_assets");
	}

}
