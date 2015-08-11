package org.gotocy.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.util.Objects;

/**
 * An abstract asset which could be an image,a pano xml file, etc.
 * @param <T> is the type of the underlying object
 *
 * @author ifedorenkov
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "asset_type")
public abstract class Asset<T> extends BaseEntity {

	@Column(name = "asset_key")
	private String key;

	protected transient T object;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Asset<?> asset = (Asset<?>) o;
		return Objects.equals(key, asset.key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}
}
