package org.gotocy.factory;

import org.gotocy.domain.*;

/**
 * A factory of the {@link org.gotocy.domain.Property} entity.
 *
 * @author ifedorenkov
 */
public class PropertyFactory {

	/**
	 * Returns the fully populated and valid property instance.
	 */
	public static Property build() {
		Property p = new Property();
		p.setTitle("Test title");
		p.setPropertyType(PropertyType.HOUSE);
		p.setPropertyStatus(PropertyStatus.SALE);
		p.setOfferStatus(OfferStatus.ACTIVE);
		p.setLocation(Location.LARNACA);
		p.setAddress("Test address");
		p.setShortAddress("Test address");
		p.setPrice(1);
		p.setLatitude(1D);
		p.setLongitude(1D);
		p.setPlotSize(1);
		p.setCoveredArea(1);
		p.setLevels(1);
		p.setBedrooms(1);
		p.setReadyToMoveIn(Boolean.TRUE);
		p.setAirConditioner(Boolean.TRUE);
		p.setHeatingSystem(Boolean.TRUE);
		p.setVatIncluded(Boolean.TRUE);
		p.setFurnishing(Furnishing.FULL);

		Contact c = new Contact();
		c.setEmail("test@test.test");
		c.setName("Test");
		c.setPhone("123-45-67");
		c.setSpokenLanguages("En, Rus");

		p.setPrimaryContact(c);

		return p;
	}


}
