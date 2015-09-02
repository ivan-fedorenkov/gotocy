package org.gotocy.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.gotocy.domain.LocalizedProperty;

import java.util.Locale;

import static org.gotocy.domain.QLocalizedProperty.localizedProperty;

/**
 * @author ifedorenkov
 */
public class LocalizedPropertyPredicates {

	private LocalizedPropertyPredicates() {
	}

	public static Predicate forLocale(Locale locale) {
		return localizedProperty.locale.eq(locale.getLanguage());
	}

	public static BooleanBuilder similarWith(LocalizedProperty lp) {
		return new BooleanBuilder(localizedProperty.property.propertyStatus.eq(lp.getProperty().getPropertyStatus()))
			.and(localizedProperty.property.location.eq(lp.getProperty().getLocation()))
			.and(localizedProperty.locale.eq(lp.getLocale()))
			.and(localizedProperty.ne(lp));
	}

}
