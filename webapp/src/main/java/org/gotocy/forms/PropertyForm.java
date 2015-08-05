package org.gotocy.forms;

import org.gotocy.domain.*;

import java.util.List;

/**
 * A property dto to be displayed in creating/editing forms.
 *
 * @author ifedorenkov
 */
public class PropertyForm {

	private final LocalizedProperty enLocalizedProperty;
	private final LocalizedProperty ruLocalizedProperty;

	private final Property propertyDelegate;

	public PropertyForm(LocalizedProperty enLocalizedProperty, LocalizedProperty ruLocalizedProperty) {
		this.enLocalizedProperty = enLocalizedProperty;
		this.ruLocalizedProperty = ruLocalizedProperty;

		propertyDelegate = enLocalizedProperty.getProperty();
	}

	public LocalizedProperty getEn() {
		return enLocalizedProperty;
	}

	public LocalizedProperty getRu() {
		return ruLocalizedProperty;
	}

	// Property delegate

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

	public String getAddress() {
		return propertyDelegate.getAddress();
	}

	public void setAddress(String address) {
		propertyDelegate.setAddress(address);
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
