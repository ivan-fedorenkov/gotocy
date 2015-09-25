package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * An contact of a property object(s).
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Contact extends BaseEntity {

	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Contact)) return false;
		if (!super.equals(o)) return false;
		Contact contact = (Contact) o;
		return Objects.equals(getName(), contact.getName()) &&
			Objects.equals(getEmail(), contact.getEmail()) &&
			Objects.equals(getPhone(), contact.getPhone()) &&
			Objects.equals(getSpokenLanguages(), contact.getSpokenLanguages());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), getEmail(), getPhone(), getSpokenLanguages());
	}
}
