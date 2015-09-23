package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.ComplexLocalizedFieldsManager;
import org.gotocy.domain.i18n.LocalizedField;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: validation / integration test on validation
 * TODO: optimize #setRepresentativeImage
 *
 * @author ifedorenkov
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Entity
@NamedEntityGraph(name = "Complex.withRequiredAssociations", attributeNodes = {
	@NamedAttributeNode("primaryContact"),
	@NamedAttributeNode("representativeImage")
})
@Getter
@Setter
public class Complex extends BaseEntity {

	@ManyToOne(optional = false)
	private Owner primaryContact;

	@Enumerated(EnumType.STRING)
	private Location location;

	private String title;
	private String address;
	private String coordinates;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Image representativeImage;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PdfFile> pdfFiles = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	@OneToMany(mappedBy = "complex")
	@JsonManagedReference
	private List<Property> properties = new ArrayList<>();

	public int getPropertiesCount() {
		return properties.size();
	}

	public long getPropertiesWithActiveOfferCount() {
		return properties.stream().filter(p -> p.getOfferStatus() == OfferStatus.ACTIVE).count();
	}

	public Image getImage(int index) {
		// Ensure that the index is in the images list bounds
		if (index >= images.size())
			index = index % images.size();
		return images.get(index);
	}

	public void setImages(List<Image> images) {
		CollectionUtils.updateCollection(this.images, images);
	}

	public void setPdfFiles(List<PdfFile> pdfFiles) {
		CollectionUtils.updateCollection(this.pdfFiles, pdfFiles);
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
