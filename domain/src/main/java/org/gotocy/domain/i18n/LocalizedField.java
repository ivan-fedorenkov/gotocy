package org.gotocy.domain.i18n;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BaseEntity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "field_type")
@Getter
@Setter
public abstract class LocalizedField extends BaseEntity {

	@NotNull
	private String language;

	@NotNull
	private String fieldKey;

	public LocalizedField() {
	}

	public LocalizedField(String fieldKey, String language) {
		this.fieldKey = fieldKey;
		this.language = language;
	}

	public abstract String getValue();

	public abstract void setValue(String value);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LocalizedField that = (LocalizedField) o;
		return Objects.equals(getLanguage(), that.getLanguage()) &&
			Objects.equals(getFieldKey(), that.getFieldKey());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLanguage(), getFieldKey());
	}

}
