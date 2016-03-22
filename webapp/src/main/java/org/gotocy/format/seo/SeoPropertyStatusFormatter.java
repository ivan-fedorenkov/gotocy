package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.PropertyStatus;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.*;

/**
 * SEO uri property status formatter.
 *
 * @author ifedorenkov
 */
public class SeoPropertyStatusFormatter implements Formatter<PropertyStatus> {

	public static final String RU_SALE = "prodazha";
	public static final String RU_SHORT_TERM = "kratkosrochnaya-arenda";
	public static final String RU_LONG_TERM = "dolgosrochnaya-arenda";

	public static final String[] RU_STATUSES = new String[] {RU_SALE, RU_SHORT_TERM, RU_LONG_TERM};

	public static final String SALE = "for-sale";
	public static final String SHORT_TERM = "short-term-rental";
	public static final String LONG_TERM = "long-term-rental";

	public static final String[] STATUSES = new String[] {SALE, SHORT_TERM, LONG_TERM};

	private static final Map<String, PropertyStatus> RU_STR_TO_STATUS;
	private static final Map<String, PropertyStatus> STR_TO_STATUS;

	private static final Map<PropertyStatus, String> RU_STATUS_TO_STR;
	private static final Map<PropertyStatus, String> STATUS_TO_STR;

	static {
		Map<String, PropertyStatus> ruStrToStatus = new HashMap<>(RU_STATUSES.length);
		ruStrToStatus.put(RU_SALE, PropertyStatus.SALE);
		ruStrToStatus.put(RU_SHORT_TERM, PropertyStatus.SHORT_TERM);
		ruStrToStatus.put(RU_LONG_TERM, PropertyStatus.LONG_TERM);
		RU_STR_TO_STATUS = Collections.unmodifiableMap(ruStrToStatus);

		Map<String, PropertyStatus> strToStatus = new HashMap<>(STATUSES.length);
		strToStatus.put(SALE, PropertyStatus.SALE);
		strToStatus.put(SHORT_TERM, PropertyStatus.SHORT_TERM);
		strToStatus.put(LONG_TERM, PropertyStatus.LONG_TERM);
		STR_TO_STATUS = Collections.unmodifiableMap(strToStatus);

		Map<PropertyStatus, String> ruStatusToStr = new EnumMap<>(PropertyStatus.class);
		ruStatusToStr.put(PropertyStatus.SALE, RU_SALE);
		ruStatusToStr.put(PropertyStatus.SHORT_TERM, RU_SHORT_TERM);
		ruStatusToStr.put(PropertyStatus.LONG_TERM, RU_LONG_TERM);
		RU_STATUS_TO_STR = Collections.unmodifiableMap(ruStatusToStr);

		Map<PropertyStatus, String> statusToStr = new EnumMap<>(PropertyStatus.class);
		statusToStr.put(PropertyStatus.SALE, SALE);
		statusToStr.put(PropertyStatus.SHORT_TERM, SHORT_TERM);
		statusToStr.put(PropertyStatus.LONG_TERM, LONG_TERM);
		STATUS_TO_STR = Collections.unmodifiableMap(statusToStr);
	}

	@Override
	public PropertyStatus parse(String propertyStatusStr, Locale locale) throws ParseException {
		Map<String, PropertyStatus> strToStatus = locale == Locales.RU ? RU_STR_TO_STATUS : STR_TO_STATUS;
		PropertyStatus propertyStatus = strToStatus.get(propertyStatusStr);
		if (propertyStatus == null)
			throw new ParseException("Unknown property status: " + propertyStatusStr, 0);
		return propertyStatus;
	}

	@Override
	public String print(PropertyStatus propertyStatus, Locale locale) {
		Map<PropertyStatus, String> statusToStr = locale == Locales.RU ? RU_STATUS_TO_STR : STATUS_TO_STR;
		return statusToStr.get(propertyStatus);
	}

}
