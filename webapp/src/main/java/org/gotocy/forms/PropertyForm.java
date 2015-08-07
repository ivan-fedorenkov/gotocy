package org.gotocy.forms;

import org.gotocy.domain.*;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A property dto to be displayed in creating/editing forms.
 *
 * @author ifedorenkov
 */
public class PropertyForm {

	private static final String STRING_JOINER = "\n";
	private static final Pattern STRING_SEPARATOR = Pattern.compile("[\n\r]+");

	private LocalizedProperty enLocalizedProperty;
	private LocalizedProperty ruLocalizedProperty;

	private Property propertyDelegate;

	// Default constructor for controller binding only
	public PropertyForm() {
		propertyDelegate = new Property();
		propertyDelegate.setImageSet(new ImageSet());

		enLocalizedProperty = new LocalizedProperty();
		enLocalizedProperty.setProperty(propertyDelegate);

		ruLocalizedProperty = new LocalizedProperty();
		ruLocalizedProperty.setProperty(propertyDelegate);

		setLocales();
	}

	public PropertyForm(LocalizedProperty enLocalizedProperty, LocalizedProperty ruLocalizedProperty) {
		this.enLocalizedProperty = enLocalizedProperty;
		this.ruLocalizedProperty = ruLocalizedProperty;

		propertyDelegate = enLocalizedProperty.getProperty();

		setLocales();
	}

	public LocalizedProperty getEnLocalizedProperty() {
		return enLocalizedProperty;
	}

	public LocalizedProperty getRuLocalizedProperty() {
		return ruLocalizedProperty;
	}

	public Property getPropertyDelegate() {
		return propertyDelegate;
	}

	private void setLocales() {
		enLocalizedProperty.setLocale("en");
		ruLocalizedProperty.setLocale("ru");
	}

	// En localized property delegate

	public void setEnDescription(String description) {
		enLocalizedProperty.setDescription(description);
	}

	public String getEnDescription() {
		return enLocalizedProperty.getDescription();
	}

	/**
	 * Set en specifications from the given string representation.
	 * Unit test: PropertyFormTest#setSpecifications
	 */
	public void setEnSpecifications(String specifications) {
		if (specifications != null && !specifications.isEmpty()) {
			for (String s : STRING_SEPARATOR.split(specifications)) {
				LocalizedPropertySpecification lps = new LocalizedPropertySpecification();
				lps.setSpecification(s);
				enLocalizedProperty.addSpecification(lps);
			}
		}
	}

	/**
	 * Return en specifications as a string.
	 * Unit test: PropertyFormTest#getSpecifications
	 */
	public String getEnSpecifications() {
		return enLocalizedProperty.getSpecifications()
			.stream()
			.map(LocalizedPropertySpecification::getSpecification)
			.collect(Collectors.joining(STRING_JOINER));
	}

	// Ru localized property delegate

	public void setRuDescription(String description) {
		ruLocalizedProperty.setDescription(description);
	}


	public String getRuDescription() {
		return ruLocalizedProperty.getDescription();
	}

	/**
	 * Set ru specifications from the given string representation.
	 * Unit test: PropertyFormTest#setSpecifications
	 */
	public void setRuSpecifications(String specifications) {
		if (specifications != null && !specifications.isEmpty()) {
			for (String s : STRING_SEPARATOR.split(specifications)) {
				LocalizedPropertySpecification lps = new LocalizedPropertySpecification();
				lps.setSpecification(s);
				ruLocalizedProperty.addSpecification(lps);
			}
		}
	}

	/**
	 * Return ru specifications as a string.
	 * Unit test: PropertyFormTest#getSpecifications
	 */
	public String getRuSpecifications() {
		return ruLocalizedProperty.getSpecifications()
			.stream()
			.map(LocalizedPropertySpecification::getSpecification)
			.collect(Collectors.joining(STRING_JOINER));
	}

	// Property delegate

	public void setImages(String images) {
		if (images != null && !images.isEmpty()) {
			for (String imageKey : STRING_SEPARATOR.split(images)) {
				Image image = new Image();
				image.setKey(imageKey);
				propertyDelegate.getImageSet().addImage(image);
			}
		}
	}

	/**
	 * Return property delegate images as a string.
	 * Unit test: PropertyFormTest#getImages
	 */
	public String getImages() {
		return propertyDelegate.getImageSet().getImages()
			.stream()
			.map(Image::getKey)
			.collect(Collectors.joining(STRING_JOINER));
	}

	public Long getId() {
		return propertyDelegate.getId();
	}

