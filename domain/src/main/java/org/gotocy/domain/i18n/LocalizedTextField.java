package org.gotocy.domain.i18n;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("text")
public class LocalizedTextField extends LocalizedField {

	public LocalizedTextField() {
	}

	public LocalizedTextField(String fieldKey, String textValue, String language) {
		super(fieldKey, language);
		this.textValue = textValue;
	}

	@Lob
	@NotNull
	private String textValue;

	@Override
	public String getValue() {
		return textValue;
	}

	@Override
	public void setValue(String value) {
		textValue = value;
	}

}
