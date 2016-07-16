package org.gotocy.domain.security;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
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

	private String role;

	@Override
	public String getAuthority() {
		return getRole();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		GtcUserRole userRole = (GtcUserRole) o;
		return Objects.equals(role, userRole.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), role);
	}

	@Override
	public String toString() {
		return "UserRole{" +
			"role='" + role + '\'' +
			"} " + super.toString();
	}

}
