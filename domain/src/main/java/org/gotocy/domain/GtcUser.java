package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

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
	private long registrationDate;

	@OneToMany(mappedBy = "gtcUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GtcUserRole> roles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Contact> contacts = new HashSet<>();

	public void setRoles(Set<GtcUserRole> roles) {
		roles.forEach(role -> role.setGtcUser(this));
		CollectionUtils.updateCollection(this.roles, roles);
	}

	public void setContacts(List<Contact> contacts) {
		CollectionUtils.updateCollection(this.contacts, contacts);
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
