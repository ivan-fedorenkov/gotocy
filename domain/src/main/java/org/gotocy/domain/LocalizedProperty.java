package org.gotocy.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
public class LocalizedProperty extends BaseEntity {

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Property property;

	private String locale;

	private String title;

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
