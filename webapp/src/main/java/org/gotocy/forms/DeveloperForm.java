package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Developer;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class DeveloperForm {

	private static final Locale EN_LOCALE = Locale.ENGLISH;
	private static final Locale RU_LOCALE = new Locale("ru");

	private String name;
	private String enDescription;
	private String ruDescription;

	public DeveloperForm() {
	}

	public DeveloperForm(Developer developer) {
		name = developer.getName();
		enDescription = developer.getDescription(EN_LOCALE);
		ruDescription = developer.getDescription(RU_LOCALE);
	}

	public Developer mergeWithDeveloper(Developer developer) {
		developer.setName(name);
		developer.setDescription(enDescription, EN_LOCALE);
		developer.setDescription(ruDescription, RU_LOCALE);
		return developer;
	}

}
