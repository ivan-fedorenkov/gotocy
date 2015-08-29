package org.gotocy.forms;

import org.gotocy.domain.*;
import org.gotocy.utils.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * TODO: unit tests
 *
 * @author ifedorenkov
 */
public class PropertyForm {

	private static final Pattern STRING_SEPARATOR = Pattern.compile("[\n\r]+");
	private static final String STRINGS_JOINER = "\n";

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

	public PropertyForm() {
		location = Location.FAMAGUSTA;
		propertyType = PropertyType.APARTMENT;
		propertyStatus = PropertyStatus.LONG_TERM;
		offerStatus = OfferStatus.ACTIVE;
		furnishing = Furnishing.NONE;
	}

	public PropertyForm(Property property, LocalizedProperty enLP, LocalizedProperty ruLP) {
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

		enDescription = enLP.getDescription();
		enSpecifications = enLP.getSpecifications()
			.stream()
			.map(LocalizedPropertySpecification::getSpecification)
			.collect(Collectors.joining(STRINGS_JOINER));

		ruDescription = ruLP.getDescription();
		ruSpecifications = ruLP.getSpecifications()
			.stream()
			.map(LocalizedPropertySpecification::getSpecification)
			.collect(Collectors.joining(STRINGS_JOINER));

		imageKeys = property.getImages()
			.stream()
			.map(Image::getKey)
			.collect(Collectors.joining(STRINGS_JOINER));

		if (property.getRepresentativeImage() != null)
			representativeImageKey = property.getRepresentativeImage().getKey();
		if (property.getPanoXml() != null)
			panoXmlKey = property.getPanoXml().getKey();

	}

	// En Localized Property
	private String enDescription;
	private String enSpecifications;

	// Ru Localized Property
	private String ruDescription;
	private String ruSpecifications;

	// Assets
	private String imageKeys;
	private String representativeImageKey;
	private String panoXmlKey;

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

	public LocalizedProperty mergeWithRuLocalizedProperty(LocalizedProperty lp) {
		return mergeWithLocalizedProperty(lp, ruDescription, ruSpecifications);
	}

	public LocalizedProperty mergeWithEnLocalizedProperty(LocalizedProperty lp) {
		return mergeWithLocalizedProperty(lp, enDescription, enSpecifications);
	}

	private LocalizedProperty mergeWithLocalizedProperty(LocalizedProperty lp, String description, String specifications)
	{
		lp.setDescription(description);

		List<LocalizedPropertySpecification> updatedSpecifications = new ArrayList<>();
		if (specifications != null && !specifications.isEmpty()) {
			for (String s : STRING_SEPARATOR.split(specifications)) {
				if (s != null && !s.trim().isEmpty()) {
					LocalizedPropertySpecification lps = new LocalizedPropertySpecification();
					lps.setSpecification(s);
					updatedSpecifications.add(lps);
				}
			}
		}
		// Only if specifications changed
		if (!CollectionUtils.collectionsEquals(lp.getSpecifications(), updatedSpecifications))
			lp.setSpecifications(updatedSpecifications);

		return lp;
	}

	private static boolean assetKeyIsDefined(String assetKey) {
		return assetKey != null && !assetKey.trim().isEmpty();
	}

	// Getters and setters

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerSpokenLanguages() {
		return ownerSpokenLanguages;
	}

	public void setOwnerSpokenLanguages(String ownerSpokenLanguages) {
		this.ownerSpokenLanguages = ownerSpokenLanguages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public OfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(OfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(Integer coveredArea) {
		this.coveredArea = coveredArea;
	}

	public Integer getPlotSize() {
		return plotSize;
	}

	public void setPlotSize(Integer plotSize) {
		this.plotSize = plotSize;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getGuests() {
		return guests;
	}

	public void setGuests(Integer guests) {
		this.guests = guests;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public Integer getDistanceToSea() {
		return distanceToSea;
	}

	public void setDistanceToSea(Integer distanceToSea) {
		this.distanceToSea = distanceToSea;
	}

	public Furnishing getFurnishing() {
		return furnishing;
	}

	public void setFurnishing(Furnishing furnishing) {
		this.furnishing = furnishing;
	}

	public Boolean getVatIncluded() {
		return vatIncluded;
	}

	public void setVatIncluded(Boolean vatIncluded) {
		this.vatIncluded = vatIncluded;
	}

	public Boolean getAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(Boolean airConditioner) {
		this.airConditioner = airConditioner;
	}

	public Boolean getHeatingSystem() {
		return heatingSystem;
	}

	public void setHeatingSystem(Boolean heatingSystem) {
		this.heatingSystem = heatingSystem;
	}

	public Boolean getReadyToMoveIn() {
		return readyToMoveIn;
	}

	public void setReadyToMoveIn(Boolean readyToMoveIn) {
		this.readyToMoveIn = readyToMoveIn;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getEnDescription() {
		return enDescription;
	}

	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}

	public String getEnSpecifications() {
		return enSpecifications;
	}

	public void setEnSpecifications(String enSpecifications) {
		this.enSpecifications = enSpecifications;
	}

	public String getRuDescription() {
		return ruDescription;
	}

	public void setRuDescription(String ruDescription) {
		this.ruDescription = ruDescription;
	}

	public String getRuSpecifications() {
		return ruSpecifications;
	}

	public void setRuSpecifications(String ruSpecifications) {
		this.ruSpecifications = ruSpecifications;
	}

	public String getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String imageKeys) {
		this.imageKeys = imageKeys;
	}

	public String getRepresentativeImageKey() {
		return representativeImageKey;
	}

	public void setRepresentativeImageKey(String representativeImageKey) {
		this.representativeImageKey = representativeImageKey;
	}

	public String getPanoXmlKey() {
		return panoXmlKey;
	}

	public void setPanoXmlKey(String panoXmlKey) {
		this.panoXmlKey = panoXmlKey;
	}
}
