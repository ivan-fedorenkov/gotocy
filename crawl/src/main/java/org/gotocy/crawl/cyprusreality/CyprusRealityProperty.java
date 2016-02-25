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
public class CyprusRealityProperty extends Property {

	private static final String SQ_M = "m&sup2;";

	private String sourceUrl;
	private String sourceId;

	public CyprusRealityProperty() {
		setFurnishing(Furnishing.NONE); // default furnishing
		setOfferStatus(OfferStatus.ACTIVE); // default offer status
	}

	public void setTitle(String title) {
		super.setTitle(title.trim());
	}

	public void setLocation(String location) {
		setAddress(location.trim());
		switch (location.substring(0, location.indexOf(','))) {
		case "Ayia Napa":
		case "Protaras":
			setLocation(Location.FAMAGUSTA);
			break;
		case "Larnaca":
			setLocation(Location.LARNACA);
			break;
		case "Limassol":
		case "Troodos":
			setLocation(Location.LIMASSOL);
			break;
		case "Nicosia":
			setLocation(Location.NICOSIA);
			break;
		case "Paphos":
			setLocation(Location.PAPHOS);
			break;
		}
	}

	public void setPropertyType(String propertyType) {
		switch (propertyType.trim()) {
		case "Apartaments":
		case "Penthouse":
		case "Studio":
			setPropertyType(PropertyType.APARTMENT);
			break;
		case "Townhouse":
		case "Villas":
		case "Maisonette":
			setPropertyType(PropertyType.HOUSE);
			break;
		case "Land":
			setPropertyType(PropertyType.LAND);
			break;
		}
	}

	public void setOfferType(String offerType) {
		switch (offerType.trim()) {
		case "Property for sale":
			setPropertyStatus(PropertyStatus.SALE);
			break;
		case "Short-term rent":
			setPropertyStatus(PropertyStatus.SHORT_TERM);
			break;
		case "Long-term rent":
			setPropertyStatus(PropertyStatus.LONG_TERM);
			break;
		}
	}

	public void setPrice(String priceString) {
		setPrice(extractNumber(priceString.trim()));
	}

	public void setDescription(String description) {
		if (description.startsWith("\r\n"))
			description = description.substring(2).trim();
		super.setDescription(description, Locale.ENGLISH);
	}

	public void setReadyToMoveIn(String readyToMoveInString) {
		setReadyToMoveIn("Ready to move".equals(readyToMoveInString.trim()));
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
			setFurnishing(extractFurnishing(specValue));
			break;
		case "To sea":
			setDistanceToSea(extractNumber(specValue));
			break;
		case "Plot area":
			setPlotSize(extractSQM(specValue));
			break;
		case "Bedrooms":
			setBedrooms(extractBedrooms(specValue));
			break;
		case "Covered area":
			setCoveredArea(extractSQM(specValue));
			break;
		case "Additional features":
			setFeatures(extractAdditionalFeatures(specValue), Locale.ENGLISH);
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
	 * Determines if the given Cyprus Reality property could be mapped to GoToCy property.
	 */
	public boolean isSuitable() {
		return getLocation() != null && getPropertyType() != null && getPropertyStatus() != null &&
			getPropertyStatus() == PropertyStatus.SALE;
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
