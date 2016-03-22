package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.Location;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author ifedorenkov
 */
public class SeoLocationFormatter implements Formatter<Location> {

	public static final String RU_NO_LOCATION = "na-kipre";
	public static final String RU_AYIA_NAPA = "v-ayia-nape";
	public static final String RU_FAMAGUSTA = "v-famaguste";
	public static final String RU_LARNACA = "v-larnake";
	public static final String RU_LIMASSOL = "v-limassole";
	public static final String RU_NICOSIA = "v-nikosii";
	public static final String RU_PAPHOS = "v-pafose";
	public static final String RU_PROTARAS = "v-protarase";
	public static final String RU_TROODOS = "v-troodose";

	public static final String[] RU_LOCATIONS = { RU_NO_LOCATION, RU_AYIA_NAPA, RU_FAMAGUSTA, RU_LARNACA, RU_LIMASSOL,
		RU_NICOSIA, RU_PAPHOS, RU_PROTARAS, RU_TROODOS };

	public static final String NO_LOCATION = "in-cyprus";
	public static final String AYIA_NAPA = "in-ayia-napa";
	public static final String FAMAGUSTA = "in-famagusta";
	public static final String LARNACA = "in-larnaca";
	public static final String LIMASSOL = "in-limassol";
	public static final String NICOSIA = "in-nicosia";
	public static final String PAPHOS = "in-paphos";
	public static final String PROTARAS= "in-protaras";
	public static final String TROODOS = "in-troodos";

	public static final String[] LOCATIONS = { NO_LOCATION, AYIA_NAPA, FAMAGUSTA, LARNACA, LIMASSOL, NICOSIA,
		PAPHOS, PROTARAS, TROODOS };

	private static final Map<String, Location> RU_STR_TO_LOC;
	private static final Map<String, Location> STR_TO_LOC;

	private static final Map<Location, String> RU_LOC_TO_STR;
	private static final Map<Location, String> LOC_TO_STR;

	static {
		Map<String, Location> ruStrToLoc = new HashMap<>();
		ruStrToLoc.put(RU_NO_LOCATION, null);
		ruStrToLoc.put(RU_AYIA_NAPA, Location.AYIA_NAPA);
		ruStrToLoc.put(RU_FAMAGUSTA, Location.FAMAGUSTA);
		ruStrToLoc.put(RU_LARNACA, Location.LARNACA);
		ruStrToLoc.put(RU_LIMASSOL, Location.LIMASSOL);
		ruStrToLoc.put(RU_NICOSIA, Location.NICOSIA);
		ruStrToLoc.put(RU_PAPHOS, Location.PAPHOS);
		ruStrToLoc.put(RU_PROTARAS, Location.PROTARAS);
		ruStrToLoc.put(RU_TROODOS, Location.TROODOS);
		RU_STR_TO_LOC = Collections.unmodifiableMap(ruStrToLoc);

		Map<String, Location> strToLoc = new HashMap<>();
		strToLoc.put(NO_LOCATION, null);
		strToLoc.put(AYIA_NAPA, Location.AYIA_NAPA);
		strToLoc.put(FAMAGUSTA, Location.FAMAGUSTA);
		strToLoc.put(LARNACA, Location.LARNACA);
		strToLoc.put(LIMASSOL, Location.LIMASSOL);
		strToLoc.put(NICOSIA, Location.NICOSIA);
		strToLoc.put(PAPHOS, Location.PAPHOS);
		strToLoc.put(PROTARAS, Location.PROTARAS);
		strToLoc.put(TROODOS, Location.TROODOS);
		STR_TO_LOC = Collections.unmodifiableMap(strToLoc);

		Map<Location, String> ruLocToStr = new HashMap<>();
		ruLocToStr.put(null, RU_NO_LOCATION);
		ruLocToStr.put(Location.AYIA_NAPA, RU_AYIA_NAPA);
		ruLocToStr.put(Location.FAMAGUSTA, RU_FAMAGUSTA);
		ruLocToStr.put(Location.LARNACA, RU_LARNACA);
		ruLocToStr.put(Location.LIMASSOL, RU_LIMASSOL);
		ruLocToStr.put(Location.NICOSIA, RU_NICOSIA);
		ruLocToStr.put(Location.PAPHOS, RU_PAPHOS);
		ruLocToStr.put(Location.PROTARAS, RU_PROTARAS);
		ruLocToStr.put(Location.TROODOS, RU_TROODOS);
		RU_LOC_TO_STR = Collections.unmodifiableMap(ruLocToStr);

		Map<Location, String> locToStr = new HashMap<>();
		locToStr.put(null, NO_LOCATION);
		locToStr.put(Location.AYIA_NAPA, AYIA_NAPA);
		locToStr.put(Location.FAMAGUSTA, FAMAGUSTA);
		locToStr.put(Location.LARNACA, LARNACA);
		locToStr.put(Location.LIMASSOL, LIMASSOL);
		locToStr.put(Location.NICOSIA, NICOSIA);
		locToStr.put(Location.PAPHOS, PAPHOS);
		locToStr.put(Location.PROTARAS, PROTARAS);
		locToStr.put(Location.TROODOS, TROODOS);
		LOC_TO_STR = Collections.unmodifiableMap(locToStr);
	}

	@Override
	public Location parse(String locationStr, Locale locale) throws ParseException {
		Map<String, Location> strToLocation = locale == Locales.RU ? RU_STR_TO_LOC : STR_TO_LOC;
		if (!strToLocation.containsKey(locationStr))
			throw new ParseException("Unknown location: " + locationStr, 0);
		return strToLocation.get(locationStr);
	}

	@Override
	public String print(Location location, Locale locale) {
		Map<Location, String> locToStr = locale == Locales.RU ? RU_LOC_TO_STR : LOC_TO_STR;
		return locToStr.get(location);
	}

}
