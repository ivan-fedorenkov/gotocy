package org.gotocy.domain.factory;

import org.gotocy.domain.*;

/**
 * A factory class of the {@link org.gotocy.domain.Property} entity.
 *
 * @author ifedorenkov
 */
public class PropertyFactory extends BaseFactory<Property> {

	public static final PropertyFactory INSTANCE = new PropertyFactory();

	private PropertyFactory() {
	}

	@Override
	public Property get() {
		Property property = new Property();
		property.setTitle(ANY_STRING);
		property.setLocation(Location.LARNACA);
		property.setAddress(ANY_STRING);
		property.setLatitude(ANY_DOUBLE);
		property.setLongitude(ANY_DOUBLE);
		property.setPropertyType(PropertyType.HOUSE);
		property.setPropertyStatus(PropertyStatus.SALE);
		property.setOfferStatus(OfferStatus.SOLD);
		property.setVatIncluded(ANY_BOOLEAN);
		property.setCoveredArea(ANY_INT);
		property.setPlotSize(ANY_INT);
		property.setBedrooms(ANY_INT);
		property.setReadyToMoveIn(ANY_BOOLEAN);
		property.setLevels(ANY_INT);
		property.setFurnishing(Furnishing.NONE);
		property.setGuests(ANY_INT);
		property.setAirConditioner(ANY_BOOLEAN);
		property.setDistanceToSea(ANY_INT);
		property.setHeatingSystem(ANY_BOOLEAN);
		return property;
	}

}
