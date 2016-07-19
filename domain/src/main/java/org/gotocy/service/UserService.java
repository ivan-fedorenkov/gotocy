package org.gotocy.service;

import org.gotocy.domain.security.GtcUser;

/**
 * A service that is responsible for operations on {@link org.gotocy.domain.security.GtcUser} entities.
 *
 * @author ifedorenkov
 */
public interface UserService {

	/**
	 * Searches for a registered user with the given email.
	 */
	GtcUser findByEmail(String email);

	/**
	 * Registers the given user entity.
	 */
	GtcUser register(GtcUser user);

}
