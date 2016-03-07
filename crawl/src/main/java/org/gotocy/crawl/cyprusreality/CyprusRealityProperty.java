package org.gotocy.crawl.cyprusreality;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class CyprusRealityProperty {

	private static final String CRAWL_SOURCE = "cyprus-reality.info";

	private static final String SQ_M = "m&sup2;";

	Property targetProperty;

	public CyprusRealityProperty() {
		targetProperty = new Property();
		targetProperty.setFurnishing(Furnishing.NONE); // default furnishing
		targetProperty.setOfferStatus(OfferStatus.ACTIVE); // default offer status
		targetProperty.setCrawlSource(CRAWL_SOURCE);
	}

	public void setTitle(String title) {
		targetProperty.setTitle(title.trim());
	}

	public void setCrawlId(String crawlId) {
		targetProperty.setCrawlId(crawlId);
	}

	public void setCrawlUrl(String crawlUrl) {
		targetProperty.setCrawlUrl(crawlUrl);
	}

	public void setLatitude(double latitude) {
		targetProperty.setLatitude(latitude);
	}

	public void setLongitude(double longitude) {
		targetProperty.setLongitude(longitude);
	}

	public void setRepresentativeImage(Image image) {
		targetProperty.setRepresentativeImage(image);
	}

	public void setImages(List<Image> images) {
		targetProperty.setImages(images);
	}

	public void setLocation(String location) {
		targetProperty.setAddress(location.trim());
		int commaIndex = location.indexOf(',');
		if (commaIndex > 0)
			location = location.substring(0, commaIndex);
		switch (location) {
		case "Ayia Napa":
			targetProperty.setLocation(Location.AYIA_NAPA);
			break;
		case "Protaras":
			targetProperty.setLocation(Location.PROTARAS);
			break;
		case "Larnaca":
			targetProperty.setLocation(Location.LARNACA);
			break;
		case "Limassol":
			targetProperty.setLocation(Location.LIMASSOL);
			break;
		case "Troodos":
			targetProperty.setLocation(Location.TROODOS);
			break;
		case "Nicosia":
			targetProperty.setLocation(Location.NICOSIA);
			break;
		case "Paphos":
			targetProperty.setLocation(Location.PAPHOS);
			break;
		}
	}

	public void setPropertyType(String propertyType) {
		switch (propertyType.trim()) {
		case "Apartaments":
		case "Penthouse":
		case "Studio":
			targetProperty.setPropertyType(PropertyType.APARTMENT);
			break;
		case "Townhouse":
		case "Villas":
		case "Maisonette":
			targetProperty.setPropertyType(PropertyType.HOUSE);
			break;
		case "Land":
			targetProperty.setPropertyType(PropertyType.LAND);
			break;
		}
	}

	public void setOfferType(String offerType) {
		switch (offerType.trim()) {
		case "Property for sale":
			targetProperty.setPropertyStatus(PropertyStatus.SALE);
			break;
		case "Short-term rent":
			targetProperty.setPropertyStatus(PropertyStatus.SHORT_TERM);
			break;
		case "Long-term rent":
			targetProperty.setPropertyStatus(PropertyStatus.LONG_TERM);
			break;
		}
	}

	public void setOfferStatus(String offerStatus) {
		if (offerStatus != null) {
			switch (offerStatus.trim()) {
			case "Sold out":
				targetProperty.setOfferStatus(OfferStatus.SOLD);
				break;
			}
		}
	}

	public void setPrice(String priceString) {
		targetProperty.setPrice(extractNumber(priceString.trim()));
	}

	public void setCrawledDescription(String description) {
		if (description.startsWith("\r\n"))
			description = description.substring(2).trim();
		targetProperty.setDescription(description, Locale.ENGLISH);
	}

	public void setReadyToMoveIn(String readyToMoveInString) {
		targetProperty.setReadyToMoveIn("Ready to move".equals(readyToMoveInString.trim()));
	}

	public boolean setSpec(String specTitle, String specValue) {
		if (specTitle == null || specValue == null)
			return false;

		specTitle = specTitle.trim();
		if (specTitle.isEmpty())
			return false;

		if (specTitle.endsWith(":"))
			specTitle = specTitle.substring(0, specTitle.length() - 1);

		specValue = specValue.trim();

		switch (specTitle) {
		case "Furniture":
			targetProperty.setFurnishing(extractFurnishing(specValue));
			break;
		case "To sea":
			targetProperty.setDistanceToSea(extractNumber(specValue));
			break;
		case "Plot area":
			targetProperty.setPlotSize(extractSQM(specValue));
			break;
		case "Bedrooms":
			targetProperty.setBedrooms(extractBedrooms(specValue));
			break;
		case "Covered area":
			targetProperty.setCoveredArea(extractSQM(specValue));
			// Some house types don't specify plot size (e.g. townhouse, maisonette),
			// so set the plot size equal to the the covered area
			if (targetProperty.getPropertyType() == PropertyType.HOUSE && targetProperty.getPlotSize() == 0)
				targetProperty.setPlotSize(targetProperty.getCoveredArea());
			break;
		case "Additional features":
			// All Land properties has a single 'Garden' feature which is useless
			List<String> features = extractAdditionalFeatures(specValue);
			if (targetProperty.getPropertyType() == PropertyType.LAND)
				features.removeIf("Garden"::equals);
			targetProperty.setFeatures(features, Locale.ENGLISH);
			break;
		case "Bathrooms":
		case "Swimming pool":
		case "Distance to supermarket":
		case "Built year":
			// ignore known useless specs
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * Determines if the given Cyprus Reality property is currently supported.
	 */
	public boolean isSupported() {
		return targetProperty.getLocation() != null && targetProperty.getPropertyType() != null &&
			targetProperty.getPropertyStatus() != null && targetProperty.getPropertyStatus() == PropertyStatus.SALE;
	}

	private static int extractNumber(String numberString) {
		int number = 0;

		if (numberString == null || numberString.isEmpty())
			return number;

		for (int i = 0; i < numberString.length(); i++) {
			char c = numberString.charAt(i);
			if (c >= '0' && c <= '9')
				number = number * 10 + (c - '0');
		}

		return number;
	}

	private static int extractSQM(String sqm) {
		if (sqm.endsWith(SQ_M))
			sqm = sqm.substring(0, sqm.length() - SQ_M.length()).trim();
		return extractNumber(sqm);
	}

	private static int extractBedrooms(String bedroomsString) {
		if ("Studio".equals(bedroomsString))
			return 1;
		if ("5 и более".equals(bedroomsString))
			return 5;
		return extractNumber(bedroomsString);
	}

	private static Furnishing extractFurnishing(String furnishingString) {
		switch (furnishingString) {
		case "Partly":
			return Furnishing.SEMI;
		case "Full":
			return Furnishing.FULL;
		default:
			return Furnishing.NONE;
		}
	}

	private static List<String> extractAdditionalFeatures(String additionalFeaturesString) {
		return Arrays.stream(additionalFeaturesString.split("•"))
			.map(String::trim)
			.map(f -> "Praking".equals(f) ? "Parking" : f) // fix typo
			.collect(toList());
	}

}
