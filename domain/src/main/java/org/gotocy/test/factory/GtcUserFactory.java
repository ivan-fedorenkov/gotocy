package org.gotocy.test.factory;

import org.gotocy.domain.GtcUser;

/**
 * @author ifedorenkov
 */
public class GtcUserFactory extends BaseFactory<GtcUser> {

	public static final GtcUserFactory INSTANCE = new GtcUserFactory();

	private GtcUserFactory() {
	}

	@Override
	public GtcUser get() {
		GtcUser user = new GtcUser();
		user.setUsername(ANY_EMAIL);
		user.setPassword(ANY_STRING);
		return user;
	}

}
