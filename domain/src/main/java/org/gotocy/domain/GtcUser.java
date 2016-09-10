package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.config.Roles;
import org.gotocy.utils.CollectionUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
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
public class GtcUser extends BaseEntity implements UserDetails, CredentialsContainer {

	private String username;
	private String password;
	private LocalDate registrationDate;

	@Embedded
	private Contacts contacts;

	@OneToMany(mappedBy = "gtcUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GtcUserRole> roles = new HashSet<>();

	public boolean isMaster() {
		return roles.stream().anyMatch(role -> Roles.ROLE_MASTER.equals(role.getRole()));
	}

	public void setRoles(Set<GtcUserRole> roles) {
		roles.forEach(role -> role.setGtcUser(this));
		CollectionUtils.updateCollection(this.roles, roles);
	}

	public Contacts getContacts() {
		return contacts == null ? Contacts.EMPTY : contacts;
	}

	@Override
	public void eraseCredentials() {
		password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

	@Override
	public String toString() {
		return "GtcUser{" +
			"username='" + username + '\'' +
			", registrationDate=" + registrationDate +
			", roles=" + roles +
			'}';
	}
}
