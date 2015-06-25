package org.gotocy.domain;

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

	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;
	
	private Integer price;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}