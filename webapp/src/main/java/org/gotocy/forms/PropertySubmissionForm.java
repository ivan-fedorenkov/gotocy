package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User form for property submissions.
 * TODO: remove fields: offer status, price
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertySubmissionForm {

	private String title;
	private String address;
	private Location location;
	private PropertyType propertyType;
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

	public PropertySubmissionForm() {
		location = Location.FAMAGUSTA;
		propertyType = PropertyType.APARTMENT;
		furnishing = Furnishing.NONE;
	}

	public PropertySubmissionForm(Property property) {
		title = property.getTitle();
		address = property.getAddress();
		location = property.getLocation();
		propertyType = property.getPropertyType();
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

		// Preserve the contacts display option or set to OWNER (default)
		PropertyContactsDisplayOption contactsDisplayOption = property.getContactsDisplayOption();
		if (contactsDisplayOption == null)
			contactsDisplayOption = PropertyContactsDisplayOption.OWNER;
		property.setContactsDisplayOption(contactsDisplayOption);

		return property;
	}

	/**
	 * Returns a list of {@link Image} created from the attached {@link #images}.
	 */
	public List<Image> mapFilesToImages() throws IOException {
		if (images.isEmpty())
			return Collections.emptyList();

		List<Image> result = new ArrayList<>(images.size());
		for (MultipartFile file : images) {
			Image image = new Image(file.getOriginalFilename());
			image.setBytes(file.getBytes());
			result.add(image);
		}
		return result;
	}

	public void setImages(List<MultipartFile> images) {
		images.removeIf(MultipartFile::isEmpty);
		this.images.addAll(images);
	}

}
