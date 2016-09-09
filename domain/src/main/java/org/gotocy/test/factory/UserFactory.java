package org.gotocy.test.factory;

import org.gotocy.config.Roles;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.GtcUserRole;

import java.util.Collections;

/**
 * @author ifedorenkov
 */
public class UserFactory extends BaseFactory<GtcUser> {
	public static final UserFactory INSTANCE = new UserFactory();

	@Override
	public GtcUser get() {
		GtcUser user = new GtcUser();
		user.setUsername(ANY_STRING);
		user.setPassword(ANY_STRING);
		user.setContacts(ContactsFactory.INSTANCE.get());
		user.setRoles(Collections.singleton(new GtcUserRole(Roles.ROLE_USER)));
		return user;
	}

	public GtcUser getMaster() {
		GtcUser master = get();
		master.getRoles().add(new GtcUserRole(Roles.ROLE_MASTER));
		return master;
	}

}
