package org.gotocy.domain.i18n;

import org.gotocy.utils.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Base class for localized version of the {@link Localized} entity.
 *
 * @param <T> type of entity that is localized
 * @param <V> type of localized entity
 *
 * @author ifedorenkov
 */
abstract class LocalizedEntity<T extends Localized<T, V>, V extends LocalizedEntity<T, V>> {

	private final T entity;
	private final Locale locale;

	public LocalizedEntity(T entity, Locale locale) {
		this.entity = entity;
		this.locale = locale;
	}

	/**
	 * Returns the original entity (non translated).
	 */
	public T getOriginal() {
		return entity;
	}

	/**
	 * Returns the locale of this localized entity instance.
	 */
	public Locale getLocale() {
		return locale;
	}

	Optional<LocalizedField> getField(String key) {
		return entity.getLocalizedFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.findAny();
	}

	List<LocalizedField> getFieldList(String key) {
		return entity.getLocalizedFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.collect(toList());
	}

	Optional<String> getFieldValue(String key) {
		return getField(key).map(LocalizedField::getValue);
	}

	List<String> getFieldValues(String key) {
		return entity.getLocalizedFields().stream()
			.filter(field -> key.equals(field.getFieldKey()) && locale.getLanguage().equals(field.getLanguage()))
			.map(LocalizedField::getValue)
			.collect(toList());
	}

	void updateTextField(String key, String value) {
		Optional<LocalizedField> field = getField(key);

		if (value == null || value.isEmpty()) {
			if (field.isPresent())
				entity.getLocalizedFields().remove(field.get());
		} else {
			if (field.isPresent()) {
				field.get().setValue(value);
			} else {
				entity.getLocalizedFields().add(new LocalizedTextField(key, value, locale.getLanguage()));
			}
		}
	}

	void updateStringField(String key, String value) {
		Optional<LocalizedField> field = getField(key);

		if (value == null || value.isEmpty()) {
			if (field.isPresent())
				entity.getLocalizedFields().remove(field.get());
		} else {
			if (field.isPresent()) {
				field.get().setValue(value);
			} else {
				entity.getLocalizedFields().add(new LocalizedStringField(key, value, locale.getLanguage()));
			}
		}
	}

	void updateStringFields(String key, List<String> values) {
		List<LocalizedField> existingFields = getFieldList(key);
		List<LocalizedField> updatedFields = values.stream()
			.map(v -> new LocalizedStringField(key, v, locale.getLanguage()))
			.collect(toList());

		entity.getLocalizedFields().removeAll(existingFields);
		entity.getLocalizedFields().addAll(CollectionUtils.updateCollection(existingFields, updatedFields));
	}

}
