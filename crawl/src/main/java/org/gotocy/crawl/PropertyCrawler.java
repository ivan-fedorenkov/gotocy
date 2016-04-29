package org.gotocy.crawl;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.Getter;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author ifedorenkov
 */
@Getter
public abstract class PropertyCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(PropertyCrawler.class);

	protected static final Pattern FILTER = Pattern.compile(".*(\\.(css|js|gif|jpg|jpeg|png|mp3|mp3|zip|gz))$");

	private final Consumer<Property> propertyConsumer;

	public PropertyCrawler(Consumer<Property> propertyConsumer) {
		this.propertyConsumer = propertyConsumer;
	}

	protected Logger getLogger() {
		return logger;
	}

	/**
	 * Extracts image urls by iterating over the provided nodes using the specified image url attribute name.
	 *
	 * @param imagesNodes nodes with image urls in some attribute
	 * @param urlAttributeName name of attribute with image url
	 * @return list of image urls
	 */
	protected List<String> extractImageUrls(NodeList imagesNodes, String urlAttributeName) {
		List<String> imageUrls = new ArrayList<>();
		if (imagesNodes != null && imagesNodes.getLength() > 0) {
			for (int i = 0; i < imagesNodes.getLength(); i++) {
				Node imageNode = imagesNodes.item(i);
				if (imageNode != null) {
					Node imageHref = imageNode.getAttributes().getNamedItem(urlAttributeName);
					if (imageHref != null) {
						imageUrls.add(imageHref.getNodeValue());
					}
				}
			}
		}
		return imageUrls;
	}

	/**
	 * Returns a list of downloaded images represented by the {@link Image} domain object.
	 * Note: supports only jpg/jpeg images.
	 */
	protected List<Image> downloadImages(WebURL pageWebURL, List<String> imageUrls) {
		List<PageFetchResult> fetchedImages = new ArrayList<>(imageUrls.size());
		for (String imageUrl : imageUrls) {
			if (!imageUrl.toLowerCase().endsWith("jpg") && !imageUrl.toLowerCase().endsWith("jpeg")) {
				getLogger().warn("{} unsupported image format {}", pageWebURL.getURL(), imageUrl);
				continue;
			}

			WebURL webURL = new WebURL();
			webURL.setURL(imageUrl);
			webURL.setParentDocid(pageWebURL.getParentDocid());
			webURL.setParentUrl(pageWebURL.getParentUrl());
			webURL.setDepth(pageWebURL.getDepth());
			webURL.setDocid(-1);
			webURL.setAnchor(pageWebURL.getAnchor());

			try {
				fetchedImages.add(getMyController().getPageFetcher().fetchPage(webURL));
			} catch (Exception e) {
				getLogger().error("{} failed to fetch image {}", pageWebURL.getURL(), imageUrl);
			}
		}

		List<Image> images = new ArrayList<>(fetchedImages.size());
		for (int i = 0; i < fetchedImages.size(); i++) {
			PageFetchResult fetchedImage = fetchedImages.get(i);
			try {
				Image image = new Image();
				image.setBytes(StreamUtils.copyToByteArray(fetchedImage.getEntity().getContent()));
				image.setKey("" + i + ".jpg");
				images.add(image);
			} catch (IOException ioe) {
				getLogger().error("{} failed to set image bytes {}", pageWebURL.getURL(), imageUrls);
			}
		}

		return images;
	}

}
