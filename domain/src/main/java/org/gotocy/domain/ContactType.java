package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * A type of contact. Could be an email, a phone, etc.
 *
 * @author ifedorenkov
 */
public enum ContactType implements MessageSourceResolvable {
	LEGACY, NAME, EMAIL, PHONE, SPOKEN_LANGUAGES;


	@Override
	public String[] getCodes() {
		return new String[] { "org.gotocy.domain.ContactType." + name() };
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
