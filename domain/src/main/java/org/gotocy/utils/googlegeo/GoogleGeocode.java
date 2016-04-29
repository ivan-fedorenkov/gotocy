package org.gotocy.utils.googlegeo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.StringJoiner;

/**
 * Google Maps Geocoding API access point.
 *
 * @author ifedorenkov
 */
public class GoogleGeocode {

	private static final String GOOGLE_GEOCODE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * Resolves location by passed in {@param latitude} and {@param longitude}.
	 */
	public static FromLatLngResponse backGeocodeRequest(double latitude, double longitude, String apiKey)
		throws IOException
	{
		StringJoiner params = new StringJoiner("&");
		params.add("latlng=" + latitude + "," + longitude);
		params.add("key=" + apiKey);
		URL url = new URL(GOOGLE_GEOCODE_API_URL + params);
		return OBJECT_MAPPER.readValue(url.openStream(), FromLatLngResponse.class);
	}

}
