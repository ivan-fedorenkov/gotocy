package org.gotocy.crawl.giovani;

import org.gotocy.crawl.CrawledProperty;
import org.gotocy.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * @author ifedorenkov
 */
public class GiovaniProperty extends CrawledProperty {

	private static final String CRAWL_SOURCE = "http://giovani.com.cy";
	private static final Pattern TITLE_PATTERN = Pattern.compile("^(?:GDR?\\d+ – )?([\\w\\s\\d]+).*$");

	private final Property targetProperty;

	public GiovaniProperty() {
		targetProperty = new Property();
		targetProperty.setCrawlSource(CRAWL_SOURCE);
		targetProperty.setPropertyStatus(PropertyStatus.SALE);
		targetProperty.setOfferStatus(OfferStatus.ACTIVE);
		targetProperty.setFurnishing(Furnishing.NONE);
		targetProperty.setReadyToMoveIn(true);
	}

	public boolean isSupported() {
		return targetProperty.getTitle() != null && targetProperty.getPropertyType() != null &&
			(targetProperty.getOfferStatus() == OfferStatus.SOLD || targetProperty.getPrice() > 0);
	}

	@Override
	public Property toProperty() {
		return targetProperty;
	}

	public void setCrawlId(String crawlId) {
		targetProperty.setCrawlId(crawlId);
	}

	public void setCrawlUrl(String crawlUrl) {
		targetProperty.setCrawlUrl(crawlUrl);
	}

	public void setTitle(String title) {
		Matcher m = TITLE_PATTERN.matcher(title);
		if (m.matches()) {
			title = Arrays.stream(m.group(1).split(" "))
				.map(String::trim)
				.map(String::toLowerCase)
				.map(t -> Character.toUpperCase(t.charAt(0)) + t.substring(1))
				.collect(joining(" "));
			targetProperty.setTitle(title);
		}
	}

	public void setLatitude(double latitude) {
		targetProperty.setLatitude(latitude);
	}

	public void setLongitude(double longitude) {
		targetProperty.setLongitude(longitude);
	}

	public void setPlotSize(String plotSize) {
		targetProperty.setPlotSize(extractNumber(plotSize));
	}

	public void setCoveredArea(String coveredArea) {
		targetProperty.setCoveredArea(extractSQM(coveredArea));
	}

	public void setPrice(String price) {
		price = price.replaceAll("[\\n\\r\\s]", "").trim();
		if (!price.isEmpty()) {
			targetProperty.setPrice(extractNumber(price));
			if (!price.contains("VAT"))
				targetProperty.setVatIncluded(true);
		}
	}

	public void setSold(String sold) {
		if (sold != null && !sold.trim().isEmpty()) {
			targetProperty.setOfferStatus(OfferStatus.SOLD);
			targetProperty.setReadyToMoveIn(false);
			targetProperty.setPrice(0);
			targetProperty.setVatIncluded(false);
		}
	}

	public void setBedrooms(String bedrooms) {
		targetProperty.setBedrooms(extractNumber(bedrooms));
	}

	public void setFeatures(List<String> features) {
		targetProperty.setFeatures(features, Locale.ENGLISH);
	}

	public void setRepresentativeImage(Image image) {
		targetProperty.setRepresentativeImage(image);
	}

	public void setImages(List<Image> images) {
		targetProperty.setImages(images);
	}

	public void setPropertyType(String propertyType) {
		propertyType = propertyType.replaceAll("[^\\w\\s]", "");
		switch (propertyType.trim().toUpperCase()) {
		case "APARTMENT":
			targetProperty.setPropertyType(PropertyType.APARTMENT);
			break;
		case "BUNGALOW":
		case "TOWNHOUSE":
		case "VILLA DETACHED":
		case "VILLA LINK DETACHED":
		case "VILLA SEMI DETACHED":
			targetProperty.setPropertyType(PropertyType.HOUSE);
			break;
		}
	}

}
