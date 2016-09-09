package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * A property offer status.
 *
 * @author ifedorenkov
 */
public enum OfferStatus implements MessageSourceResolvable {
	INACTIVE, ACTIVE, BOOKED, SOLD, RENTED, PROMO;

	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.OfferStatus." + name()};
	}

	@Override
	public Object[] getArguments() {
		return new Object[0];
	}

	@Override
	public String getDefaultMessage() {
		return name();
	}

	/**
	 * Returns an array of statuses that a user can apply to one of their offers.
	 * Unit test: OfferStatusTest#testGetAvailableForUser
	 */
	public static OfferStatus[] getAvailableForUser(GtcUser user, Property property) {
		if (user.isMaster())
			return Constants.AVAILABLE_FOR_MASTER;
		return property.isPromo() ? Constants.PROMO : Constants.AVAILABLE_FOR_USER;
	}

	private static class Constants {
		private static final OfferStatus[] PROMO = { OfferStatus.PROMO };
		private static final OfferStatus[] AVAILABLE_FOR_USER = { INACTIVE, ACTIVE, BOOKED, SOLD, RENTED };
		private static final OfferStatus[] AVAILABLE_FOR_MASTER = OfferStatus.values();
	}

}
