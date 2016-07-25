package org.gotocy.test.factory;

import org.gotocy.domain.Contacts;

/**
 * A factory class of the {@link org.gotocy.domain.Contacts} entities.
 *
 * @author ifedorenkov
 */
public class ContactsFactory extends BaseFactory<Contacts> {

	public static final ContactsFactory INSTANCE = new ContactsFactory();

	private ContactsFactory() {
	}

	@Override
	public Contacts get() {
		Contacts contacts = new Contacts();
		contacts.setName(ANY_STRING);
		contacts.setEmail(ANY_EMAIL);
		contacts.setPhone(ANY_STRING);
		contacts.setSpokenLanguages(ANY_STRING);
		return contacts;
	}

}
