package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.LocalizedField;
import org.gotocy.domain.i18n.PropertyLocalizedFieldsManager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: validation / integration test on validation
 *
 * @author ifedorenkov
 */
@Entity
@NamedEntityGraph(name = "Property.withAssets",
	attributeNodes = {
		@NamedAttributeNode("owner"),
		@NamedAttributeNode("panoXml"),
		@NamedAttributeNode("representativeImage")
	}
)
@Getter
@Setter
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

	private Integer levels;

	private Integer distanceToSea;

	private Boolean airConditioner;

	private Boolean readyToMoveIn;

	private Boolean heatingSystem;

	private Boolean vatIncluded;

	@Enumerated(EnumType.STRING)
	private Furnishing furnishing;

	@Embedded
	private ImageSet imageSet = new ImageSet();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Image representativeImage;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private PanoXml panoXml;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	// Localized fields

	private transient PropertyLocalizedFieldsManager localizedFieldsManager;
	private transient String description;
	private transient List<String> features;

	public void initLocalizedFields(Locale locale) {
		getLocalizedFieldsManager().setFields(locale);
	}

	public String getDescription(Locale locale) {
		return getLocalizedFieldsManager().getDescription(locale).orElse("");
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description);
		getLocalizedFieldsManager().setDescription(description, locale);
	}

	public List<String> getFeatures(Locale locale) {
		return getLocalizedFieldsManager().getFeatures(locale);
	}

	public void setFeatures(List<String> features, Locale locale) {
		setFeatures(features);
		getLocalizedFieldsManager().setFeatures(features, locale);
	}

	// ImageSet delegate

	public void setImages(List<Image> images) {
		imageSet.setImages(images);
	}

	public List<Image> getImages() {
		return imageSet.getImages();
	}

	// Private stuff

	private PropertyLocalizedFieldsManager getLocalizedFieldsManager() {
		if (localizedFieldsManager == null)
			localizedFieldsManager = new PropertyLocalizedFieldsManager(this);
		return localizedFieldsManager;
	}

}