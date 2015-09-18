package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author ifedorenkov
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseEntity that = (BaseEntity) o;

		return !(id != null ? !id.equals(that.id) : that.id != null);

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}
