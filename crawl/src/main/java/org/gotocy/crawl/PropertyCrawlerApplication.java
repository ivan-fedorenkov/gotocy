package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.gotocy.crawl.cyprusreality.CyprusRealityCrawler;
import org.gotocy.crawl.giovani.GiovaniCrawler;
import org.gotocy.crawl.mayfair.MayfairCrawler;
import org.gotocy.domain.Property;
import org.gotocy.service.PropertyService;
import org.gotocy.utils.googlegeo.FromLatLngResponse;
import org.gotocy.utils.googlegeo.GoogleGeocode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.gotocy")
@EnableConfigurationProperties(value = CrawlProperties.class)
public class PropertyCrawlerApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(PropertyCrawlerApplication.class, args);
		CrawlProperties properties = context.getBean(CrawlProperties.class);
		properties.setConnectionTimeout(20000);
		properties.setSocketTimeout(20000);
		PageFetcher pageFetcher = new PageFetcher(properties);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(properties, pageFetcher, robotstxtServer);
		PropertyService propertyService = context.getBean(PropertyService.class);

		CrawlController.WebCrawlerFactory factory = createCrawlerFactory(property -> {
			if (properties.getFilter().isPassingFilter(property)) {
				propertyService.create(property);
			}
		}, properties);

		properties.getSeeds().forEach(controller::addSeed);
		controller.start(factory, properties.getNumOfCrawlers());
	}

	private static CrawlController.WebCrawlerFactory createCrawlerFactory(Consumer<Property> consumer,
		CrawlProperties properties)
	{
		switch (properties.getCrawlerClass()) {
		case "MayfairCrawler":
			return () -> new MayfairCrawler(consumer);
		case "CyprusRealityCrawler":
			return () -> new CyprusRealityCrawler(consumer);
		case "GiovaniCrawler":
			return () -> new GiovaniCrawler(new PropertyLocationResolver(properties.getGoogleMapsApiKey())
				.andThen(consumer));
		default:
			throw new RuntimeException("Unsupported crawler class: " + properties.getCrawlerClass());
		}
	}

	private static class PropertyLocationResolver implements Consumer<Property> {
		private static final Logger logger = LoggerFactory.getLogger(PropertyLocationResolver.class);

		private final String googleMapsApiKey;

		public PropertyLocationResolver(String googleMapsApiKey) {
			this.googleMapsApiKey = googleMapsApiKey;
		}

		@Override
		public void accept(Property property) {
			try {
				FromLatLngResponse geocodeResponse = GoogleGeocode.backGeocodeRequest(
					property.getLatitude(), property.getLongitude(), googleMapsApiKey);
				if (geocodeResponse.isResponseOK()) {
					property.setAddress(geocodeResponse.getAddress());
					property.setLocation(geocodeResponse.getLocation());
				} else {
					logger.warn("Failed to resolve location of the property: {} from coordinates: {},{}",
						property.getTitle(), property.getLatitude(), property.getLongitude());
				}
			} catch (IOException ioe) {
				logger.error("Failed to resolve location of the property", ioe);
			}
		}
	}

}
