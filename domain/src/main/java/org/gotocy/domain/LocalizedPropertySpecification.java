package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

}
