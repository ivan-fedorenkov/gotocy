package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO: implement add/remove specifications
 * TODO: relations management integration tests
 * TODO: validation / validation integration tests
 * TODO: remove the JsonManagedReference annotation
 *
 * @author ifedorenkov
 */
@Entity
public class LocalizedProperty extends BaseEntity {

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Property property;

	private String locale;

	private String title;

	private String address;

	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "localizedProperty", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedPropertySpecification> specifications = new ArrayList<>();

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LocalizedPropertySpecification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<LocalizedPropertySpecification> specifications) {
		this.specifications = specifications;
		specifications.forEach(s -> s.setLocalizedProperty(this));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocalizedProperty)) return false;
		LocalizedProperty that = (LocalizedProperty) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
