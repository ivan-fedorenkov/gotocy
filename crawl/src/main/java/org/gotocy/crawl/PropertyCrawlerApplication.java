package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.gotocy.crawl.cyprusreality.CyprusRealityCrawler;
import org.gotocy.crawl.mayfair.MayfairCrawler;
import org.gotocy.domain.Property;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
@SpringBootApplication
@EnableConfigurationProperties(value = CrawlProperties.class)
public class PropertyCrawlerApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(PropertyCrawlerApplication.class, args);

		CrawlProperties properties = context.getBean(CrawlProperties.class);

		PageFetcher pageFetcher = new CookieAwarePageFetcher(properties);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(properties, pageFetcher, robotstxtServer);

		List<Property> fetchedProperties = new CopyOnWriteArrayList<>();
		CrawlController.WebCrawlerFactory factory = createCrawlerFactory(property -> {
			if (fetchedProperties.size() <= 10 && properties.getFilter().isPassingFilter(property))
				fetchedProperties.add(property);
			if (fetchedProperties.size() > 10 && !controller.isShuttingDown() && !controller.isFinished())
				controller.shutdown();
		}, properties.getCrawlerClass());

		properties.getSeeds().forEach(controller::addSeed);
		controller.start(factory, properties.getNumOfCrawlers());

		try (PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\ifedorenkov\\Documents\\tmp\\cyprusreality_new.txt"))) {
			for (Property property : fetchedProperties) {
				pw.println("Property: " + property.getTitle());
				pw.println("Url: " + property.getFeatures().stream().filter(f -> f.startsWith("url")).findFirst().orElse(""));
				pw.println("Features:");
				for (String f : property.getFeatures()) {
					if (!f.startsWith("url"))
						pw.println("  - " + f);
				}
				pw.println();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
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
