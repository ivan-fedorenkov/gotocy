package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.CookieAwarePageFetcher;
import org.gotocy.crawl.CrawlProperties;
import org.gotocy.domain.Property;
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
		List<Property> result = new ArrayList<>();
		CyprusRealityCrawler crawler = new CyprusRealityCrawler(result::add);

		WebURL url = new WebURL();
		url.setURL(SALE_PENTHOUSE_URL);

		PageFetchResult fetchResult = pageFetcher.fetchPage(url);
		Page page = new Page(url);
		fetchResult.fetchContent(page);

		crawler.visit(page);
	}

}
