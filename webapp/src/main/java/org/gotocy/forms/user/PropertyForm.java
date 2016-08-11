package org.gotocy.forms.user;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.config.Locales;
import org.gotocy.domain.*;

/**
 * User form for property related activities.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertyForm {

	private String title;
	private String address;
	private Location location;
	private PropertyType propertyType;
	private int coveredArea;
	private int plotSize;
	private int bedrooms;
	private int guests;
	private int levels;
	private int distanceToSea;
	private Furnishing furnishing;
	private boolean vatIncluded;
	private boolean airConditioner;
	private boolean heatingSystem;
	private boolean readyToMoveIn;
	private double latitude;
	private double longitude;

	private String enDescription;
	private String ruDescription;
	private String elDescription;

	public PropertyForm() {
		location = Location.FAMAGUSTA;
		propertyType = PropertyType.APARTMENT;
		furnishing = Furnishing.NONE;
	}

	public PropertyForm(Property property) {
		title = property.getTitle();
		address = property.getAddress();
		location = property.getLocation();
		propertyType = property.getPropertyType();
		coveredArea = property.getCoveredArea();
		plotSize = property.getPlotSize();
		bedrooms = property.getBedrooms();
		guests = property.getGuests();
		levels = property.getLevels();
		distanceToSea = property.getDistanceToSea();
		furnishing = property.getFurnishing();
		vatIncluded = property.isVatIncluded();
		airConditioner = property.hasAirConditioner();
		heatingSystem = property.hasHeatingSystem();
		readyToMoveIn = property.isReadyToMoveIn();
		latitude = property.getLatitude();
		longitude = property.getLongitude();

		enDescription = property.getDescription(Locales.EN);
		ruDescription = property.getDescription(Locales.RU);
		elDescription = property.getDescription(Locales.EL);
	}

	public Property mergeWithProperty(Property property) {
		property.setTitle(title);
		property.setAddress(address);
		property.setLocation(location);
		property.setPropertyType(propertyType);
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

		property.setDescription(enDescription, Locales.EN);
		property.setDescription(ruDescription, Locales.RU);
		property.setDescription(elDescription, Locales.EL);

		// Preserve the contacts display option or set to OWNER (default)
		PropertyContactsDisplayOption contactsDisplayOption = property.getContactsDisplayOption();
		if (contactsDisplayOption == null)
			contactsDisplayOption = PropertyContactsDisplayOption.OWNER;
		property.setContactsDisplayOption(contactsDisplayOption);

		return property;
	}

}
