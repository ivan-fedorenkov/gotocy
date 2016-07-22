package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * A peace of contact details. Could be an email address, a phone, a link to social network profile, etc.
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Contact extends BaseEntity {

	@Column(name = "contact_type")
	@Enumerated(EnumType.STRING)
	private ContactType type;

	@Column(name = "contact_value")
	private String value;

	// Legacy
	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

	public Contact() {
	}

	public Contact(ContactType type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contact contact = (Contact) o;
		return type == contact.type &&
			Objects.equals(value, contact.value) &&
			Objects.equals(name, contact.name) &&
			Objects.equals(email, contact.email) &&
			Objects.equals(phone, contact.phone) &&
			Objects.equals(spokenLanguages, contact.spokenLanguages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, value, name, email, phone, spokenLanguages);
	}
}
