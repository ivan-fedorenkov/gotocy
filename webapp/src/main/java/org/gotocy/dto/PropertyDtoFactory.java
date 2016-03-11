package org.gotocy.dto;

import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.gotocy.helpers.property.PropertyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ifedorenkov
 */
@Component
public class PropertyDtoFactory {
	
	private final PropertyHelper propertyHelper;

	@Autowired
	public PropertyDtoFactory(PropertyHelper propertyHelper) {
		this.propertyHelper = propertyHelper;
	}

	/**
	 * Factory method.
	 */
	public PropertyDto create(Property property) {
		PropertyDto propertyDto = new PropertyDto();
		propertyDto.setTitle(property.getTitle());
		propertyDto.setLatitude(property.getLatitude());
		propertyDto.setLongitude(property.getLongitude());
		propertyDto.setShortAddress(property.getShortAddress());
		propertyDto.setPrice(PropertyHelper.price(property));
		propertyDto.setTypeIcon(PropertyHelper.typeIcon(property.getPropertyType()));
		propertyDto.setPropertyUrl(Helper.path(property));
		propertyDto.setRepresentativeImageUrl(propertyHelper.representativeImageUrl(property));
		return propertyDto;
	}
	
}
