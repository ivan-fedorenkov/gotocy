package org.gotocy.forms.user.profile;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Contacts;
import org.gotocy.domain.GtcUser;

import java.util.List;

/**
 * Form that allows a user to change their profile.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class ProfileForm {

	private String name;
	private String email;
	private String phone;
	private List<String> spokenLanguages;

	public ProfileForm() {
	}

	public ProfileForm(GtcUser user) {
		if (user.getContacts() != null) {
			Contacts userContacts = user.getContacts();
			name = userContacts.getName();
			email = userContacts.getEmail();
			phone = userContacts.getPhone();
			spokenLanguages = userContacts.getSpokenLanguagesList();
		}
	}

	public GtcUser mergeWithGtcUser(GtcUser user) {
		Contacts userContacts = user.getContacts();
		if (userContacts == null)
			userContacts = new Contacts();
		userContacts.setName(name);
		userContacts.setEmail(email);
		userContacts.setPhone(phone);
		userContacts.setSpokenLanguagesList(spokenLanguages);
		user.setContacts(userContacts);
		return user;
	}

}
