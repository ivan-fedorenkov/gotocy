package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
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

	private static final String SALE_PENTHOUSE_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/penthouse/15001288";

	private static final String SALE_VILLA_URL =
		"http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/villas/15000134";

	private static PageFetcher pageFetcher;

	@BeforeClass
	public static void init() {
		CrawlProperties crawlProperties = new CrawlProperties();
		crawlProperties.setCrawlerClass("CyprusRealityCrawler");
		CrawlProperties.ClientCookie clientCookie = new CrawlProperties.ClientCookie();
		clientCookie.setDomain(".cyprus-realty.info");
		clientCookie.setName("language");
		clientCookie.setValue("en");
		crawlProperties.setCookies(Collections.singletonList(clientCookie));

		pageFetcher = new CookieAwarePageFetcher(crawlProperties);
	}

	@Test
	public void salePenthouseTest() throws Exception {
		Property penthouse = crawlProperty(SALE_PENTHOUSE_URL);

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

		Assert.assertEquals("Luxury 5 bedroom villa in Limassol, Tourist Area", villa.getTitle());
		Assert.assertEquals(Location.LIMASSOL, villa.getLocation());
		Assert.assertEquals("Limassol, Tourist Area", villa.getAddress());
		Assert.assertEquals(34.712712D, villa.getLatitude(), 0.000001);
		Assert.assertEquals(33.165498D, villa.getLongitude(), 0.000001);
		Assert.assertEquals(PropertyType.HOUSE, villa.getPropertyType());
		Assert.assertEquals(PropertyStatus.SALE, villa.getPropertyStatus());
		Assert.assertEquals(OfferStatus.ACTIVE, villa.getOfferStatus());
		Assert.assertEquals(10950000, villa.getPrice());
		Assert.assertEquals(670, villa.getCoveredArea());
		Assert.assertEquals(1250, villa.getPlotSize());
		Assert.assertEquals(0, villa.getLevels());
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

	private static Property crawlProperty(String url) throws Exception {
		List<Property> result = new ArrayList<>();
		CyprusRealityCrawler crawler = new CyprusRealityCrawler(result::add);

		WebURL webURL = new WebURL();
		webURL.setURL(url);

		PageFetchResult fetchResult = pageFetcher.fetchPage(webURL);
		Page page = new Page(webURL);
		fetchResult.fetchContent(page);

		crawler.visit(page);
		return result.get(0);
	}

}
