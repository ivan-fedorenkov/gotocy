package org.gotocy.domain.i18n;

import org.gotocy.domain.Developer;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public class DeveloperLocalizedFieldsManager extends LocalizedFieldsManager {
	private static final String DESCRIPTION_KEY = "description";

	private final Developer developer;

	public DeveloperLocalizedFieldsManager(Developer developer) {
		this.developer = developer;
	}

	@Override
	public void setFields(Locale locale) {
		developer.setDescription(getFieldValue(DESCRIPTION_KEY, locale).orElse(""));
	}

	@Override
	public List<LocalizedField> getFields() {
		return developer.getLocalizedFields();
	}

	public void setDescription(String description, Locale locale) {
		updateTextField(DESCRIPTION_KEY, description, locale);
	}

	public Optional<String> getDescription(Locale locale) {
		return getFieldValue(DESCRIPTION_KEY, locale);
	}

}
