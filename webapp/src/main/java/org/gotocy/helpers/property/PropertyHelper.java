package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.helpers.Format;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A number of property related helper methods for the view layer.
 *
 * @author ifedorenkov
 */
public class PropertyHelper {

	private static final Map<PropertyStatus, String> PROPERTY_STATUS_TO_PRICE_KEY = new HashMap<>();

	static {
		for (PropertyStatus status : PropertyStatus.values()) {
			PROPERTY_STATUS_TO_PRICE_KEY.put(status, "org.gotocy.domain.property." +
				status.name().toLowerCase().replaceAll("_", "-") + "-price");
		}
	}

	private final MessageSource messageSource;

	public PropertyHelper(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Just a convenient method for the view layer.
	 */
	public String price(Property property) {
		return price(messageSource, property);
	}

	/**
	 * Just a convenient method for the view layer.
	 */
	public String distance(int meters) {
		return distance(messageSource, meters);
	}

	/**
	 * Generates the quick summary html code.
	 */
	public String quickSummaryHtml(Property p) {
		QuickSummaryGenerator generator = null;

		switch (p.getPropertyStatus()) {
		case LONG_TERM:
			generator = QuickSummaryGenerator.LONG_TERM;
			break;
		case SHORT_TERM:
			generator = QuickSummaryGenerator.SHORT_TERM;
			break;
		case SALE:
			switch (p.getPropertyType()) {
			case HOUSE:
				generator = QuickSummaryGenerator.SALE_HOUSE;
				break;
			case APARTMENT:
				generator = QuickSummaryGenerator.SALE_APARTMENT;
				break;
			case LAND:
				generator = QuickSummaryGenerator.SALE_LAND;
			}
		}

		return generator.generateHtml(messageSource, p);
	}

	/**
	 * Returns suitable icon for the given {@link PropertyType}.
	 * Unit test: PropertyHelperTest#typeIcon
	 */
	public static String typeIcon(PropertyType type) {
		switch (type) {
		case HOUSE:
			return "single-family";
		case APARTMENT:
			return "apartment";
		case LAND:
			return "land";
		default:
			// TODO: log error
			return "";
		}
	}

	/**
	 * Returns a fully formatted price of the given property.
	 * TODO: unit test
	 */
	public static String price(MessageSource messageSource, Property property) {
		return messageSource.getMessage(PROPERTY_STATUS_TO_PRICE_KEY.get(property.getPropertyStatus()),
			new Object[]{price(property.getPrice())}, LocaleContextHolder.getLocale());
	}

	/**
	 * Returns the distance in the appropriate form, which is: < 500 m - meters (m), > 500 m - kilometers (km).
	 * TODO: unit test
	 */
	public static String distance(MessageSource messageSource, int meters) {
		Locale locale = LocaleContextHolder.getLocale();
		String ending;
		Number number;
		if (meters < 500) {
			number = meters;
			ending = messageSource.getMessage("org.gotocy.dictionary.meters", null, locale);
		} else {
			number = meters / 1000d;
			ending = messageSource.getMessage("org.gotocy.dictionary.kilometers", null, locale);
		}
		return Format.DISTANCE_FORMATTER.print(number, locale) + " " + ending;
	}

	/**
	 * Prints a given price in a locale specific manner.
	 */
	private static String price(int price) {
		return "&euro; " + Format.CURRENCY_FORMATTER.print(price, LocaleContextHolder.getLocale());
	}

}
