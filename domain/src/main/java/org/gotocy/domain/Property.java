package org.gotocy.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

	@OneToMany(cascade = CascadeType.PERSIST) // TODO: decide if cascading is applicable here
	private Set<Image> images = new HashSet<>();

	@OneToOne(optional = false, cascade = CascadeType.PERSIST) // TODO: decide if cascading is applicable here
	private Image thumbnail;

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

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}
}