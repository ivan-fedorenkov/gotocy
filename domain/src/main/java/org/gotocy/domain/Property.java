package org.gotocy.domain;

import javax.persistence.*;
import java.util.List;

/**
 * TODO: validation / integration test on validation
 *
 * @author ifedorenkov
 */
@Entity
public class Property extends BaseEntity {

	@ManyToOne(optional = false)
	private Owner owner;

	@Enumerated(EnumType.STRING)
	private Location location;

	private String title;

	private String address;

	private String shortAddress;

	private Double latitude;

	private Double longitude;

	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;

	@Enumerated(EnumType.STRING)
	private PropertyStatus propertyStatus;

	@Enumerated(EnumType.STRING)
	private OfferStatus offerStatus;

	private Integer price;

	private Integer coveredArea;

	private Integer plotSize;

	private Integer bedrooms;

	private Integer guests;

	// TODO: remove ?
	private Integer baths;

	private Integer distanceToSea;

	private Boolean airConditioner;

	private Boolean readyToMoveIn;

	private Boolean heatingSystem;

	@Enumerated(EnumType.STRING)
	private Furnishing furnishing;

	@Embedded
	private ImageSet imageSet = new ImageSet();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private PanoXml panoXml;

	// ImageSet delegate
	public void setImages(List<Image> images) {
		imageSet.setImages(images);
	}

	public List<Image> getImages() {
		return imageSet.getImages();
	}

	public void setRepresentativeImage(Image image) {
		imageSet.setRepresentativeImage(image);
	}

	public Image getRepresentativeImage() {
		return imageSet.getRepresentativeImage();
	}

	// Getters and setters
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

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

	public Integer getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(Integer coveredArea) {
		this.coveredArea = coveredArea;
	}

	public Integer getPlotSize() {
		return plotSize;
	}

	public void setPlotSize(Integer plotSize) {
		this.plotSize = plotSize;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getGuests() {
		return guests;
	}

	public void setGuests(Integer guests) {
		this.guests = guests;
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

	public Boolean getReadyToMoveIn() {
		return readyToMoveIn;
	}

	public void setReadyToMoveIn(Boolean readyToMoveIn) {
		this.readyToMoveIn = readyToMoveIn;
	}

	public Boolean getHeatingSystem() {
		return heatingSystem;
	}

	public void setHeatingSystem(Boolean heatingSystem) {
		this.heatingSystem = heatingSystem;
	}

	public Furnishing getFurnishing() {
		return furnishing;
	}

	public void setFurnishing(Furnishing furnishing) {
		this.furnishing = furnishing;
	}

	public PanoXml getPanoXml() {
		return panoXml;
	}

	public void setPanoXml(PanoXml panoXml) {
		this.panoXml = panoXml;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public OfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(OfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}
}