package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.Getter;
import org.gotocy.domain.Property;

import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
@Getter
public abstract class PropertyCrawler extends WebCrawler {

	private final Consumer<Property> propertyConsumer;

	public PropertyCrawler(Consumer<Property> propertyConsumer) {
		this.propertyConsumer = propertyConsumer;
	}

}
