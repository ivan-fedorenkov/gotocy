package org.gotocy.service;

import org.gotocy.domain.Contact;
import org.gotocy.domain.security.GtcUser;

/**
 * A service that is responsible for operations on {@link org.gotocy.domain.security.GtcUser} entities.
 *
 * @author ifedorenkov
 */
public interface UserService {

	/**
	 * Searches for a registered user with the given username.
	 *
	 * @param username of user
	 * @return existing user or {@code null} if none could be found for the given username
	 */
	GtcUser findByUsername(String username);

	/**
	 * Registers the given user entity.
	 *
	 * @param user to be registered
	 * @param contact details of this user
	 * @return registered user
	 */
	GtcUser register(GtcUser user, Contact contact);

}
