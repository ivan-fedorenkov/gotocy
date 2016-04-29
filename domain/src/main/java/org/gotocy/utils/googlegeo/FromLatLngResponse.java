package org.gotocy.utils.googlegeo;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Location;
import org.gotocy.utils.googlegeo.internal.AddressComponent;
import org.gotocy.utils.googlegeo.internal.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains results of back geocode request (from lat/lng to location).
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class FromLatLngResponse {

	private static final String AYIA_NAPA = "Ayia Napa";
	private static final String PROTARAS = "Protaras";
	private static final String TROODOS = "Troodos";
	private static final String PANO_PLATRES = "Pano Platres";
	private static final String PARALINMI = "Paralimni";

	private String status;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private List<Result> results = new ArrayList<>();

	// Lazy initialization
	private transient boolean responseParsed;
	private transient Location location;
	private transient String address;

	public boolean isResponseOK() {
		return status != null && Code.valueOf(status) == Code.OK;
	}

	public Location getLocation() {
		if (!responseParsed)
			parseResponse();
		return location;
	}

	public String getAddress() {
		if (!responseParsed)
			parseResponse();
		return address;
	}

	/**
	 * Initializes location and address. Kludgy code...
	 */
	private void parseResponse() {
		if (isResponseOK()) {
			for (Result result : results) {
				List<String> types = result.getTypes();
				if (types.size() > 1) {
					// Best case - we can get all the required information from this result object
					if (types.get(0).equals("locality") && types.get(1).equals("political")) {
						address = result.getFormattedAddress();
						location = resolveLocation(result.getAddressComponents(), address);
					}

					// We can obtain location, but the address would be less accurate
					if (types.get(0).equals("administrative_area_level_1") && types.get(1).equals("political")) {
						if (address == null || address.isEmpty())
							address = result.getFormattedAddress();
						location = resolveLocation(result.getAddressComponents(), address);
					}

					if (address != null && !address.isEmpty() && location != null)
						break;
				}
			}
		}
		responseParsed = true;
	}

	private static Location resolveLocation(List<AddressComponent> addressComponents, String address) {
		for (AddressComponent addressComponent : addressComponents) {
			List<String> types = addressComponent.getTypes();
			if (types.size() > 1) {
				if (types.get(0).equals("administrative_area_level_1") && types.get(1).equals("political"))
					return parseLocation(addressComponent.getLongName(), address);
			}
		}
		return null;
	}

	private static Location parseLocation(String location, String address) {
		if (location.isEmpty())
			return Location.FAMAGUSTA;

		// Special cases of locations that could be obtained only from the address string
		if (address != null && !address.isEmpty()) {
			int commaIndex = address.indexOf(",");
			if (commaIndex > 0) {
				String locationFromAddress = address.substring(0, commaIndex);
				switch (locationFromAddress) {
				case AYIA_NAPA:
					return Location.AYIA_NAPA;
				case PROTARAS:
				case PARALINMI:
					return Location.PROTARAS;
				case TROODOS:
				case PANO_PLATRES:
					return Location.TROODOS;
				}
			}
		}

		return Location.valueOf(location.toUpperCase().replaceAll("\\s", "_"));
	}

	public enum Code {
		OK, ZERO_RESULTS, OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST, UNKNOWN_ERROR
	}

}
