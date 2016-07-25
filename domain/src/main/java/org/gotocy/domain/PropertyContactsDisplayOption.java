package org.gotocy.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * Display options of property's contacts.
 *
 * {@link #SYSTEM_DEFAULT} - display the default system contacts
 * {@link #OVERRIDDEN} - display overridden on per property basis contacts
 * {@link #OWNER} - display owner's contacts
 *
 * @author ifedorenkov
 */
public enum PropertyContactsDisplayOption implements MessageSourceResolvable {
	SYSTEM_DEFAULT, OVERRIDDEN, OWNER;

	@Override
	public String[] getCodes() {
		return new String[] {"org.gotocy.domain.PropertyContactsDisplayOption." + name()};
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
