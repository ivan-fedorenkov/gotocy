package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Contact;
import org.gotocy.domain.ContactType;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.GtcUserRole;
import org.gotocy.forms.validation.UserRegistrationFormValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Form that is responsible for user registration activity.
 * Validator: {@link UserRegistrationFormValidator}.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class UserRegistrationForm {

	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private Set<String> roles;

	public GtcUser toUser() {
		GtcUser user = new GtcUser();
		user.setUsername(email);
		user.setPassword(password);
		user.setContacts(Arrays.asList(
			new Contact(ContactType.NAME, name),
			new Contact(ContactType.EMAIL, email)
		));

		if (roles == null)
			roles = Collections.emptySet();
		user.setRoles(roles.stream().map(GtcUserRole::new).collect(Collectors.toSet()));

		return user;
	}

}
