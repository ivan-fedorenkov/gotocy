package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;
import org.gotocy.utils.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * TODO: unit tests
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

	// Owner
	private Long ownerId;
	private String ownerName;
	private String ownerPhone;
	private String ownerEmail;
	private String ownerSpokenLanguages;

	// Property
	private String title;
	private String fullAddress;
	private String shortAddress;
	private Location location;
	private PropertyType propertyType;
	private PropertyStatus propertyStatus;
	private OfferStatus offerStatus;
	private Integer price;
	private Integer coveredArea;
	private Integer plotSize;
	private Integer bedrooms;
	private Integer guests;
	private Integer levels;
	private Integer distanceToSea;
	private Furnishing furnishing;
	private Boolean vatIncluded;
	private Boolean airConditioner;
	private Boolean heatingSystem;
	private Boolean readyToMoveIn;
	private Double latitude;
	private Double longitude;

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
		offerStatus = OfferStatus.ACTIVE;
		furnishing = Furnishing.NONE;
	}

	public PropertyForm(Property property) {
		ownerId = property.getOwner().getId();
		ownerName = property.getOwner().getName();
		ownerPhone = property.getOwner().getPhone();
		ownerEmail = property.getOwner().getEmail();
		ownerSpokenLanguages = property.getOwner().getSpokenLanguages();

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
		vatIncluded = property.getVatIncluded();
		airConditioner = property.getAirConditioner();
		heatingSystem = property.getHeatingSystem();
		readyToMoveIn = property.getReadyToMoveIn();
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

	public Owner mergeWithOwner(Owner owner) {
		owner.setName(ownerName);
		owner.setPhone(ownerPhone);
		owner.setEmail(ownerEmail);
		owner.setSpokenLanguages(ownerSpokenLanguages);
		return owner;
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
		// Only if features changed
		if (!CollectionUtils.collectionsEquals(property.getFeatures(), enFeaturesList))
			property.setFeatures(enFeaturesList, EN_LOCALE);

		List<String> ruFeaturesList = ruFeatures != null && !ruFeatures.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(ruFeatures)) : Collections.emptyList();
		// Only if features changed
		if (!CollectionUtils.collectionsEquals(property.getFeatures(), ruFeaturesList))
			property.setFeatures(ruFeaturesList, RU_LOCALE);

		List<Image> images = new ArrayList<>();
		if (imageKeys != null && !imageKeys.isEmpty()) {
			for (String imageKey : STRING_SEPARATOR.split(imageKeys)) {
				if (assetKeyIsDefined(imageKey)) {
					Image image = new Image();
					image.setKey(imageKey);
					images.add(image);
				}
			}
		}
		// Only if images changed
		if (!CollectionUtils.collectionsEquals(property.getImages(), images))
			property.setImages(images);


		Image representativeImage = null;
		if (assetKeyIsDefined(representativeImageKey)) {
			representativeImage = new Image();
			representativeImage.setKey(representativeImageKey);
		}
		// Only if representative image changed
		if (!Objects.equals(property.getRepresentativeImage(), representativeImage))
			property.setRepresentativeImage(representativeImage);

		PanoXml panoXml = null;
		if (assetKeyIsDefined(panoXmlKey)) {
			panoXml = new PanoXml();
			panoXml.setKey(panoXmlKey);
		}
		// Only if pano xml changed
		if (!Objects.equals(property.getPanoXml(), panoXml))
			property.setPanoXml(panoXml);

		return property;
	}

	private static boolean assetKeyIsDefined(String assetKey) {
		return assetKey != null && !assetKey.trim().isEmpty();
	}

}
