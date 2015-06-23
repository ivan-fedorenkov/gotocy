package org.gotocy;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ifedorenkov
 */
@Configuration
@EnableJpaRepositories
@EntityScan
public class Config {
}
