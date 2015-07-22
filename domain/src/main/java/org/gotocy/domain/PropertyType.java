package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * A property type. Currently all property types are stored as a enum.
 *
 * @author ifedorenkov
 */
public enum PropertyType implements MessageSourceResolvable {
	HOUSE, APARTMENT, FLAT;


	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.PropertyType." + name()};
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
