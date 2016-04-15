package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.PropertyType;
import org.gotocy.format.Declension;
import org.gotocy.format.DeclensionAwarePrinter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author ifedorenkov
 */
public class SeoPropertyTypeFormatter implements Formatter<PropertyType>, DeclensionAwarePrinter<PropertyType> {

	public static final String RU_NO_TYPE_NOMINATIVE = "nedvizhimost";
	public static final String RU_NO_TYPE_ACCUSATIVE = "nedvizhimosti";

	public static final String RU_HOUSES_NOMINATIVE = "doma";
	public static final String RU_HOUSES_ACCUSATIVE = "domov";

	public static final String RU_APARTMENTS_NOMINATIVE = "apartamenti";
	public static final String RU_APARTMENTS_ACCUSATIVE = "apartamentov";

	public static final String RU_LAND_NOMINATIVE = "zemlya";
	public static final String RU_LAND_ACCUSATIVE = "zemli";

	public static final String[] RU_TYPES = new String[] {
		RU_NO_TYPE_NOMINATIVE, RU_NO_TYPE_ACCUSATIVE,
		RU_HOUSES_NOMINATIVE, RU_HOUSES_ACCUSATIVE,
		RU_APARTMENTS_NOMINATIVE, RU_APARTMENTS_ACCUSATIVE,
		RU_LAND_NOMINATIVE, RU_LAND_ACCUSATIVE};

	public static final String NO_TYPE = "properties";
	public static final String HOUSES = "houses";
	public static final String APARTMENTS = "apartments";
	public static final String LAND = "land";

	public static final String[] TYPES = new String[] { NO_TYPE, HOUSES, APARTMENTS, LAND };

	private static final Map<String, PropertyType> RU_STR_TO_TYPE;
	private static final Map<String, PropertyType> STR_TO_TYPE;

	private static final Map<PropertyType, String> RU_TYPE_TO_STR_NOMINATIVE;
	private static final Map<PropertyType, String> RU_TYPE_TO_STR_ACCUSATIVE;
	private static final Map<PropertyType, String> TYPE_TO_STR;

	static {
		Map<String, PropertyType> ruStrToType = new HashMap<>();
		ruStrToType.put(RU_NO_TYPE_NOMINATIVE, null);
		ruStrToType.put(RU_NO_TYPE_ACCUSATIVE, null);
		ruStrToType.put(RU_HOUSES_NOMINATIVE, PropertyType.HOUSE);
		ruStrToType.put(RU_HOUSES_ACCUSATIVE, PropertyType.HOUSE);
		ruStrToType.put(RU_APARTMENTS_NOMINATIVE, PropertyType.APARTMENT);
		ruStrToType.put(RU_APARTMENTS_ACCUSATIVE, PropertyType.APARTMENT);
		ruStrToType.put(RU_LAND_NOMINATIVE, PropertyType.LAND);
		ruStrToType.put(RU_LAND_ACCUSATIVE, PropertyType.LAND);
		RU_STR_TO_TYPE = Collections.unmodifiableMap(ruStrToType);

		Map<String, PropertyType> strToType = new HashMap<>();
		strToType.put(NO_TYPE, null);
		strToType.put(HOUSES, PropertyType.HOUSE);
		strToType.put(APARTMENTS, PropertyType.APARTMENT);
		strToType.put(LAND, PropertyType.LAND);
		STR_TO_TYPE = Collections.unmodifiableMap(strToType);

		Map<PropertyType, String> ruTypeToStrNominative = new HashMap<>();
		ruTypeToStrNominative.put(null, RU_NO_TYPE_NOMINATIVE);
		ruTypeToStrNominative.put(PropertyType.HOUSE, RU_HOUSES_NOMINATIVE);
		ruTypeToStrNominative.put(PropertyType.APARTMENT, RU_APARTMENTS_NOMINATIVE);
		ruTypeToStrNominative.put(PropertyType.LAND, RU_LAND_NOMINATIVE);
		RU_TYPE_TO_STR_NOMINATIVE = Collections.unmodifiableMap(ruTypeToStrNominative);

		Map<PropertyType, String> ruTypeToStrAccusative = new HashMap<>();
		ruTypeToStrAccusative.put(null, RU_NO_TYPE_ACCUSATIVE);
		ruTypeToStrAccusative.put(PropertyType.HOUSE, RU_HOUSES_ACCUSATIVE);
		ruTypeToStrAccusative.put(PropertyType.APARTMENT, RU_APARTMENTS_ACCUSATIVE);
		ruTypeToStrAccusative.put(PropertyType.LAND, RU_LAND_ACCUSATIVE);
		RU_TYPE_TO_STR_ACCUSATIVE = Collections.unmodifiableMap(ruTypeToStrAccusative);

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
		return print(propertyType, Declension.NOMINATIVE, locale);
	}

	@Override
	public String print(PropertyType propertyType, Declension declension, Locale locale) {
		Map<PropertyType, String> typeToStr = locale != Locales.RU ? TYPE_TO_STR :
			declension == Declension.ACCUSATIVE ? RU_TYPE_TO_STR_ACCUSATIVE : RU_TYPE_TO_STR_NOMINATIVE;
		return typeToStr.get(propertyType);
	}

}
