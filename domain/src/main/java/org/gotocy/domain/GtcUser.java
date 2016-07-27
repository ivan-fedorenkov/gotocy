package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This is the object which represents database state of a user.
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class GtcUser extends BaseEntity {

	private String username;
	private String password;
	private LocalDate registrationDate;

	@Embedded
	private Contacts contacts;

	@OneToMany(mappedBy = "gtcUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GtcUserRole> roles = new HashSet<>();

	public void setRoles(Set<GtcUserRole> roles) {
		roles.forEach(role -> role.setGtcUser(this));
		CollectionUtils.updateCollection(this.roles, roles);
	}

	public Contacts getContacts() {
		return contacts == null ? Contacts.EMPTY : contacts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GtcUser gtcUser = (GtcUser) o;
		return Objects.equals(username, gtcUser.username) &&
			Objects.equals(password, gtcUser.password) &&
			Objects.equals(roles, gtcUser.roles) &&
			Objects.equals(contacts, gtcUser.contacts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, roles, contacts);
	}

}
