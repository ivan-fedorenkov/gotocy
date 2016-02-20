package org.gotocy.crawl;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * @author ifedorenkov
 */
@ConfigurationProperties(prefix = "gotocy.crawl")
@Getter
@Setter
public class CrawlerProperties {

	@NotNull
	private String crawlerClass;

	@NotNull
	private String crawlerStorageFolder;

	@NotEmpty
	private String[] seeds;

	private int numOfCrawlers = 1;

}
