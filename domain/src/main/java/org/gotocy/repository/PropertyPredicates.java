package org.gotocy.repository;

import com.mysema.query.BooleanBuilder;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;

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

	public static BooleanBuilder withPropertyStatus(PropertyStatus propertyStatus) {
		return new BooleanBuilder(property.propertyStatus.eq(propertyStatus));
	}

	public static BooleanBuilder publiclyVisible() {
		return new BooleanBuilder(property.offerStatus.ne(OfferStatus.PROMO));
	}

}
