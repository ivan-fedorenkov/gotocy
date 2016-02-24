package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.PropertyCrawler;
import org.gotocy.domain.Property;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;
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

	private final HtmlCleaner htmlCleaner;
	private final DomSerializer domSerializer;

	private final XPathExpression propertyTypeExpression;
	private final XPathExpression titleExpression;
	private final XPathExpression locationExpression;
	private final XPathExpression priceExpression;
	private final XPathExpression offerTypeExpression;
	private final XPathExpression readyToMoveInExpression;
	private final XPathExpression descriptionExpression;
	private final XPathExpression featuresRowsExpression;

	public CyprusRealityCrawler(Consumer<Property> propertyConsumer) {
		super(propertyConsumer);

		htmlCleaner = new HtmlCleaner();
		domSerializer = new DomSerializer(new CleanerProperties());

		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			propertyTypeExpression = xpath.compile("*//ul[@id='breadcrumbs']/li[3]/a/text()");
			titleExpression = xpath.compile("*//div[@id='content']/div/h1/text()");
			locationExpression = xpath.compile("*//div[@id='params']/ul/li[3]/span[2]/text()");
			priceExpression = xpath.compile("*//div[@id='params']/ul/li[6]/span[@class='main-price']/text()");
			offerTypeExpression = xpath.compile("*//div[@id='params']/ul/li[4]/span[1]/text()");
			readyToMoveInExpression = xpath.compile("*//div[@id='params']/ul/li[5]");
			descriptionExpression = xpath.compile("*//div[@id='tab-description']/p/text()");
			featuresRowsExpression = xpath.compile("*//div[@id='tab-attribute']/div/table/tbody/tr");
		} catch (XPathExpressionException e) {
			throw new RuntimeException("Can't create a crawler instance.", e);
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

		Matcher matcher = PROPERTY_PAGE.matcher(pageWebURL.getURL());
		if (matcher.matches()) {
			try {
				String html = new String(page.getContentData(), "UTF-8");
				Document dom = domSerializer.createDOM(htmlCleaner.clean(html));

				CyprusRealityProperty property = new CyprusRealityProperty();

				String sourceId = matcher.group(1);
				property.setSourceId(sourceId);
				property.setSourceUrl(pageWebURL.getURL());
				property.setTitle((String) titleExpression.evaluate(dom, XPathConstants.STRING));
				property.setPropertyType((String) propertyTypeExpression.evaluate(dom, XPathConstants.STRING));
				property.setLocation((String) locationExpression.evaluate(dom, XPathConstants.STRING));
				property.setPrice(extractPrice((String) priceExpression.evaluate(dom, XPathConstants.STRING)));
				property.setOfferType((String) offerTypeExpression.evaluate(dom, XPathConstants.STRING));
				Node readyToMoveInNode = (Node) readyToMoveInExpression.evaluate(dom, XPathConstants.NODE);
				property.setReadyToMoveIn(readyToMoveInNode.getTextContent());
				property.setDescription((String) descriptionExpression.evaluate(dom, XPathConstants.STRING));

				NodeList featuresNodes = (NodeList) featuresRowsExpression.evaluate(dom, XPathConstants.NODESET);
				for (int i = 0; i < featuresNodes.getLength(); i++) {
					Node featureNode = featuresNodes.item(i);
					// td's
					String featureTitle = "";
					String featureUnknown = "";
					String featureValue = "";
					NodeList featureSubNodes = featureNode.getChildNodes();
					int processedTds = 0;
					for (int j = 0; j < featureSubNodes.getLength(); j++) {
						Node featureSubNode = featureSubNodes.item(j);
						if ("td".equals(featureSubNode.getNodeName())) {
							switch (processedTds) {
							case 0:
								featureTitle = featureSubNode.getTextContent();
								break;
							case 1:
								featureUnknown = featureSubNode.getTextContent();
								break;
							case 2:
								featureValue = featureSubNode.getTextContent();
								break;
							}
							processedTds++;
						}
					}
					property.getFeatures().add(featureTitle + featureUnknown + featureValue);
				}

				getPropertyConsumer().accept(property.toProperty());

			} catch (UnsupportedEncodingException | XPathExpressionException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	private static int extractPrice(String priceString) {
		int price = 0;

		if (priceString == null || priceString.isEmpty())
			return price;

		for (int i = 0; i < priceString.length(); i++) {
			char c = priceString.charAt(i);
			if (c >= '0' && c <= '9')
				price = price * 10 + (c - '0');
		}

		return price;
	}

}
