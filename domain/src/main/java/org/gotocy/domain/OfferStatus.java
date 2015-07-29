package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * A property offer status.
 *
 * @author ifedorenkov
 */
public enum OfferStatus implements MessageSourceResolvable {
	ACTIVE, BOOKED, SOLD, RENTED;

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
}
