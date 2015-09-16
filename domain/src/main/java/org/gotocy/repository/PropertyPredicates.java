package org.gotocy.repository;

import com.mysema.query.BooleanBuilder;
import org.gotocy.domain.Property;

import static org.gotocy.domain.QProperty.property;

/**
 * @author ifedorenkov
 */
public class PropertyPredicates {

	private PropertyPredicates() {
	}

	public static BooleanBuilder similarWith(Property p) {
		return new BooleanBuilder(property.propertyStatus.eq(p.getPropertyStatus()))
			.and(property.location.eq(p.getLocation())).and(property.ne(p));
	}

}
