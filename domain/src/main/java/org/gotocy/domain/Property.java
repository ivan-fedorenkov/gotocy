package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.LocalizedField;
import org.gotocy.domain.i18n.PropertyLocalizedFieldsManager;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: optimize #setRepresentativeImage #setPanoXml
 *
 * @author ifedorenkov
 */
@Entity
@NamedEntityGraph(name = "Property.withAssociations",
	attributeNodes = {
		@NamedAttributeNode(value = "complex", subgraph = "complex"),
		@NamedAttributeNode("developer"),
		@NamedAttributeNode("panoXml"),
		@NamedAttributeNode("representativeImage"),
	},
	subgraphs = {
		@NamedSubgraph(name = "complex", attributeNodes = {
			@NamedAttributeNode("representativeImage")
		})
	}
)
@Getter
@Setter
public class Property extends BaseEntity {

	@JsonBackReference
	@ManyToOne
	private Developer developer;

	@JsonBackReference
	@ManyToOne
	private Complex complex;

	@ManyToOne
	private GtcUser owner;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "overridden_contacts_name")),
		@AttributeOverride(name = "email", column = @Column(name = "overridden_contacts_email")),
		@AttributeOverride(name = "phone", column = @Column(name = "overridden_contacts_phone")),
		@AttributeOverride(name = "spokenLanguages", column = @Column(name = "overridden_contacts_spoken_languages"))
	})
	private Contacts overriddenContacts;

	@Enumerated(EnumType.STRING)
	private PropertyContactsDisplayOption contactsDisplayOption;

	@Enumerated(EnumType.STRING)
	private Location location;

	private String title;

	private String address;

	private String shortAddress;

	private double latitude;

	private double longitude;

	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;

	@Enumerated(EnumType.STRING)
	private PropertyStatus propertyStatus;

	@Enumerated(EnumType.STRING)
	private OfferStatus offerStatus;

	private int price;

	private int coveredArea;

	private int plotSize;

	private int bedrooms;

	private int guests;

	private int levels;

	private int distanceToSea;

	private boolean airConditioner;

	private boolean readyToMoveIn;

	private boolean heatingSystem;

	private boolean vatIncluded;

	private boolean featured;

	@Enumerated(EnumType.STRING)
	private Furnishing furnishing = Furnishing.NONE;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Image representativeImage;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private PanoXml panoXml;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	// Crawl specific fields
	private String crawlSource;
	private String crawlId;
	private String crawlUrl;

	public Image getImage(int index) {
		// Ensure that the index is in the images list bounds
		if (index >= images.size())
			index = index % images.size();
		return images.get(index);
	}

	public void setImages(List<Image> images) {
		CollectionUtils.updateCollection(this.images, images);
	}

	/**
	 * Returns the appropriate property's contacts.
	 */
	public Contacts getContacts() {
		switch (contactsDisplayOption) {
		case OVERRIDDEN:
			return overriddenContacts;
		case OWNER:
			return owner == null ? overriddenContacts : owner.getContacts();
		default:
			return null;
		}
	}

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

	public String getShortAddress() {
		return shortAddress == null ? address : shortAddress;
	}

	public boolean hasHeatingSystem() {
		return isHeatingSystem();
	}

	public boolean hasAirConditioner() {
		return isAirConditioner();
	}

	// Private stuff

	private PropertyLocalizedFieldsManager getLocalizedFieldsManager() {
		if (localizedFieldsManager == null)
			localizedFieldsManager = new PropertyLocalizedFieldsManager(this);
		return localizedFieldsManager;
	}

	/**
	 * Determines if this property should be treated as promo.
	 */
	public boolean isPromo() {
		return offerStatus == OfferStatus.PROMO || crawlSource != null;
	}

}