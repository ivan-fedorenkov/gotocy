package org.gotocy.utils;

import org.gotocy.domain.Location;
import org.gotocy.utils.googlegeo.FromLatLngResponse;
import org.gotocy.utils.googlegeo.GoogleGeocode;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ifedorenkov
 */
@RunWith(Parameterized.class)
public class GoogleBackGeocodeTest {

	private static final String API_KEY = "API_KEY";

	private final double latitude;
	private final double longitude;
	private final Location expectedLocation;
	private final String expectedAddress;

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{35.175391, 32.552938, Location.PAPHOS, "Pomos, Cyprus"},
			{34.820746, 33.58592, Location.LARNACA, "Perivolia, Cyprus"},
			{35.024763, 34.01413, Location.PROTARAS, "Paralimni, Cyprus"},
			{35.043414851659364, 34.024990827478064, Location.PROTARAS, "Paralimni, Cyprus"},
			{34.988733, 33.992505, Location.AYIA_NAPA, "Ayia Napa, Cyprus"},
			{35.000305, 34.065412, Location.PROTARAS, "Protaras, Cyprus"},
			{34.893825, 32.861229, Location.TROODOS, "Pano Platres, Cyprus"}
		});
	}

	public GoogleBackGeocodeTest(double latitude, double longitude, Location expectedLocation,
		String expectedAddress)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.expectedLocation = expectedLocation;
		this.expectedAddress = expectedAddress;
	}

	/**
	 * This test takes quite a long time, so ignore by default.
	 */
	@Test
	@Ignore
	public void testBackGeocoding() throws Exception {
		FromLatLngResponse response = GoogleGeocode.backGeocodeRequest(latitude, longitude, API_KEY);
		Assert.assertEquals(expectedLocation, response.getLocation());
		Assert.assertEquals(expectedAddress, response.getAddress());
	}

}
