package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.CookieAwarePageFetcher;
import org.gotocy.crawl.CrawlProperties;
import org.gotocy.domain.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class CyprusRealityCrawlerTest {

	private static final String SALE_APARTMENTS_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/apartaments/15001291";

	private static final String SALE_PENTHOUSE_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/penthouse/15001288";

	private static final String SALE_VILLA_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/villas/15000134";

	private static final String SALE_TOWNHOUSE_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/town-house/15000706";

	private static final String SALE_STUDIO_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/studio/15000139";

	private static final String SALE_LAND_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/land/15000943";

	private static final String SALE_MAISONETTE_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/maisonette/15000712";

	private static final String SOLD_STUDIO_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/studio/15000423";

	private static CrawlController crawlController;

	@BeforeClass
	public static void init() throws Exception {
		CrawlProperties crawlProperties = new CrawlProperties();
		crawlProperties.setCrawlStorageFolder("/tmp/crawl");
		crawlProperties.setCrawlerClass("CyprusRealityCrawler");
		CrawlProperties.ClientCookie clientCookie = new CrawlProperties.ClientCookie();
		clientCookie.setDomain(".cyprus-realty.info");
		clientCookie.setName("language");
		clientCookie.setValue("en");
		crawlProperties.setCookies(Collections.singletonList(clientCookie));

		PageFetcher pageFetcher = new CookieAwarePageFetcher(crawlProperties);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		crawlController = new CrawlController(crawlProperties, pageFetcher, robotstxtServer);
	}

	@Test
	public void saleApartmentsTest() throws Exception {
		Property penthouse = crawlProperty(SALE_APARTMENTS_URL);

		Assert.assertEquals(SALE_APARTMENTS_URL, penthouse.getCrawlUrl());
		Assert.assertEquals("15001291", penthouse.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", penthouse.getCrawlSource());

		Assert.assertEquals("Two bedroom apartment in Limassol, Potamos Germasogeia", penthouse.getTitle());
		Assert.assertEquals(Location.LIMASSOL, penthouse.getLocation());
		Assert.assertEquals("Limassol, Potamos Germasogeia", penthouse.getAddress());
		Assert.assertEquals(34.701648D, penthouse.getLatitude(), 0.000001);
		Assert.assertEquals(33.104186D, penthouse.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.APARTMENT, penthouse.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, penthouse.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, penthouse.getOfferStatus());
		Assert.assertEquals(350000, penthouse.getPrice());
		Assert.assertEquals(80, penthouse.getCoveredArea());
		Assert.assertEquals(0, penthouse.getLevels());
		Assert.assertEquals(2, penthouse.getBedrooms());
		Assert.assertEquals(100, penthouse.getDistanceToSea());
		Assert.assertEquals(Furnishing.FULL, penthouse.getFurnishing());
		Assert.assertTrue(penthouse.isReadyToMoveIn());

		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Security"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Veranda"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Storeroom"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Washing machine"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Refrigerator"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("TV"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Internet"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Garden"));
	}

	@Test
	public void salePenthouseTest() throws Exception {
		Property penthouse = crawlProperty(SALE_PENTHOUSE_URL);

		Assert.assertEquals(SALE_PENTHOUSE_URL, penthouse.getCrawlUrl());
		Assert.assertEquals("15001288", penthouse.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", penthouse.getCrawlSource());

		Assert.assertEquals("Penthouse in Limassol with 3 bedrooms, Ekali", penthouse.getTitle());
		Assert.assertEquals(Location.LIMASSOL, penthouse.getLocation());
		Assert.assertEquals("Limassol, Ekali", penthouse.getAddress());
		Assert.assertEquals(34.701718D, penthouse.getLatitude(), 0.000001);
		Assert.assertEquals(33.011420D, penthouse.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.APARTMENT, penthouse.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, penthouse.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, penthouse.getOfferStatus());
		Assert.assertEquals(320000, penthouse.getPrice());
		Assert.assertEquals(138, penthouse.getCoveredArea());
		Assert.assertEquals(0, penthouse.getLevels());
		Assert.assertEquals(3, penthouse.getBedrooms());
		Assert.assertEquals(3000, penthouse.getDistanceToSea());
		Assert.assertEquals(Furnishing.SEMI, penthouse.getFurnishing());
		Assert.assertTrue(penthouse.isReadyToMoveIn());

		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Veranda"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Storeroom"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("BBQ"));
		Assert.assertTrue(penthouse.getFeatures(Locale.ENGLISH).contains("Garden"));
	}

	@Test
	public void saleVillaTest() throws Exception {
		Property villa = crawlProperty(SALE_VILLA_URL);

		Assert.assertEquals(SALE_VILLA_URL, villa.getCrawlUrl());
		Assert.assertEquals("15000134", villa.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", villa.getCrawlSource());

		Assert.assertEquals("Luxury 5 bedroom villa in Limassol, East Beach", villa.getTitle());
		Assert.assertEquals(Location.LIMASSOL, villa.getLocation());
		Assert.assertEquals("Limassol, East Beach", villa.getAddress());
		Assert.assertEquals(34.712712D, villa.getLatitude(), 0.000001);
		Assert.assertEquals(33.165498D, villa.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.HOUSE, villa.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, villa.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, villa.getOfferStatus());
		Assert.assertEquals(10950000, villa.getPrice());
		Assert.assertEquals(670, villa.getCoveredArea());
		Assert.assertEquals(1250, villa.getPlotSize());
		Assert.assertEquals(0, villa.getLevels());
		Assert.assertEquals(5, villa.getBedrooms());
		Assert.assertEquals(50, villa.getDistanceToSea());
		Assert.assertEquals(Furnishing.FULL, villa.getFurnishing());
		Assert.assertTrue(villa.isReadyToMoveIn());

		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Security"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Veranda"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Heat floor"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Storeroom"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Garden"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Pergola"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Sauna"));
		Assert.assertTrue(villa.getFeatures(Locale.ENGLISH).contains("Jacuzzi"));
	}

	@Test
	public void saleTownhouseTest() throws Exception {
		Property townHouse = crawlProperty(SALE_TOWNHOUSE_URL);

		Assert.assertEquals(SALE_TOWNHOUSE_URL, townHouse.getCrawlUrl());
		Assert.assertEquals("15000706", townHouse.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", townHouse.getCrawlSource());

		Assert.assertEquals("Two bedroom townhouse in Paphos, Peyia", townHouse.getTitle());
		Assert.assertEquals(Location.PAPHOS, townHouse.getLocation());
		Assert.assertEquals("Paphos, Peyia", townHouse.getAddress());
		Assert.assertEquals(34.879655D, townHouse.getLatitude(), 0.000001);
		Assert.assertEquals(32.369204D, townHouse.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.HOUSE, townHouse.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, townHouse.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, townHouse.getOfferStatus());
		Assert.assertEquals(175000, townHouse.getPrice());
		Assert.assertEquals(85, townHouse.getCoveredArea());
		Assert.assertEquals(85, townHouse.getPlotSize());
		Assert.assertEquals(0, townHouse.getLevels());
		Assert.assertEquals(2, townHouse.getBedrooms());
		Assert.assertEquals(2500, townHouse.getDistanceToSea());
		Assert.assertEquals(Furnishing.FULL, townHouse.getFurnishing());
		Assert.assertTrue(townHouse.isReadyToMoveIn());

		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Washing machine"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("Refrigerator"));
		Assert.assertTrue(townHouse.getFeatures(Locale.ENGLISH).contains("TV"));
	}

	@Test
	public void saleStudioTest() throws Exception {
		Property studio = crawlProperty(SALE_STUDIO_URL);

		Assert.assertEquals(SALE_STUDIO_URL, studio.getCrawlUrl());
		Assert.assertEquals("15000139", studio.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", studio.getCrawlSource());

		Assert.assertEquals("Studio in Paphos, Peyia", studio.getTitle());
		Assert.assertEquals(Location.PAPHOS, studio.getLocation());
		Assert.assertEquals("Paphos, Peyia", studio.getAddress());
		Assert.assertEquals(34.884100D, studio.getLatitude(), 0.000001);
		Assert.assertEquals(32.384225D, studio.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.APARTMENT, studio.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, studio.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, studio.getOfferStatus());
		Assert.assertEquals(68000, studio.getPrice());
		Assert.assertEquals(33, studio.getCoveredArea());
		Assert.assertEquals(0, studio.getPlotSize());
		Assert.assertEquals(0, studio.getLevels());
		Assert.assertEquals(1, studio.getBedrooms());
		Assert.assertEquals(3000, studio.getDistanceToSea());
		Assert.assertEquals(Furnishing.SEMI, studio.getFurnishing());
		Assert.assertTrue(studio.isReadyToMoveIn());

		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Parking"));
	}

	@Test
	public void saleLandTest() throws Exception {
		Property studio = crawlProperty(SALE_LAND_URL);

		Assert.assertEquals(SALE_LAND_URL, studio.getCrawlUrl());
		Assert.assertEquals("15000943", studio.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", studio.getCrawlSource());

		Assert.assertEquals("Land in Paphos, Polis", studio.getTitle());
		Assert.assertEquals(Location.PAPHOS, studio.getLocation());
		Assert.assertEquals("Paphos, Polis", studio.getAddress());
		Assert.assertEquals(35.065337D, studio.getLatitude(), 0.000001);
		Assert.assertEquals(32.489941D, studio.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.LAND, studio.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, studio.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, studio.getOfferStatus());
		Assert.assertEquals(189000, studio.getPrice());
		Assert.assertEquals(0, studio.getCoveredArea());
		Assert.assertEquals(551, studio.getPlotSize());
		Assert.assertEquals(0, studio.getLevels());
		Assert.assertEquals(0, studio.getBedrooms());
		Assert.assertEquals(1400, studio.getDistanceToSea());
		Assert.assertEquals(Furnishing.NONE, studio.getFurnishing());
		Assert.assertFalse(studio.isReadyToMoveIn());

		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).isEmpty());
	}

	@Test
	public void saleMaisonetteTest() throws Exception {
		Property maisonette = crawlProperty(SALE_MAISONETTE_URL);

		Assert.assertEquals(SALE_MAISONETTE_URL, maisonette.getCrawlUrl());
		Assert.assertEquals("15000712", maisonette.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", maisonette.getCrawlSource());

		Assert.assertEquals("Maisonette in Paphos, with 3 bedrooms, Geroskipou", maisonette.getTitle());
		Assert.assertEquals(Location.PAPHOS, maisonette.getLocation());
		Assert.assertEquals("Paphos, Geroskipou", maisonette.getAddress());
		Assert.assertEquals(34.762084D, maisonette.getLatitude(), 0.000001);
		Assert.assertEquals(32.459430D, maisonette.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.HOUSE, maisonette.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, maisonette.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, maisonette.getOfferStatus());
		Assert.assertEquals(167500, maisonette.getPrice());
		Assert.assertEquals(124, maisonette.getCoveredArea());
		Assert.assertEquals(124, maisonette.getPlotSize());
		Assert.assertEquals(0, maisonette.getLevels());
		Assert.assertEquals(3, maisonette.getBedrooms());
		Assert.assertEquals(2000, maisonette.getDistanceToSea());
		Assert.assertEquals(Furnishing.SEMI, maisonette.getFurnishing());
		Assert.assertTrue(maisonette.isReadyToMoveIn());

		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Balcony"));
		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(maisonette.getFeatures(Locale.ENGLISH).contains("Storeroom"));
	}

	@Test
	public void soldStudioTest() throws Exception {
		Property studio = crawlProperty(SOLD_STUDIO_URL);

		Assert.assertEquals(SOLD_STUDIO_URL, studio.getCrawlUrl());
		Assert.assertEquals("15000423", studio.getCrawlId());
		Assert.assertEquals("cyprus-reality.info", studio.getCrawlSource());

		Assert.assertEquals("Studio in Limassol, Potamos Germasogeia", studio.getTitle());
		Assert.assertEquals(Location.LIMASSOL, studio.getLocation());
		Assert.assertEquals("Limassol, Potamos Germasogeia", studio.getAddress());
		Assert.assertEquals(34.700522D, studio.getLatitude(), 0.000001);
		Assert.assertEquals(33.100304D, studio.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.APARTMENT, studio.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, studio.getPropertyStatus());
		Assert.assertEquals(OfferStatus.SOLD, studio.getOfferStatus());
		Assert.assertEquals(60000, studio.getPrice());
		Assert.assertEquals(35, studio.getCoveredArea());
		Assert.assertEquals(0, studio.getPlotSize());
		Assert.assertEquals(0, studio.getLevels());
		Assert.assertEquals(1, studio.getBedrooms());
		Assert.assertEquals(50, studio.getDistanceToSea());
		Assert.assertEquals(Furnishing.FULL, studio.getFurnishing());
		Assert.assertTrue(studio.isReadyToMoveIn());

		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Air conditioning"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Solar battery"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Equipped kitchen"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Parking"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Washing machine"));
		Assert.assertTrue(studio.getFeatures(Locale.ENGLISH).contains("Refrigerator"));
	}

	private static Property crawlProperty(String url) throws Exception {
		List<Property> result = new ArrayList<>();
		CyprusRealityCrawler crawler = new CyprusRealityCrawler(result::add);
		crawler.init(1, crawlController);

		WebURL webURL = new WebURL();
		webURL.setURL(url);

		PageFetchResult fetchResult = crawler.getMyController().getPageFetcher().fetchPage(webURL);
		Page page = new Page(webURL);
		fetchResult.fetchContent(page);

		crawler.visit(page);
		return result.get(0);
	}

}
