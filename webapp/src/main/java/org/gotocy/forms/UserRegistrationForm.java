package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.security.GtcUser;
import org.gotocy.domain.security.GtcUserRole;
import org.gotocy.forms.validation.UserRegistrationFormValidator;

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

	private String email;
	private String password;
	private String repeatPassword;
	private Set<String> roles;

	public GtcUser toUser() {
		GtcUser user = new GtcUser();
		user.setEmail(email);
		user.setPassword(password);

		if (roles == null)
			roles = Collections.emptySet();
		user.setRoles(roles.stream().map(GtcUserRole::new).collect(Collectors.toSet()));

		return user;
	}

}
