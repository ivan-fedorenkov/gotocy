package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * A property location. Currently all locations are stored as a enum.
 *
 * @author ifedorenkov
 */
public enum Location implements MessageSourceResolvable {
	FAMAGUSTA,
	LIMASSOL,
	LARNACA,
	NICOSIA,
	PAPHOS;

	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.Location." + name()};
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
