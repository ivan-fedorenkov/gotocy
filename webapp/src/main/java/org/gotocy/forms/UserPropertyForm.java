package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * User form for property submissions.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class UserPropertyForm {

	private String title;
	private String address;
	private Location location;
	private PropertyType propertyType;
	private PropertyStatus propertyStatus;
	private OfferStatus offerStatus;
	private String description;
	private int price;
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

	private List<MultipartFile> images = new ArrayList<>();

	public UserPropertyForm() {
		location = Location.FAMAGUSTA;
		propertyType = PropertyType.APARTMENT;
		propertyStatus = PropertyStatus.SALE;
		offerStatus = OfferStatus.PROMO;
		furnishing = Furnishing.NONE;
	}

	public UserPropertyForm(Property property) {
		title = property.getTitle();
		address = property.getAddress();
		location = property.getLocation();
		propertyType = property.getPropertyType();
		propertyStatus = property.getPropertyStatus();
		offerStatus = property.getOfferStatus();
		description = property.getDescription();
		price = property.getPrice();
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
	}

	public Property mergeWithProperty(Property property) {
		property.setTitle(title);
		property.setAddress(address);
		property.setLocation(location);
		property.setPropertyType(propertyType);
		property.setPropertyStatus(propertyStatus);
		property.setOfferStatus(offerStatus);
		property.setDescription(description);
		property.setPrice(price);
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
		return property;
	}

	/**
	 * @return collection of non-empty images.
	 */
	public List<MultipartFile> getImages() {
		return images.stream().filter(image -> !image.isEmpty()).collect(toList());
	}

}
