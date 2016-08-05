package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.OfferType;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.*;

/**
 * SEO uri property status formatter.
 *
 * @author ifedorenkov
 */
public class SeoOfferTypeFormatter implements Formatter<OfferType> {

	public static final String RU_SALE = "prodazha";
	public static final String RU_SHORT_TERM = "kratkosrochnaya-arenda";
	public static final String RU_LONG_TERM = "dolgosrochnaya-arenda";

	public static final String[] RU_STATUSES = new String[] {RU_SALE, RU_SHORT_TERM, RU_LONG_TERM};

	public static final String SALE = "for-sale";
	public static final String SHORT_TERM = "short-term-rental";
	public static final String LONG_TERM = "long-term-rental";

	public static final String[] STATUSES = new String[] {SALE, SHORT_TERM, LONG_TERM};

	private static final Map<String, OfferType> RU_STR_TO_STATUS;
	private static final Map<String, OfferType> STR_TO_STATUS;

	private static final Map<OfferType, String> RU_STATUS_TO_STR;
	private static final Map<OfferType, String> STATUS_TO_STR;

	static {
		Map<String, OfferType> ruStrToStatus = new HashMap<>(RU_STATUSES.length);
		ruStrToStatus.put(RU_SALE, OfferType.SALE);
		ruStrToStatus.put(RU_SHORT_TERM, OfferType.SHORT_TERM);
		ruStrToStatus.put(RU_LONG_TERM, OfferType.LONG_TERM);
		RU_STR_TO_STATUS = Collections.unmodifiableMap(ruStrToStatus);

		Map<String, OfferType> strToStatus = new HashMap<>(STATUSES.length);
		strToStatus.put(SALE, OfferType.SALE);
		strToStatus.put(SHORT_TERM, OfferType.SHORT_TERM);
		strToStatus.put(LONG_TERM, OfferType.LONG_TERM);
		STR_TO_STATUS = Collections.unmodifiableMap(strToStatus);

		Map<OfferType, String> ruStatusToStr = new EnumMap<>(OfferType.class);
		ruStatusToStr.put(OfferType.SALE, RU_SALE);
		ruStatusToStr.put(OfferType.SHORT_TERM, RU_SHORT_TERM);
		ruStatusToStr.put(OfferType.LONG_TERM, RU_LONG_TERM);
		RU_STATUS_TO_STR = Collections.unmodifiableMap(ruStatusToStr);

		Map<OfferType, String> statusToStr = new EnumMap<>(OfferType.class);
		statusToStr.put(OfferType.SALE, SALE);
		statusToStr.put(OfferType.SHORT_TERM, SHORT_TERM);
		statusToStr.put(OfferType.LONG_TERM, LONG_TERM);
		STATUS_TO_STR = Collections.unmodifiableMap(statusToStr);
	}

	@Override
	public OfferType parse(String offerTypeStr, Locale locale) throws ParseException {
		Map<String, OfferType> strToStatus = locale == Locales.RU ? RU_STR_TO_STATUS : STR_TO_STATUS;
		OfferType offerType = strToStatus.get(offerTypeStr);
		if (offerType == null)
			throw new ParseException("Unknown property status: " + offerTypeStr, 0);
		return offerType;
	}

	@Override
	public String print(OfferType offerType, Locale locale) {
		Map<OfferType, String> statusToStr = locale == Locales.RU ? RU_STATUS_TO_STR : STATUS_TO_STR;
		return statusToStr.get(offerType);
	}

}
