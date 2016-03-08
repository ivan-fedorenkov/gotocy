package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.gotocy.crawl.cyprusreality.CyprusRealityCrawler;
import org.gotocy.crawl.mayfair.MayfairCrawler;
import org.gotocy.domain.Property;
import org.gotocy.service.PropertyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.atomic.AtomicInteger;
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

		PageFetcher pageFetcher = new CookieAwarePageFetcher(properties);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(properties, pageFetcher, robotstxtServer);

		PropertyService propertyService = context.getBean(PropertyService.class);

		CrawlController.WebCrawlerFactory factory = createCrawlerFactory(property -> {
			if (properties.getFilter().isPassingFilter(property)) {
				propertyService.create(property);
			}
		}, properties.getCrawlerClass());

		properties.getSeeds().forEach(controller::addSeed);
		controller.start(factory, properties.getNumOfCrawlers());
	}

	private static CrawlController.WebCrawlerFactory createCrawlerFactory(Consumer<Property> consumer, String crawlerClass) {
		switch (crawlerClass) {
		case "MayfairCrawler":
			return () -> new MayfairCrawler(consumer);
		case "CyprusRealityCrawler":
			return () -> new CyprusRealityCrawler(consumer);
		default:
			throw new RuntimeException("Unsupported crawler class: " + crawlerClass);
		}
	}

}
