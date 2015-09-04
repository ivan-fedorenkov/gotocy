package org.gotocy.dto;

/**
 * @author ifedorenkov
 */
public class PropertyJson {

	private String title;
	private double latitude;
	private double longitude;
	private String shortAddress;
	private String typeIcon;
	private String price;
	private String propertyUrl;
	private String representativeImageUrl;

	public PropertyJson() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public String getPropertyUrl() {
		return propertyUrl;
	}

	public void setPropertyUrl(String propertyUrl) {
		this.propertyUrl = propertyUrl;
	}

	public String getTypeIcon() {
		return typeIcon;
	}

	public void setTypeIcon(String typeIcon) {
		this.typeIcon = typeIcon;
	}

	public String getRepresentativeImageUrl() {
		return representativeImageUrl;
	}

	public void setRepresentativeImageUrl(String representativeImageUrl) {
		this.representativeImageUrl = representativeImageUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
