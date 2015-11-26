package org.gotocy.test.factory;

import org.gotocy.domain.Developer;

/**
 * @author ifedorenkov
 */
public class DeveloperFactory extends BaseFactory<Developer> {

	public static final DeveloperFactory INSTANCE = new DeveloperFactory();

	private DeveloperFactory() {
	}

	@Override
	public Developer get() {
		Developer developer = new Developer();
		developer.setName(ANY_STRING);
		return developer;
	}

}
