package org.gotocy.utils.googlegeo.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class Result {

	private List<String> types = new ArrayList<>();

	@JsonProperty("formatted_address")
	private String formattedAddress;

	@JsonProperty("address_components")
	private List<AddressComponent> addressComponents = new ArrayList<>();

	@JsonProperty("postcode_localities")
	private List<String> postcodeLocalities = new ArrayList<>();

	@JsonIgnore
	private Object geometry;

	@JsonIgnore
	private String partialMatch;

	@JsonProperty("place_id")
	private String placeId;

}
