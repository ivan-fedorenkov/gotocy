package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * Forms of business.
 *
 * @author ifedorenkov
 */
public enum BusinessForm implements MessageSourceResolvable {

	INDIVIDUAL, AGENT, DEVELOPER;


	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.BusinessForm." + name()};
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
