package org.gotocy.utils.googlegeo.internal;

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
public class AddressComponent {

	private List<String> types = new ArrayList<>();

	@JsonProperty("long_name")
	private String longName;

	@JsonProperty("short_name")
	private String shortName;

}
