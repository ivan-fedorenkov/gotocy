package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * TODO: relations management integration tests
 * TODO: validation / validation integration tests
 * TODO: remove the JsonBackReference annotation
 *
 * @author ifedorenkov
 */
@Entity
public class LocalizedPropertySpecification extends BaseEntity {

	@JsonBackReference
	@ManyToOne(optional = false)
	private LocalizedProperty localizedProperty;

	private String specification;

	public LocalizedProperty getLocalizedProperty() {
		return localizedProperty;
	}

	public void setLocalizedProperty(LocalizedProperty localizedProperty) {
		this.localizedProperty = localizedProperty;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LocalizedPropertySpecification that = (LocalizedPropertySpecification) o;
		return Objects.equals(specification, that.specification);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), specification);
	}
}
