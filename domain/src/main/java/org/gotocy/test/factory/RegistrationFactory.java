package org.gotocy.test.factory;

import org.gotocy.domain.BusinessForm;
import org.gotocy.domain.Registration;

/**
 * @author ifedorenkov
 */
public class RegistrationFactory extends BaseFactory<Registration> {

	public static final RegistrationFactory INSTANCE = new RegistrationFactory();

	private RegistrationFactory() {
	}

	@Override
	public Registration get() {
		Registration registration = new Registration();
		registration.setName(ANY_STRING);
		registration.setEmail(ANY_EMAIL);
		registration.setBusinessForm(BusinessForm.INDIVIDUAL);
		registration.setPropertiesCount(ANY_INT);
		return registration;
	}

}
