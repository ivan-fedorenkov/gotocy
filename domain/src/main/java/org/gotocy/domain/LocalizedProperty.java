package org.gotocy.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
public class LocalizedProperty extends BaseEntity {

	private Property property;

	@NotNull
	private String locale;

	@NotNull
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
