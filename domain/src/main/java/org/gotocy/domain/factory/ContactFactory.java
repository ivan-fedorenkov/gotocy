package org.gotocy.domain.factory;

import org.gotocy.domain.Contact;

/**
 * A factory class of the {@link org.gotocy.domain.Contact} entity.
 *
 * @author ifedorenkov
 */
public class ContactFactory extends BaseFactory<Contact> {

	public static final ContactFactory INSTANCE = new ContactFactory();

	private ContactFactory() {
	}

	@Override
	public Contact get() {
		Contact contact = new Contact();
		contact.setEmail(ANY_EMAIL);
		contact.setName(ANY_STRING);
		contact.setPhone(ANY_STRING);
		contact.setSpokenLanguages(ANY_STRING);
		return contact;
	}

}
