package org.gotocy.crawl.mayfair;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Image;

import java.util.List;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class MayfairProperty {

	private int id;

	private String refNumber;

	private String title;

	private String propertyType;

	private String offerType;

	/**
	 *	VAT info or rental period
	 */
	private String searchTitles;

	private String city;

	private String area;

	private String description;

	private int price;

	private int plotSize;

	private int coveredArea;

	private int bedrooms;

	private List<Image> images;

	private List<String> features;

}