	public void setOfferStatus(OfferStatus offerStatus) {
		propertyDelegate.setOfferStatus(offerStatus);
	}

	public Owner getOwner() {
		return propertyDelegate.getOwner();
	}

	public void setOwner(Owner owner) {
		propertyDelegate.setOwner(owner);
	}

	public Location getLocation() {
		return propertyDelegate.getLocation();
	}

	public void setLocation(Location location) {
		propertyDelegate.setLocation(location);
	}

	public Double getLatitude() {
		return propertyDelegate.getLatitude();
	}

	public void setLatitude(Double latitude) {
		propertyDelegate.setLatitude(latitude);
	}

	public Double getLongitude() {
		return propertyDelegate.getLongitude();
	}

	public void setLongitude(Double longitude) {
		propertyDelegate.setLongitude(longitude);
	}

	public PropertyType getPropertyType() {
		return propertyDelegate.getPropertyType();
	}

	public void setPropertyType(PropertyType propertyType) {
		propertyDelegate.setPropertyType(propertyType);
	}

	public PropertyStatus getPropertyStatus() {
		return propertyDelegate.getPropertyStatus();
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		propertyDelegate.setPropertyStatus(propertyStatus);
	}

	public Integer getPrice() {
		return propertyDelegate.getPrice();
	}

	public void setPrice(Integer price) {
		propertyDelegate.setPrice(price);
	}

	public Integer getCoveredArea() {
		return propertyDelegate.getCoveredArea();
	}

	public void setCoveredArea(Integer coveredArea) {
		propertyDelegate.setCoveredArea(coveredArea);
	}

	public Integer getPlotSize() {
		return propertyDelegate.getPlotSize();
	}

	public void setPlotSize(Integer plotSize) {
		propertyDelegate.setPlotSize(plotSize);
	}

	public Integer getBedrooms() {
		return propertyDelegate.getBedrooms();
	}

	public void setBedrooms(Integer bedrooms) {
		propertyDelegate.setBedrooms(bedrooms);
	}

	public Integer getGuests() {
		return propertyDelegate.getGuests();
	}

	public void setGuests(Integer guests) {
		propertyDelegate.setGuests(guests);
	}

	public Integer getBaths() {
		return propertyDelegate.getBaths();
	}

	public void setBaths(Integer baths) {
		propertyDelegate.setBaths(baths);
	}

	public ImageSet getImageSet() {
		return propertyDelegate.getImageSet();
	}

	public void setImageSet(ImageSet imageSet) {
		propertyDelegate.setImageSet(imageSet);
	}

	public Integer getDistanceToSea() {
		return propertyDelegate.getDistanceToSea();
	}

	public void setDistanceToSea(Integer distanceToSea) {
		propertyDelegate.setDistanceToSea(distanceToSea);
	}

	public Boolean getAirConditioner() {
		return propertyDelegate.getAirConditioner();
	}

	public void setAirConditioner(Boolean airConditioner) {
		propertyDelegate.setAirConditioner(airConditioner);
	}

	public Boolean getReadyToMoveIn() {
		return propertyDelegate.getReadyToMoveIn();
	}

	public void setReadyToMoveIn(Boolean readyToMoveIn) {
		propertyDelegate.setReadyToMoveIn(readyToMoveIn);
	}

	public Boolean getHeatingSystem() {
		return propertyDelegate.getHeatingSystem();
	}

	public void setHeatingSystem(Boolean heatingSystem) {
		propertyDelegate.setHeatingSystem(heatingSystem);
	}

	public Furnishing getFurnishing() {
		return propertyDelegate.getFurnishing();
	}

	public void setFurnishing(Furnishing furnishing) {
		propertyDelegate.setFurnishing(furnishing);
	}

	public PanoXml getPanoXml() {
		return propertyDelegate.getPanoXml();
	}

	public void setPanoXml(PanoXml panoXml) {
		propertyDelegate.setPanoXml(panoXml);
	}

	public String getTitle() {
		return propertyDelegate.getTitle();
	}

	public void setTitle(String title) {
		propertyDelegate.setTitle(title);
	}

	public String getFullAddress() {
		return propertyDelegate.getAddress();
	}

	public void setFullAddress(String fullAddress) {
		propertyDelegate.setAddress(fullAddress);
	}

	public String getShortAddress() {
		return propertyDelegate.getShortAddress();
	}

	public void setShortAddress(String shortAddress) {
		propertyDelegate.setShortAddress(shortAddress);
	}

	public OfferStatus getOfferStatus() {
		return propertyDelegate.getOfferStatus();
	}

}
