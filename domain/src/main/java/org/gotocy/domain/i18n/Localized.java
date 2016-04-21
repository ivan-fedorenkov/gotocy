package org.gotocy.domain.i18n;

import java.util.List;
import java.util.Locale;

/**
 * Base interface for entities that support localization.
 *
 * @param <T> type of entity that is localized
 * @param <V> type of localized entity
 *
 * @author ifedorenkov
 */
public interface Localized<T extends Localized<T, V>, V extends LocalizedEntity<T, V>> {

	/**
	 * Returns a list of localized fields.
	 */
	List<LocalizedField> getLocalizedFields();

	/**
	 * Returns localized version of this entity.
	 */
	V localize(Locale locale);

}
