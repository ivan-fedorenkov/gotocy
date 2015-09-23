package org.gotocy.domain.i18n;

import org.gotocy.utils.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
public abstract class LocalizedFieldsManager {

	// Abstract methods

	public abstract void setFields(Locale locale);

	public abstract List<LocalizedField> getFields();

	// Helper methods

	protected Optional<LocalizedField> getField(String key, Locale locale) {
		return getFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.findAny();
	}

	protected List<LocalizedField> getFieldList(String key, Locale locale) {
		return getFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.collect(toList());
	}

	protected Optional<String> getFieldValue(String key, Locale locale) {
		return getField(key, locale).map(LocalizedField::getValue);
	}

	protected List<String> getFieldValues(String key, Locale locale) {
		return getFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.map(LocalizedField::getValue)
			.collect(toList());
	}

	protected void updateTextField(String key, String value, Locale locale) {
		Optional<LocalizedField> field = getField(key, locale);

		if (value == null || value.isEmpty()) {
			if (field.isPresent())
				getFields().remove(field.get());
		} else {
			if (field.isPresent()) {
				field.get().setValue(value);
			} else {
				getFields().add(new LocalizedTextField(key, value, locale.getLanguage()));
			}
		}
	}

	protected void updateStringFields(String key, List<String> values, Locale locale) {
		List<LocalizedField> existingFields = getFieldList(key, locale);
		List<LocalizedField> updatedFields = values.stream()
			.map(v -> new LocalizedStringField(key, v, locale.getLanguage()))
			.collect(toList());

		getFields().removeAll(existingFields);
		getFields().addAll(CollectionUtils.updateCollection(existingFields, updatedFields));
	}

}
