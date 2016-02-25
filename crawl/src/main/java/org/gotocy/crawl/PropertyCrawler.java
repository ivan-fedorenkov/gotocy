package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.Getter;
import org.gotocy.domain.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author ifedorenkov
 */
@Getter
public abstract class PropertyCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(PropertyCrawler.class);

	protected static final Pattern FILTER = Pattern.compile(".*(\\.(css|js|gif|jpg|jpeg|png|mp3|mp3|zip|gz))$");

	private final Consumer<Property> propertyConsumer;

	public PropertyCrawler(Consumer<Property> propertyConsumer) {
		this.propertyConsumer = propertyConsumer;
	}

	protected Logger getLogger() {
		return logger;
	}

}
