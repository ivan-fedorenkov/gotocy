package org.gotocy.domain;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * An owner of a property object(s).
 *
 * @author ifedorenkov
 */
@Entity
public class Owner extends BaseEntity {

	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(String spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Owner)) return false;
		if (!super.equals(o)) return false;
		Owner owner = (Owner) o;
		return Objects.equals(getName(), owner.getName()) &&
			Objects.equals(getEmail(), owner.getEmail()) &&
			Objects.equals(getPhone(), owner.getPhone()) &&
			Objects.equals(getSpokenLanguages(), owner.getSpokenLanguages());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), getEmail(), getPhone(), getSpokenLanguages());
	}
}
