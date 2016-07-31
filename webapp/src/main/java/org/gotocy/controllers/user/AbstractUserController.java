package org.gotocy.controllers.user;

import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.GtcUser;
import org.gotocy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Base abstract class of user controllers with a number of utility (shared) methods.
 *
 * @author ifedorenkov
 */
public abstract class AbstractUserController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final UserService userService;

	public AbstractUserController(UserService userService) {
		this.userService = userService;
	}

	protected GtcUser resolveGtcUser(UserDetails userDetails) throws DomainObjectNotFoundException {
		GtcUser user = userService.findByUsername(userDetails.getUsername());
		if (user == null) {
			logger.error("Failed to load user profile of user {}", userDetails.getUsername());
			throw new DomainObjectNotFoundException();
		}
		return user;
	}

}
