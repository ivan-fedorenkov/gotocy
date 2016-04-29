package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ifedorenkov
 */
@ConfigurationProperties(prefix = "gotocy.crawl")
@Getter
@Setter
public class CrawlProperties extends CrawlConfig {

	@NotNull
	private String crawlerClass;

	@NotEmpty
	private List<String> seeds = new ArrayList<>();

	@Min(1)
	private int numOfCrawlers = 1;

	@NestedConfigurationProperty
	private List<ClientCookie> cookies = new ArrayList<>();

	@NestedConfigurationProperty
	private PropertyFilter filter = new PropertyFilter();

	@NotEmpty
	private String googleMapsApiKey;

	/**
	 * Delegate for the {@link org.apache.http.impl.cookie.BasicClientCookie} that could be created
	 * by means of default constructor.
	 */
	@Setter
	public static class ClientCookie {

		@NotEmpty
		private String domain;

		@NotNull
		private String name;

		private String value;

		public org.apache.http.cookie.ClientCookie toClientCookie() {
			BasicClientCookie clientCookie = new BasicClientCookie(name, value);
			clientCookie.setDomain(domain);
			return clientCookie;
		}

	}

}
