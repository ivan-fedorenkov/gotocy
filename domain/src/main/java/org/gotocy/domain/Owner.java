package org.gotocy.domain;

import javax.persistence.Entity;

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
}
