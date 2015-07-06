package org.gotocy.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * TODO: validation / integration test on validation
 *
 * @author ifedorenkov
 */
@Entity
public class Property extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private Location location;

	private Double latitude;

	private Double longitude;

	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;

	@Enumerated(EnumType.STRING)
	private PropertyStatus propertyStatus;
	
	private Integer price;

	private Integer area;

	private Integer bedrooms;

	// TODO: remove ?
	private Integer baths;

	private Integer distanceToSea;

	@Type(type="yes_no")
	private Boolean airConditioner;

	@Embedded
	private ImageSet imageSet;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getBaths() {
		return baths;
	}

	public void setBaths(Integer baths) {
		this.baths = baths;
	}

	public ImageSet getImageSet() {
		return imageSet;
	}

	public void setImageSet(ImageSet imageSet) {
		this.imageSet = imageSet;
	}

	public Integer getDistanceToSea() {
		return distanceToSea;
	}

	public void setDistanceToSea(Integer distanceToSea) {
		this.distanceToSea = distanceToSea;
	}

	public Boolean getAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(Boolean airConditioner) {
		this.airConditioner = airConditioner;
	}
}