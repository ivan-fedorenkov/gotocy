package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.config.Locales;
import org.gotocy.domain.Developer;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class DeveloperForm {

	private String name;
	private String enDescription;
	private String ruDescription;

	public DeveloperForm() {
	}

	public DeveloperForm(Developer developer) {
		name = developer.getName();
		enDescription = developer.getDescription(Locales.EN);
		ruDescription = developer.getDescription(Locales.RU);
	}

	public Developer mergeWithDeveloper(Developer developer) {
		developer.setName(name);
		developer.setDescription(enDescription, Locales.EN);
		developer.setDescription(ruDescription, Locales.RU);
		return developer;
	}

}
