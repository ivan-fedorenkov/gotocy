package org.gotocy.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO object that represents {@link org.gotocy.domain.Property} to the end user (e.g. in the JSON form).
 * The idea is to hide internal representation details + remove the JPA specifics from the object.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertyDto {

	private String title;
	private double latitude;
	private double longitude;
	private String shortAddress;
	private String typeIcon;
	private String price;
	private String propertyUrl;
	private String representativeImageUrl;

}
