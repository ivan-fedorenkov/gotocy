package org.gotocy.domain.security;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BaseEntity;
import org.gotocy.utils.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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

	private String email;
	private String password;
	private boolean enabled;
	private long registrationDate;

	@OneToMany(mappedBy = "gtcUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GtcUserRole> roles = new HashSet<>();

	public void setRoles(Set<GtcUserRole> roles) {
		roles.forEach(role -> role.setGtcUser(this));
		CollectionUtils.updateCollection(this.roles, roles);
	}

}
