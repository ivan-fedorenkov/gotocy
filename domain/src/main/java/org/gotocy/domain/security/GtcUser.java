package org.gotocy.domain.security;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BaseEntity;
import org.gotocy.domain.Contact;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.*;
import java.util.HashSet;
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
	private long registrationDate;

	@OneToMany(mappedBy = "gtcUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GtcUserRole> roles = new HashSet<>();

	@OneToOne(optional = false)
	private Contact contact;

	public void setRoles(Set<GtcUserRole> roles) {
		roles.forEach(role -> role.setGtcUser(this));
		CollectionUtils.updateCollection(this.roles, roles);
	}

}
