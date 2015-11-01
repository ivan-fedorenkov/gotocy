package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * TODO: unit tests
 * TODO: optimize / refactor : features, assets
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertyForm {

	private static final Pattern STRING_SEPARATOR = Pattern.compile("[\n\r]+");
	private static final String STRINGS_JOINER = "\n";

	private static final Locale EN_LOCALE = Locale.ENGLISH;
	private static final Locale RU_LOCALE = new Locale("ru");

	// Developer
	private long developerId;

	// Complex
	private long complexId;

	// Primary Contact
	private long contactId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactSpokenLanguages;

	// Property
	private String title;
	private String fullAddress;
	private String shortAddress;
	private Location location;
	private PropertyType propertyType;
	private PropertyStatus propertyStatus;
	private OfferStatus offerStatus;
	private int price;
	private int coveredArea;
	private int plotSize;
	private int bedrooms;
	private int guests;
	private int levels;
	private int distanceToSea;
	private Furnishing furnishing;
	private boolean vatIncluded;
	private boolean airConditioner;
	private boolean heatingSystem;
	private boolean readyToMoveIn;
	private double latitude;
	private double longitude;

	// En Localized Property
	private String enDescription;
	private String enFeatures;

	// Ru Localized Property
	private String ruDescription;
	private String ruFeatures;

	// Assets
	private String imageKeys;
	private String representativeImageKey;
	private String panoXmlKey;

	public PropertyForm() {
		location = Location.FAMAGUSTA;
		propertyType = PropertyType.APARTMENT;
		propertyStatus = PropertyStatus.LONG_TERM;
		offerStatus = OfferStatus.DEMO;
		furnishing = Furnishing.NONE;
	}

	public PropertyForm(Property property) {
		developerId = property.getDeveloper() == null ? 0 : property.getDeveloper().getId();
		complexId = property.getComplex() == null ? 0 : property.getComplex().getId();

		if (property.getPrimaryContact() != null) {
			contactId = property.getPrimaryContact().getId();
			contactName = property.getPrimaryContact().getName();
			contactPhone = property.getPrimaryContact().getPhone();
			contactEmail = property.getPrimaryContact().getEmail();
			contactSpokenLanguages = property.getPrimaryContact().getSpokenLanguages();
		}

		title = property.getTitle();
		fullAddress = property.getAddress();
		shortAddress = property.getShortAddress();
		location = property.getLocation();
		propertyType = property.getPropertyType();
		propertyStatus = property.getPropertyStatus();
		offerStatus = property.getOfferStatus();
		price = property.getPrice();
		coveredArea = property.getCoveredArea();
		plotSize = property.getPlotSize();
		bedrooms = property.getBedrooms();
		guests = property.getGuests();
		levels = property.getLevels();
		distanceToSea = property.getDistanceToSea();
		furnishing = property.getFurnishing();
		vatIncluded = property.isVatIncluded();
		airConditioner = property.hasAirConditioner();
		heatingSystem = property.hasHeatingSystem();
		readyToMoveIn = property.isReadyToMoveIn();
		latitude = property.getLatitude();
		longitude = property.getLongitude();

		enDescription = property.getDescription(EN_LOCALE);
		enFeatures = property.getFeatures(EN_LOCALE).stream().collect(joining(STRINGS_JOINER));

		ruDescription = property.getDescription(RU_LOCALE);
		ruFeatures = property.getFeatures(RU_LOCALE).stream().collect(joining(STRINGS_JOINER));

		imageKeys = property.getImages()
			.stream()
			.map(Image::getKey)
			.collect(joining(STRINGS_JOINER));

		if (property.getRepresentativeImage() != null)
			representativeImageKey = property.getRepresentativeImage().getKey();
		if (property.getPanoXml() != null)
			panoXmlKey = property.getPanoXml().getKey();

	}

	public Contact mergeWithContact(Contact contact) {
		contact.setName(contactName);
		contact.setPhone(contactPhone);
		contact.setEmail(contactEmail);
		contact.setSpokenLanguages(contactSpokenLanguages);
		return contact;
	}

	public Property mergeWithProperty(Property property) {
		property.setTitle(title);
		property.setAddress(fullAddress);
		property.setShortAddress(shortAddress);
		property.setLocation(location);
		property.setPropertyType(propertyType);
		property.setPropertyStatus(propertyStatus);
		property.setOfferStatus(offerStatus);
		property.setPrice(price);
		property.setCoveredArea(coveredArea);
		property.setPlotSize(plotSize);
		property.setBedrooms(bedrooms);
		property.setGuests(guests);
		property.setLevels(levels);
		property.setDistanceToSea(distanceToSea);
		property.setFurnishing(furnishing);
		property.setVatIncluded(vatIncluded);
		property.setAirConditioner(airConditioner);
		property.setHeatingSystem(heatingSystem);
		property.setReadyToMoveIn(readyToMoveIn);
		property.setLatitude(latitude);
		property.setLongitude(longitude);

		property.setDescription(enDescription, EN_LOCALE);
		property.setDescription(ruDescription, RU_LOCALE);

		List<String> enFeaturesList = enFeatures != null && !enFeatures.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(enFeatures)) : Collections.emptyList();
		property.setFeatures(enFeaturesList, EN_LOCALE);

		List<String> ruFeaturesList = ruFeatures != null && !ruFeatures.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(ruFeatures)) : Collections.emptyList();
		property.setFeatures(ruFeaturesList, RU_LOCALE);

		List<Image> images = imageKeys != null && !imageKeys.isEmpty() ?
			Arrays.stream(STRING_SEPARATOR.split(imageKeys))
				.filter(PropertyForm::assetKeyIsDefined)
				.map(Image::new)
				.collect(toList()) : Collections.emptyList();
		property.setImages(images);


		Image representativeImage = null;
		if (assetKeyIsDefined(representativeImageKey))
			representativeImage = new Image(representativeImageKey);

		// Only if representative image changed
		if (!Objects.equals(property.getRepresentativeImage(), representativeImage))
			property.setRepresentativeImage(representativeImage);

		PanoXml panoXml = null;
		if (assetKeyIsDefined(panoXmlKey))
			panoXml = new PanoXml(panoXmlKey);

		// Only if pano xml changed
		if (!Objects.equals(property.getPanoXml(), panoXml))
			property.setPanoXml(panoXml);

		return property;
	}

	private static boolean assetKeyIsDefined(String assetKey) {
		return assetKey != null && !assetKey.trim().isEmpty();
	}

}
