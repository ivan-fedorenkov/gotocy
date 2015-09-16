package org.gotocy.domain.i18n;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("string")
public class LocalizedStringField extends LocalizedField {

	public LocalizedStringField() {
	}

	public LocalizedStringField(String fieldKey, String stringValue, String language) {
		super(fieldKey, language);
		this.stringValue = stringValue;
	}

	@NotNull
	private String stringValue;

	@Override
	public String getValue() {
		return stringValue;
	}

	@Override
	public void setValue(String value) {
		stringValue = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LocalizedStringField that = (LocalizedStringField) o;
		return Objects.equals(stringValue, that.stringValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), stringValue);
	}

}
