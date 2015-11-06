package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BusinessForm;

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

}
