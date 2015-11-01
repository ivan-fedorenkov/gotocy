package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Identifiable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Version
	private int version;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseEntity that = (BaseEntity) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(version, that.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, version);
	}
}
