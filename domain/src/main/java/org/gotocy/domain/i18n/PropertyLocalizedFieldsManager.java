package org.gotocy.domain.i18n;

import org.gotocy.domain.Property;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
public class PropertyLocalizedFieldsManager extends LocalizedFieldsManager {

	private static final String DESCRIPTION_KEY = "description";
	private static final String FEATURE_KEY = "feature";

	private final Property property;

	public PropertyLocalizedFieldsManager(Property property) {
		this.property = property;
	}

	@Override
	public void setFields(Locale locale) {
		property.setDescription(getFieldValue(DESCRIPTION_KEY, locale).orElse(""));
		property.setFeatures(getFieldValues(FEATURE_KEY, locale));
	}

	@Override
	public List<LocalizedField> getFields() {
		return property.getLocalizedFields();
	}

	public void setDescription(String description, Locale locale) {
		Optional<LocalizedField> field = getField(DESCRIPTION_KEY, locale);

		// Substitute the description field value
		if (field.isPresent()) {
			field.get().setValue(description);
		} else {
			getFields().add(new LocalizedTextField(DESCRIPTION_KEY, description, locale.getLanguage()));
		}
	}

	public Optional<String> getDescription(Locale locale) {
		return getFieldValue(DESCRIPTION_KEY, locale);
	}

	public List<String> getFeatures(Locale locale) {
		return getFieldValues(FEATURE_KEY, locale);
	}

	public void setFeatures(List<String> features, Locale locale) {
		List<LocalizedField> existingFeatures = getFieldList(FEATURE_KEY, locale);
		getFields().removeAll(existingFeatures);

		getFields().addAll(features.stream()
			.map(f -> new LocalizedStringField(FEATURE_KEY, f, locale.getLanguage()))
			.collect(toList()));
	}

}
