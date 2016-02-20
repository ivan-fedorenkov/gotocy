package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.gotocy.crawl.mayfair.MayfairCrawler;
import org.gotocy.domain.Property;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author ifedorenkov
 */
@SpringBootApplication
@EnableConfigurationProperties(value = CrawlerProperties.class)
public class PropertyCrawlerApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(PropertyCrawlerApplication.class, args);

		List<Property> fetchedProperties = new CopyOnWriteArrayList<>();

		CrawlerProperties properties = context.getBean(CrawlerProperties.class);
		CrawlController.WebCrawlerFactory factory = createCrawlerFactory(fetchedProperties::add, properties.getCrawlerClass());

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(properties.getCrawlerStorageFolder());

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		IntStream.range(3000, 4000)
			.boxed()
			.map(id -> "http://www.mayfair-cyprus.com/easyconsole.cfm/page/property_viewer/pr_id/" + id)
			.forEach(controller::addSeed);
		controller.start(factory, properties.getNumOfCrawlers());

		int k = 1;
	}

	private static CrawlController.WebCrawlerFactory createCrawlerFactory(Consumer<Property> consumer, String crawlerClass) {
		switch (crawlerClass) {
		case "MayfairCrawler":
			return () -> new MayfairCrawler(consumer);
		default:
			throw new RuntimeException("Unsupported crawler class: " + crawlerClass);
		}
	}

}
