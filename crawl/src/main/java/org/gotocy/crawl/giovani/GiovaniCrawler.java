package org.gotocy.crawl.giovani;

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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://giovani.com.cy/
 *
 * @author ifedorenkov
 */
public class GiovaniCrawler extends PropertyCrawler {

	private static final Pattern PROPERTY_PAGE = Pattern.compile("^http://giovani.com.cy/property/([\\w\\d-]+)/$");
	private static final Pattern LAT_LNG_PATTERN = Pattern.compile("LatLng\\((\\d+\\.\\d+),(\\d+\\.\\d+)\\)");
	private static final Pattern ID_PATTERN = Pattern.compile("Property ID : ([\\d\\w]+)");
	private static final Pattern PLOT_SIZE_PATTERN = Pattern.compile("Plot Size : (\\d+)");

	private final HtmlCleaner htmlCleaner;
	private final DomSerializer domSerializer;

	private final XPathExpression idAndPlotSizeExpression;
	private final XPathExpression titleExpression;
	private final XPathExpression priceExpression;
	private final XPathExpression typeExpression;
	private final XPathExpression coveredAreaExpression;
	private final XPathExpression bedroomsExpression;
	private final XPathExpression featuresListExpression;
	private final XPathExpression soldFlagExpression;
	private final XPathExpression imagesExpression;

	public GiovaniCrawler(Consumer<Property> propertyConsumer) {
		super(propertyConsumer);

		htmlCleaner = new HtmlCleaner();
		domSerializer = new DomSerializer(new CleanerProperties());

		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			idAndPlotSizeExpression = xpath.compile("*//div[@id='overview']//h4[@class='title']/text()");
			titleExpression = xpath.compile("*//div[@class='page-head']//div[@class='container']//div[@class='wrap clearfix']/p/text()");
			priceExpression = xpath.compile("*//div[@id='overview']//h5[@class='price']/span[2]/text()");
			typeExpression = xpath.compile("*//div[@id='overview']//h5[@class='price']/span[2]/small/text()");
			coveredAreaExpression = xpath.compile("*//div[@id='overview']//div[contains(@class, 'property-meta')]/span[1]/text()");
			bedroomsExpression = xpath.compile("*//div[@id='overview']//div[contains(@class, 'property-meta')]/span[2]/text()");
			featuresListExpression = xpath.compile("*//div[@id='overview']//div[@class='features']//li/a/text()");
			soldFlagExpression = xpath.compile("*//div[@id='property-detail-flexslider']/div[contains(@class, 'corner-ribbon')]");
			imagesExpression = xpath.compile("*//ul[@class='slides']/li/a");
		} catch (XPathExpressionException e) {
			getLogger().error("Failed to create crawler instance. Something went wrong with XPath expressions", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTER.matcher(href).matches() && href.startsWith("http://giovani.com.cy/");
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

				GiovaniProperty property = new GiovaniProperty();
				property.setCrawlUrl(pageWebURL.getURL());

				String idAndPlotSize = (String) idAndPlotSizeExpression.evaluate(dom, XPathConstants.STRING);
				Matcher idMatcher = ID_PATTERN.matcher(idAndPlotSize);
				if (idMatcher.find())
					property.setCrawlId(idMatcher.group(1));

				Matcher plotSizeMatcher = PLOT_SIZE_PATTERN.matcher(idAndPlotSize);
				if (plotSizeMatcher.find())
					property.setPlotSize(plotSizeMatcher.group(1));

				Matcher m = LAT_LNG_PATTERN.matcher(html);
				if (m.find()) {
					try {
						property.setLatitude(Double.parseDouble(m.group(1)));
						property.setLongitude(Double.parseDouble(m.group(2)));
					} catch (NumberFormatException nfe) {
						getLogger().warn("{} Failed to obtain LatLng");
					}
				}

				property.setTitle((String) titleExpression.evaluate(dom, XPathConstants.STRING));
				property.setCoveredArea((String) coveredAreaExpression.evaluate(dom, XPathConstants.STRING));
				property.setPrice((String) priceExpression.evaluate(dom, XPathConstants.STRING));
				property.setBedrooms((String) bedroomsExpression.evaluate(dom, XPathConstants.STRING));
				property.setPropertyType((String) typeExpression.evaluate(dom, XPathConstants.STRING));
				property.setSold((String) soldFlagExpression.evaluate(dom, XPathConstants.STRING));

				List<String> features = new ArrayList<>();
				NodeList featuresNodes = (NodeList) featuresListExpression.evaluate(dom, XPathConstants.NODESET);
				for (int i = 0; i < featuresNodes.getLength(); i++) {
					Node featureNode = featuresNodes.item(i);
					if (featureNode.getNodeType() != Node.TEXT_NODE) {
						getLogger().warn("{} Found suspicious feature node: {}, type: {}, text content: {}",
							pageWebURL.getURL(), featureNode.getNodeName(), featureNode.getNodeType(),
							featureNode.getTextContent());
						continue;
					}
					features.add(featureNode.getTextContent());
				}
				if (!features.isEmpty())
					property.setFeatures(features);

				NodeList imagesNodes = (NodeList) imagesExpression.evaluate(dom, XPathConstants.NODESET);
				List<Image> downloadedImages = downloadImages(pageWebURL, extractImageUrls(imagesNodes, "href"));
				if (!downloadedImages.isEmpty()) {
					property.setRepresentativeImage(downloadedImages.get(0));
					property.setImages(downloadedImages);
				}

				if (property.isSupported())
					getPropertyConsumer().accept(property.toProperty());

			} catch (Exception e) {
				getLogger().error("Failed to parse property (" + pageWebURL.getURL() + ")", e);
			}
		}
	}

}
