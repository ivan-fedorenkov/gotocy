package org.gotocy.repository;

import com.mysema.query.types.expr.BooleanExpression;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.OfferType;
import org.gotocy.domain.Property;

import static org.gotocy.domain.QProperty.property;

/**
 * @author ifedorenkov
 */
public class PropertyPredicates {

	private PropertyPredicates() {
	}

	public static BooleanExpression withOfferType(OfferType offerType) {
		return property.offerType.eq(offerType);
	}

	public static BooleanExpression withOfferInStatus(OfferStatus offerStatus) {
		return property.offerStatus.eq(offerStatus);
	}

	public static BooleanExpression publiclyVisible() {
		return property.offerStatus.ne(OfferStatus.PROMO)
			.and(property.offerStatus.ne(OfferStatus.INACTIVE));
	}

	public static BooleanExpression featured() {
		return property.featured.isTrue();
	}

	public static BooleanExpression ne(Property p) {
		return property.ne(p);
	}

	public static BooleanExpression ofUser(GtcUser user) {
		return property.owner.eq(user);
	}

}
