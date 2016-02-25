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

		Assert.assertTrue(penthouse.getFeatures().contains("Air conditioning"));
		Assert.assertTrue(penthouse.getFeatures().contains("Balcony"));
		Assert.assertTrue(penthouse.getFeatures().contains("Veranda"));
		Assert.assertTrue(penthouse.getFeatures().contains("Solar battery"));
		Assert.assertTrue(penthouse.getFeatures().contains("Equipped kitchen"));
		Assert.assertTrue(penthouse.getFeatures().contains("Parking"));
		Assert.assertTrue(penthouse.getFeatures().contains("Storeroom"));
		Assert.assertTrue(penthouse.getFeatures().contains("BBQ"));
		Assert.assertTrue(penthouse.getFeatures().contains("Garden"));
	}

	@Test
	public void saleVillaTest() throws Exception {
		Property villa = crawlProperty(SALE_VILLA_URL);
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
