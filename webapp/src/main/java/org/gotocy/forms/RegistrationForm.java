package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BusinessForm;
import org.gotocy.domain.Registration;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class RegistrationForm {

	private String name;
	private String email;
	private BusinessForm businessForm;
	private int propertiesCount;

	public RegistrationForm() {
		businessForm = BusinessForm.INDIVIDUAL;
	}

	public Registration mergeWithRegistration(Registration registration) {
		registration.setName(name);
		registration.setEmail(email);
		registration.setBusinessForm(businessForm);
		registration.setPropertiesCount(propertiesCount);
		return registration;
	}

}
