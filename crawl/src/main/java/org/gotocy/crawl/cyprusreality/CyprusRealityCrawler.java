package org.gotocy.crawl.cyprusreality;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.gotocy.crawl.PropertyCrawler;
import org.gotocy.domain.Property;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.util.function.Consumer;
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

	private final XPathExpression titleExpression;
	private final XPathExpression primarySpecRowExpression;

	public CyprusRealityCrawler(Consumer<Property> propertyConsumer) {
		super(propertyConsumer);

		htmlCleaner = new HtmlCleaner();
		domSerializer = new DomSerializer(new CleanerProperties());

		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			titleExpression = xpath.compile("*//div[@id='content']/div/h1/text()");
			primarySpecRowExpression = xpath.compile("*//div[@id='tab-attribute']/div/table/tbody/tr");
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
		String url = pageWebURL.getURL();
		System.out.println("URL: " + url);

		if (PROPERTY_PAGE.matcher(url).matches() && (page.getParseData() instanceof HtmlParseData)) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

			try {
				String html = htmlParseData.getHtml();
				TagNode tagNode = htmlCleaner.clean(html);
				Document htmlDoc = domSerializer.createDOM(tagNode);

				CyprusRealityProperty property = new CyprusRealityProperty();

				property.setTitle((String) titleExpression.evaluate(htmlDoc, XPathConstants.STRING));
				NodeList featuresNodes = (NodeList) primarySpecRowExpression.evaluate(htmlDoc, XPathConstants.NODESET);
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
					property.getFeatures().put(featureTitle, featureUnknown + featureValue);
				}
				property.setUrl(url);

				getPropertyConsumer().accept(property.toProperty());

			} catch (XPathExpressionException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

}
