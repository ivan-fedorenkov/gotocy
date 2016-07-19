package org.gotocy.domain.security;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * A role that a user can have.
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class GtcUserRole extends BaseEntity implements GrantedAuthority {

	@ManyToOne(optional = false)
	private GtcUser gtcUser;

	private String role;

	public GtcUserRole() {
	}

	public GtcUserRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return getRole();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GtcUserRole userRole = (GtcUserRole) o;
		return Objects.equals(role, userRole.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(role);
	}

	@Override
	public String toString() {
		return "GtcUserRole{role='" + role + "'}";
	}

}
