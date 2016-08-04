package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.PropertyCrawler;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://cyprus-realty.info/
 *
 * @author ifedorenkov
 */
public class CyprusRealityCrawler extends PropertyCrawler {

	//	private static final Pattern PROPERTY_PAGE = Pattern.compile("^http://cyprus-realty.info/[\\w-]+/[\\w-]+/(\\d+)$");
	private static final Pattern PROPERTY_PAGE = Pattern.compile("^http://cyprus-realty.info/prodazha-nedvizhimosti-na-kipre/[\\w-]+/(\\d+)$");
	private static final Pattern LATITUDE_PATTERN = Pattern.compile("var latitude1 = (\\d+\\.\\d+);");
	private static final Pattern LONGITUDE_PATTERN = Pattern.compile("var longitude1 = (\\d+\\.\\d+);");

	private final HtmlCleaner htmlCleaner;
	private final DomSerializer domSerializer;

	private final XPathExpression propertyTypeExpression;
	private final XPathExpression titleExpression;
	private final XPathExpression locationExpression;
	private final XPathExpression priceExpression;
	private final XPathExpression offerTypeExpression;
	private final XPathExpression offerStatusExpression;
	private final XPathExpression readyToMoveInExpression;
	//private final XPathExpression descriptionExpression;
	private final XPathExpression specsRowsExpression;
	private final XPathExpression imagesExpression;

	public CyprusRealityCrawler(BiConsumer<Property, List<Image>> propertyConsumer) {
		super(propertyConsumer);

		htmlCleaner = new HtmlCleaner();
		domSerializer = new DomSerializer(new CleanerProperties());

		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			propertyTypeExpression = xpath.compile("*//ul[@id='breadcrumbs']/li[3]/a/text()");
			titleExpression = xpath.compile("*//div[@id='content']/div/h1/text()");
			locationExpression = xpath.compile("*//div[@id='params']/ul/li[2]/span/text()");
			priceExpression = xpath.compile("*//div[@id='params']/ul/li[7]/span[@class='main-price']/text()");
			offerTypeExpression = xpath.compile("*//div[@id='params']/ul/li[5]/span[1]/text()");
			offerStatusExpression = xpath.compile("*//div[@id='gallery']/div[@class='product-status-object']/span/text()");
			readyToMoveInExpression = xpath.compile("*//div[@id='params']/ul/li[6]");
			//descriptionExpression = xpath.compile("*//div[@id='tab-description']/p/text()");
			specsRowsExpression = xpath.compile("*//div[@id='tab-attribute']/div/table/tbody/tr");
			imagesExpression = xpath.compile("*//a[@class='slide']");
		} catch (XPathExpressionException e) {
			getLogger().error("Failed to create crawler instance. Something went wrong with XPath expressions", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTER.matcher(href).matches() && href.startsWith("http://cyprus-realty.info/");
	}

	@Override
	public void visit(Page page) {
		WebURL pageWebURL = page.getWebURL();
		getLogger().debug("Visiting url {}", pageWebURL.getURL());

		Matcher matcher = PROPERTY_PAGE.matcher(pageWebURL.getURL());
		if (matcher.matches()) {
			try {
				String html = new String(page.getContentData(), "UTF-8");
				Document dom = domSerializer.createDOM(htmlCleaner.clean(html));

				CyprusRealityProperty property = new CyprusRealityProperty();

				String sourceId = matcher.group(1);
				property.setCrawlId(sourceId);
				property.setCrawlUrl(pageWebURL.getURL());
				property.setTitle((String) titleExpression.evaluate(dom, XPathConstants.STRING));
				property.setPropertyType((String) propertyTypeExpression.evaluate(dom, XPathConstants.STRING));
				property.setLocation((String) locationExpression.evaluate(dom, XPathConstants.STRING));
				property.setPrice((String) priceExpression.evaluate(dom, XPathConstants.STRING));
				property.setOfferType((String) offerTypeExpression.evaluate(dom, XPathConstants.STRING));
				property.setOfferStatus((String) offerStatusExpression.evaluate(dom, XPathConstants.STRING));
				Node readyToMoveInNode = (Node) readyToMoveInExpression.evaluate(dom, XPathConstants.NODE);
				property.setReadyToMoveIn(readyToMoveInNode.getTextContent());
				//property.setCrawledDescription((String) descriptionExpression.evaluate(dom, XPathConstants.STRING));

				NodeList specNodes = (NodeList) specsRowsExpression.evaluate(dom, XPathConstants.NODESET);
				for (int i = 0; i < specNodes.getLength(); i++) {
					Node specNode = specNodes.item(i);
					// td's
					String specTitle = "";
					String specUnknown = ""; // unknown useless td
					String specValue = "";
					NodeList specSubNodes = specNode.getChildNodes();
					int processedTds = 0;
					for (int j = 0; j < specSubNodes.getLength(); j++) {
						Node specSubNode = specSubNodes.item(j);
						if ("td".equals(specSubNode.getNodeName())) {
							switch (processedTds) {
							case 0:
								specTitle = specSubNode.getTextContent();
								break;
							case 1:
								specUnknown = specSubNode.getTextContent();
								break;
							case 2:
								specValue = specSubNode.getTextContent();
								break;
							}
							processedTds++;
						}
					}

					if (!property.setSpec(specTitle, specUnknown + specValue)) {
						getLogger().warn("{} unknown property spec '{}' with value '{}'",
							pageWebURL.getURL(), specTitle, specValue);
					}
				}

				NodeList imagesNodes = (NodeList) imagesExpression.evaluate(dom, XPathConstants.NODESET);
				List<Image> downloadedImages = downloadImages(pageWebURL, extractImageUrls(imagesNodes, "href"));

				Matcher m = LATITUDE_PATTERN.matcher(html);
				if (m.find())
					property.setLatitude(Double.parseDouble(m.group(1)));

				m = LONGITUDE_PATTERN.matcher(html);
				if (m.find())
					property.setLongitude(Double.parseDouble(m.group(1)));

				if (property.isSupported())
					getPropertyConsumer().accept(property.toProperty(), downloadedImages);

			} catch (Exception e) {
				getLogger().error("Failed to parse property (" + pageWebURL.getURL() + ")", e);
			}
		}
	}

}
