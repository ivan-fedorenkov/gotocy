package org.gotocy.crawl.cyprusreality;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Property;

import java.util.*;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class CyprusRealityProperty {

	private String sourceUrl;
	private String sourceId;

	private String title;
	private String location;
	private double latitude;
	private double longitude;
	private String propertyType;
	private String offerType;
	private String readyToMoveIn;
	private int price;
	private int bedrooms;
	private int plotSize;
	private int coveredArea;
	private int distanceToSea;
	private String furniture;
	private String description;
	private Set<String> features = new HashSet<>();

	public Property toProperty() {
		return null;
	}
}
