package org.gotocy.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

/**
 * An abstract asset which could be an image,a pano xml file, etc.
 *
 * @author ifedorenkov
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "asset_type")
public abstract class Asset extends BaseEntity {

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
