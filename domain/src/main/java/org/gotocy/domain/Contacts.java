package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Embeddable contact details of domain entities.
 *
 * @author ifedorenkov
 */
@Embeddable
@Getter
@Setter
public class Contacts {

	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contacts contacts = (Contacts) o;
		return Objects.equals(name, contacts.name) &&
			Objects.equals(email, contacts.email) &&
			Objects.equals(phone, contacts.phone) &&
			Objects.equals(spokenLanguages, contacts.spokenLanguages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, phone, spokenLanguages);
	}

}
