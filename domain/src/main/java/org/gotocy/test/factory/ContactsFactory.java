package org.gotocy.test.factory;

import org.gotocy.domain.Contact;
import org.gotocy.domain.ContactType;

import java.util.HashSet;
import java.util.Set;

/**
 * A factory class of the {@link org.gotocy.domain.Contact} entities.
 *
 * @author ifedorenkov
 */
public class ContactsFactory extends BaseFactory<Set<Contact>> {

	public static final ContactsFactory INSTANCE = new ContactsFactory();

	private ContactsFactory() {
	}

	@Override
	public Set<Contact> get() {
		Set<Contact> contacts = new HashSet<>();
		contacts.add(new Contact(ContactType.NAME, ANY_STRING));
		contacts.add(new Contact(ContactType.EMAIL, ANY_EMAIL));
		contacts.add(new Contact(ContactType.PHONE, ANY_STRING));
		contacts.add(new Contact(ContactType.SPOKEN_LANGUAGES, ANY_STRING));
		return contacts;
	}

}
