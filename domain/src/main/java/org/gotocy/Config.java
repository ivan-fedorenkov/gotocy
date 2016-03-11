package org.gotocy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ifedorenkov
 */
@Configuration
@EnableJpaRepositories
@EntityScan
public class Config {

	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	public CacheManager cacheManager() {
		return new NoOpCacheManager();
	}

}
