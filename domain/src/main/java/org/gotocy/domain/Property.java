package org.gotocy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ifedorenkov
 */
public class Property extends BaseEntity {

	private Set<LocalizedProperty> properties = new HashSet<>();

	public Set<LocalizedProperty> getProperties() {
		return properties;
	}

	public void setProperties(Set<LocalizedProperty> properties) {
		this.properties = properties;
	}

}
