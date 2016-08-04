package org.gotocy.crawl.mayfair;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.client.utils.URIBuilder;
import org.gotocy.crawl.PropertyCrawler;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.util.StreamUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * http://www.mayfair-cyprus.com/
 *
 * @author ifedorenkov
 */
public class MayfairCrawler extends PropertyCrawler {

	private static final Pattern PROPERTY_PAGE = Pattern.compile("^http://www.mayfair-cyprus.com/easyconsole.cfm/page/property_viewer/pr_id/\\d+$");
	private static final String LARGE_IMAGE_URL_PREFIX = "http://www.mayfair-cyprus.com/assets/image/imagelarge";
	private static final int MAX_PROPERTIES = 10;

	private final HtmlCleaner htmlCleaner;
	private final DomSerializer domSerializer;

	private final XPathExpression refNumExpression;
	private final XPathExpression titleExpression;
	private final XPathExpression propertyStatusExpression;
	private final XPathExpression propertyDescriptionExpression;
	private final XPathExpression featuresExpression;
	private final XPathExpression priceExpression;
	private final XPathExpression imagesExpression;

	private int i = 0;

	public MayfairCrawler(BiConsumer<Property, List<Image>> consumer) {
		super(consumer);

		htmlCleaner = new HtmlCleaner();
		domSerializer = new DomSerializer(new CleanerProperties());

		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			refNumExpression = xpath.compile("*//div[@id='RightPropertyInfo']/div/div[3]/div[1]/table/tbody/tr/td[2]/span/text()");
			titleExpression = xpath.compile("*//h1[@class='propertytitle']/text()");
			propertyStatusExpression = xpath.compile("*//h2[@class='propertystatus'][2]/text()");
			propertyDescriptionExpression = xpath.compile("*//div[@id='mainColumnContainInside']/div[6]/p/text()");
			featuresExpression = xpath.compile("*//ul[@id='ProperyInfo']/li");
			priceExpression = xpath.compile("*//h3[@class='priceonsearch']/text()");
			imagesExpression = xpath.compile("*//div[@id='theImages']/div/a/img");
		} catch (XPathExpressionException e) {
			throw new RuntimeException("Can't create a crawler instance.", e);
		}
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTER.matcher(href).matches() && href.startsWith("http://www.mayfair-cyprus.com/");
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

				MayfairProperty property = new MayfairProperty();

				property.setRefNumber((String) refNumExpression.evaluate(htmlDoc, XPathConstants.STRING));

				if (property.getRefNumber() == null || property.getRefNumber().trim().isEmpty())
					return;

				property.setTitle((String) titleExpression.evaluate(htmlDoc, XPathConstants.STRING));
				property.setPrice(extractPrice((String) priceExpression.evaluate(htmlDoc, XPathConstants.STRING)));
				property.setPropertyStatus((String) propertyStatusExpression.evaluate(htmlDoc, XPathConstants.STRING));
				property.setDescription((String) propertyDescriptionExpression.evaluate(htmlDoc, XPathConstants.STRING));
				property.setFeatures(extractFeatures((NodeList) featuresExpression.evaluate(htmlDoc, XPathConstants.NODESET)));

				NodeList images = (NodeList) imagesExpression.evaluate(htmlDoc, XPathConstants.NODESET);
				if (images != null && images.getLength() > 0) {
					Set<String> imageUrls = new HashSet<>(images.getLength());
					for (int i = 0; i < images.getLength(); i++) {
						Node image = images.item(i);
						Node imageSrc = image.getAttributes().getNamedItem("src");
						if (imageSrc != null) {
							String imageSrcSmall = imageSrc.getNodeValue();
							if (imageSrcSmall != null && !imageSrcSmall.isEmpty()) {
								URIBuilder uriBuilder = new URIBuilder();
								uriBuilder.setPath(LARGE_IMAGE_URL_PREFIX +
									imageSrcSmall.substring(imageSrcSmall.lastIndexOf('/')));
								imageUrls.add(uriBuilder.toString());
							}
						}
					}

					List<PageFetchResult> downloadedImages = new ArrayList<>(imageUrls.size());
					for (String imageUrl : imageUrls) {
						WebURL webURL = new WebURL();
						webURL.setURL(imageUrl);
						webURL.setParentDocid(pageWebURL.getParentDocid());
						webURL.setParentUrl(pageWebURL.getParentUrl());
						webURL.setDepth(pageWebURL.getDepth());
						webURL.setDocid(-1);
						webURL.setAnchor(pageWebURL.getAnchor());

						try {
							downloadedImages.add(getMyController().getPageFetcher().fetchPage(webURL));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					List<Image> propertyImages = new ArrayList<>(downloadedImages.size());
					for (PageFetchResult downloadedImage : downloadedImages) {
						try {
							Image propertyImage = new Image();
							propertyImage.setBytes(StreamUtils.copyToByteArray(downloadedImage.getEntity().getContent()));
							propertyImages.add(propertyImage);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
					property.setImages(propertyImages);

//					getPropertyConsumer().accept(property);

					if (i++ == MAX_PROPERTIES)
						getMyController().shutdown();
				}

			} catch (XPathExpressionException | ParserConfigurationException e) {
				e.printStackTrace();
			}

		}

	}

	private static List<String> extractFeatures(NodeList nodes) {
		if (nodes == null || nodes.getLength() == 0)
			return Collections.emptyList();

		List<String> features = new ArrayList<>(nodes.getLength());
		for (int i = 0; i < nodes.getLength(); i++)
			features.add(nodes.item(i).getTextContent());
		return features;
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
