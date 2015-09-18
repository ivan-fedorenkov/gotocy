package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * An owner of a property object(s).
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Owner extends BaseEntity {

	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

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
