package org.gotocy.crawl.cyprusreality;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class CyprusRealityProperty {

	private String title;
	private String url;
	private Map<String, String> features = new HashMap<>();

	public Property toProperty() {
		Property property = new Property();
		property.setTitle(title);
		List<String> propertyFeatures = new ArrayList<>(features.size());
		propertyFeatures.add("url: " + url);
		features.forEach((key, value) -> propertyFeatures.add(key + value));
		property.setFeatures(propertyFeatures);
		return property;
	}

}
