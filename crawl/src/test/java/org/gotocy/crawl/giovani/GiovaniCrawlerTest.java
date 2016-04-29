package org.gotocy.crawl.giovani;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.CrawlProperties;
import org.gotocy.domain.Furnishing;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class GiovaniCrawlerTest {

	private static final String SALE_NEW_VILLA_DETACHED_URL =
		"http://giovani.com.cy/property/gd00188-3-bedroom-detached-villa-levanda/";

	private static final String SOLD_SEMI_DETACHED_VILLA_URL =
		"http://giovani.com.cy/property/2-bedroom-villa-for-sale-in-ayia-triada-protaras-gd00100/";

	private static final String SALE_NEW_VILLA_WITHOUT_PRICE_URL =
		"http://giovani.com.cy/property/3-bedroom-smart-homes-in-kapparis-protaras-gd00192/";

	private static CrawlController crawlController;

	@BeforeClass
	public static void init() throws Exception {
		CrawlProperties crawlProperties = new CrawlProperties();
		crawlProperties.setCrawlStorageFolder("/tmp/crawl");
		PageFetcher pageFetcher = new PageFetcher(crawlProperties);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		crawlController = new CrawlController(crawlProperties, pageFetcher, robotstxtServer);
	}

	@Test
	public void testSaleNewVillaDetached() throws Exception {
		Property villa = crawlProperty(SALE_NEW_VILLA_DETACHED_URL).get(0);
		Assert.assertEquals("3 Bedroom Detached Villa For Sale in Ayia Triada Protaras", villa.getTitle());
		Assert.assertEquals(35.043414851659364, villa.getLatitude(), 0.000001);
		Assert.assertEquals(34.024990827478064, villa.getLongitude(), 0.000001);
		Assert.assertEquals(374000, villa.getPrice());
		Assert.assertEquals(PropertyStatus.SALE, villa.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, villa.getOfferStatus());
		Assert.assertFalse(villa.isVatIncluded());
		Assert.assertTrue(villa.isReadyToMoveIn());
		Assert.assertEquals(Furnishing.NONE, villa.getFurnishing());
		Assert.assertEquals(248, villa.getPlotSize());
		Assert.assertEquals(126, villa.getCoveredArea());
		Assert.assertEquals(3, villa.getBedrooms());

		Assert.assertEquals(Arrays.asList(
			"Double glazing",
			"Guests W.C",
			"Optional Private Pool",
			"Pool Shower",
			"Pressurised Water System",
			"Provision for Air-conditioning",
			"Walking distance to the beach"
		), villa.getFeatures(Locale.ENGLISH));
	}

	@Test
	public void testSoldSemiDetachedVilla() throws Exception {
		Property villa = crawlProperty(SOLD_SEMI_DETACHED_VILLA_URL).get(0);
		Assert.assertEquals("2 Bedroom Villa For Sale in Ayia Triada Protaras", villa.getTitle());
		Assert.assertEquals(35.04442938348003, villa.getLatitude(), 0.000001);
		Assert.assertEquals(34.02507126331329, villa.getLongitude(), 0.000001);
		Assert.assertEquals(0, villa.getPrice());
		Assert.assertEquals(PropertyStatus.SALE, villa.getPropertyStatus());
		Assert.assertEquals(OfferStatus.SOLD, villa.getOfferStatus());
		Assert.assertFalse(villa.isReadyToMoveIn());
		Assert.assertEquals(Furnishing.NONE, villa.getFurnishing());
		Assert.assertEquals(146, villa.getPlotSize());
		Assert.assertEquals(96, villa.getCoveredArea());
		Assert.assertEquals(2, villa.getBedrooms());
	}

	@Test
	public void saleNewVillaDetachedTest() throws Exception {
		Assert.assertTrue("Should not crawl properties without price.",
			crawlProperty(SALE_NEW_VILLA_WITHOUT_PRICE_URL).isEmpty());
	}

	private static List<Property> crawlProperty(String url) throws Exception {
		List<Property> result = new ArrayList<>();
		GiovaniCrawler crawler = new GiovaniCrawler(result::add);
		crawler.init(1, crawlController);

		WebURL webURL = new WebURL();
		webURL.setURL(url);

		PageFetchResult fetchResult = crawler.getMyController().getPageFetcher().fetchPage(webURL);
		Page page = new Page(webURL);
		fetchResult.fetchContent(page);

		crawler.visit(page);
		return result;
	}

}
