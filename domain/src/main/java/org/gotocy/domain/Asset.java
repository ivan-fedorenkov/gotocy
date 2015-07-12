package org.gotocy.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

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

}
