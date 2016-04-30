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

	private static final String SOLD_APARTMENT_WITH_NON_STANDARD_TITLE_URL =
		"http://giovani.com.cy/property/gdr017-1-bedroom-ground-floor-apartment-in-kapparis/";

	private static final String PROPERTY_WITH_DOUBLE_SPACE_IN_TITLE_URL =
		"http://giovani.com.cy/property/4-bedroom-detached-villa-in-agia-triada-protaras/";

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
		Property property = crawlProperty(SALE_NEW_VILLA_DETACHED_URL).get(0);
		Assert.assertEquals("3 Bedroom Detached Villa for Sale in Ayia Triada Protaras", property.getTitle());
		Assert.assertEquals(35.043414851659364, property.getLatitude(), 0.000001);
		Assert.assertEquals(34.024990827478064, property.getLongitude(), 0.000001);
		Assert.assertEquals(374000, property.getPrice());
		Assert.assertEquals(PropertyStatus.SALE, property.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, property.getOfferStatus());
		Assert.assertFalse(property.isVatIncluded());
		Assert.assertTrue(property.isReadyToMoveIn());
		Assert.assertEquals(Furnishing.NONE, property.getFurnishing());
		Assert.assertEquals(248, property.getPlotSize());
		Assert.assertEquals(126, property.getCoveredArea());
		Assert.assertEquals(3, property.getBedrooms());

		Assert.assertEquals(Arrays.asList(
			"Double glazing",
			"Guests W.C",
			"Optional Private Pool",
			"Pool Shower",
			"Pressurised Water System",
			"Provision for Air-conditioning",
			"Walking distance to the beach"
		), property.getFeatures(Locale.ENGLISH));
	}

	@Test
	public void testSoldSemiDetachedVilla() throws Exception {
		Property property = crawlProperty(SOLD_SEMI_DETACHED_VILLA_URL).get(0);
		Assert.assertEquals("2 Bedroom Villa for Sale in Ayia Triada Protaras", property.getTitle());
		Assert.assertEquals(35.04442938348003, property.getLatitude(), 0.000001);
		Assert.assertEquals(34.02507126331329, property.getLongitude(), 0.000001);
		Assert.assertEquals(0, property.getPrice());
		Assert.assertEquals(PropertyStatus.SALE, property.getPropertyStatus());
		Assert.assertEquals(OfferStatus.SOLD, property.getOfferStatus());
		Assert.assertFalse(property.isReadyToMoveIn());
		Assert.assertEquals(Furnishing.NONE, property.getFurnishing());
		Assert.assertEquals(146, property.getPlotSize());
		Assert.assertEquals(96, property.getCoveredArea());
		Assert.assertEquals(2, property.getBedrooms());
	}

	@Test
	public void testSoldApartmentWithNonStandardTitle() throws Exception {
		Property property = crawlProperty(SOLD_APARTMENT_WITH_NON_STANDARD_TITLE_URL).get(0);
		Assert.assertEquals("1 Bedroom Ground Floor Apartment in Kapparis", property.getTitle());
		Assert.assertEquals(35.05267905030267, property.getLatitude(), 0.000001);
		Assert.assertEquals(33.99892363323988, property.getLongitude(), 0.000001);
		Assert.assertEquals(0, property.getPrice());
		Assert.assertEquals(PropertyStatus.SALE, property.getPropertyStatus());
		Assert.assertEquals(OfferStatus.SOLD, property.getOfferStatus());
		Assert.assertFalse(property.isVatIncluded());
		Assert.assertFalse(property.isReadyToMoveIn());
		Assert.assertEquals(Furnishing.NONE, property.getFurnishing());
		Assert.assertEquals(0, property.getPlotSize());
		Assert.assertEquals(50, property.getCoveredArea());
		Assert.assertEquals(1, property.getBedrooms());
	}

	@Test
	public void saleNewVillaDetachedTest() throws Exception {
		Assert.assertTrue("Should not crawl properties without price.",
			crawlProperty(SALE_NEW_VILLA_WITHOUT_PRICE_URL).isEmpty());
	}

	@Test
	public void testPropertyWithDoubleSpaceInTitle() throws Exception {
		Property property = crawlProperty(PROPERTY_WITH_DOUBLE_SPACE_IN_TITLE_URL).get(0);
		Assert.assertEquals("4 Bedroom Detached Villa in Agia Triada Protaras", property.getTitle());
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
