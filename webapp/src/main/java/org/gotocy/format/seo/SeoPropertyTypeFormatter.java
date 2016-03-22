package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.PropertyType;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author ifedorenkov
 */
public class SeoPropertyTypeFormatter implements Formatter<PropertyType> {

	public static final String RU_NO_TYPE = "nedvizhimosti";
	public static final String RU_HOUSES = "kottedzhei";
	public static final String RU_APARTMENTS = "apartamentov";
	public static final String RU_LAND = "zemli";

	public static final String[] RU_TYPES = new String[] { RU_NO_TYPE, RU_HOUSES, RU_APARTMENTS, RU_LAND };

	public static final String NO_TYPE = "properties";
	public static final String HOUSES = "houses";
	public static final String APARTMENTS = "apartments";
	public static final String LAND = "land";

	public static final String[] TYPES = new String[] { NO_TYPE, HOUSES, APARTMENTS, LAND };

	private static final Map<String, PropertyType> RU_STR_TO_TYPE;
	private static final Map<String, PropertyType> STR_TO_TYPE;

	private static final Map<PropertyType, String> RU_TYPE_TO_STR;
	private static final Map<PropertyType, String> TYPE_TO_STR;

	static {
		Map<String, PropertyType> ruStrToType = new HashMap<>();
		ruStrToType.put(RU_NO_TYPE, null);
		ruStrToType.put(RU_HOUSES, PropertyType.HOUSE);
		ruStrToType.put(RU_APARTMENTS, PropertyType.APARTMENT);
		ruStrToType.put(RU_LAND, PropertyType.LAND);
		RU_STR_TO_TYPE = Collections.unmodifiableMap(ruStrToType);

		Map<String, PropertyType> strToType = new HashMap<>();
		strToType.put(NO_TYPE, null);
		strToType.put(HOUSES, PropertyType.HOUSE);
		strToType.put(APARTMENTS, PropertyType.APARTMENT);
		strToType.put(LAND, PropertyType.LAND);
		STR_TO_TYPE = Collections.unmodifiableMap(strToType);

		Map<PropertyType, String> ruTypeToStr = new HashMap<>();
		ruTypeToStr.put(null, RU_NO_TYPE);
		ruTypeToStr.put(PropertyType.HOUSE, RU_HOUSES);
		ruTypeToStr.put(PropertyType.APARTMENT, RU_APARTMENTS);
		ruTypeToStr.put(PropertyType.LAND, RU_LAND);
		RU_TYPE_TO_STR = Collections.unmodifiableMap(ruTypeToStr);

		Map<PropertyType, String> typeToStr = new HashMap<>();
		typeToStr.put(null, NO_TYPE);
		typeToStr.put(PropertyType.HOUSE, HOUSES);
		typeToStr.put(PropertyType.APARTMENT, APARTMENTS);
		typeToStr.put(PropertyType.LAND, LAND);
		TYPE_TO_STR = Collections.unmodifiableMap(typeToStr);
	}

	@Override
	public PropertyType parse(String propertyTypeStr, Locale locale) throws ParseException {
		Map<String, PropertyType> strToType = locale == Locales.RU ? RU_STR_TO_TYPE : STR_TO_TYPE;
		if (!strToType.containsKey(propertyTypeStr))
			throw new ParseException("Unknown property type: " + propertyTypeStr, 0);
		return strToType.get(propertyTypeStr);
	}

	@Override
	public String print(PropertyType propertyType, Locale locale) {
		Map<PropertyType, String> typeToStr = locale == Locales.RU ? RU_TYPE_TO_STR : TYPE_TO_STR;
		return typeToStr.get(propertyType);
	}

}
