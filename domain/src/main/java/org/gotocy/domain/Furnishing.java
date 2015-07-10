package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * Specifies the furnishing conditions.
 *
 * @author ifedorenkov
 */
public enum Furnishing implements MessageSourceResolvable {
	FULL, SEMI, NONE;

	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.Furnishing." + name()};
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
