package org.gotocy.integration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author ifedorenkov
 */
@EnableAutoConfiguration
@Import(org.gotocy.Config.class)
public class Config {
}
