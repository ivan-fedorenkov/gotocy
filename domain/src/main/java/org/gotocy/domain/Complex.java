package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.ComplexLocalizedFieldsManager;
import org.gotocy.domain.i18n.LocalizedField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: validation / integration test on validation
 *
 * @author ifedorenkov
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Entity
@Getter
@Setter
public class Complex extends BaseEntity implements ImageSetDelegate {

	@ManyToOne(optional = false)
	private Owner primaryContact;

	@Enumerated(EnumType.STRING)
	private Location location;

	private String title;
	private String address;
	private String shortAddress;
	private String coordinates;

	@Embedded
	private ImageSet imageSet = new ImageSet();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Image representativeImage;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	@OneToMany(mappedBy = "complex")
	private List<Property> properties = new ArrayList<>();

	public int getPropertiesCount() {
		return properties.size();
	}

	public long getPropertiesWithActiveOfferCount() {
		return properties.stream().filter(p -> p.getOfferStatus() == OfferStatus.ACTIVE).count();
	}

	// Localized fields

	private transient ComplexLocalizedFieldsManager localizedFieldsManager;
	private transient String description;
	private transient List<String> specifications;

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

	public List<String> getSpecifications(Locale locale) {
		return getLocalizedFieldsManager().getSpecifications(locale);
	}

	public void setSpecifications(List<String> specifications, Locale locale) {
		this.setSpecifications(specifications);
		getLocalizedFieldsManager().setSpecifications(specifications, locale);
	}

	// Private stuff

	private ComplexLocalizedFieldsManager getLocalizedFieldsManager() {
		if (localizedFieldsManager == null)
			localizedFieldsManager = new ComplexLocalizedFieldsManager(this);
		return localizedFieldsManager;
	}

}
