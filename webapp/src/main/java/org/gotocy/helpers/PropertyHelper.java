package org.gotocy.helpers;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.ImageSize;
import org.gotocy.domain.OfferType;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyType;
import org.gotocy.format.CurrencyFormatter;
import org.gotocy.format.DistanceFormatter;
import org.gotocy.helpers.property.FieldsStrategy;
import org.gotocy.helpers.property.FormattedField;
import org.gotocy.i18n.I18n;
import org.gotocy.service.AssetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A number of property related helper methods.
 *
 * @author ifedorenkov
 */
public class PropertyHelper {

	private static final Logger logger = LoggerFactory.getLogger(PropertyHelper.class);

	private static final DistanceFormatter DISTANCE_FORMATTER = new DistanceFormatter();
	private static final CurrencyFormatter CURRENCY_FORMATTER = new CurrencyFormatter();

	private static final Map<OfferType, String> OFFER_TYPE_TO_PRICE_KEY = new HashMap<>();

	static {
		for (OfferType type : OfferType.values()) {
			OFFER_TYPE_TO_PRICE_KEY.put(type, "org.gotocy.domain.property." +
				type.name().toLowerCase().replaceAll("_", "-") + "-price");
		}
	}

	private final AssetsService assetsService;
	private final ApplicationProperties applicationProperties;

	public PropertyHelper(ApplicationProperties applicationProperties, AssetsService assetsService) {
		this.assetsService = assetsService;
		this.applicationProperties = applicationProperties;
	}

	/**
	 * Returns the list of property {@link FormattedField} based on the given strategy.
	 */
	public List<FormattedField> formattedFields(Property property, FieldsStrategy strategy) {
		return strategy.getFormattedFields(property);
	}

	/**
	 * Returns property's representative image url or the {@link ApplicationProperties#defaultRepresentativeImage} if a
	 * property doesn't have an attached representative.
	 */
	public String representativeImageUrl(Property property) {
		if (property.getRepresentativeImage() == null) {
			return assetsService.getPublicUrl(applicationProperties.getDefaultRepresentativeImage()).orElse("");
		} else {
			return assetsService.getPublicUrl(property.getRepresentativeImage(), ImageSize.MEDIUM).orElse("");
		}
	}

	/**
	 * Prints a given price in a locale specific manner.
	 */
	public static String price(int price) {
		return "€ " + CURRENCY_FORMATTER.print(price, LocaleContextHolder.getLocale());
	}

	/**
	 * Returns a fully formatted price of the given property.
	 * TODO: unit test
	 */
	public static String price(Property property) {
		return I18n.t(OFFER_TYPE_TO_PRICE_KEY.get(property.getOfferType()), price(property.getPrice()));
	}

	/**
	 * Returns the distance in the appropriate form, which is: < 500 m - meters (m), > 500 m - kilometers (km).
	 * TODO: unit test
	 */
	public static String distance(int meters) {
		Locale locale = LocaleContextHolder.getLocale();
		String ending;
		Number number;
		if (meters < 500) {
			number = meters;
			ending = I18n.t("dictionary.meters");
		} else {
			number = meters / 1000d;
			ending = I18n.t("dictionary.kilometers");
		}
		return DISTANCE_FORMATTER.print(number, locale) + " " + ending;
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
			logger.error("Failed to determine type icon for property type {}", type);
			return "";
		}
	}

	/**
	 * Returns the appropriate path for the given {@link Property} object.
	 * For internal use by the {@link org.gotocy.helpers.Helper} only!
	 *
	 * @param property whose path is to be returned
	 * @param appendPromoPrefix determines if the /promo path segment is applicable
	 * @return path
	 */
	static String path(Property property, boolean appendPromoPrefix) {
		return (appendPromoPrefix && property.isPromo() ? "/promo" : "") + "/properties/" + property.getId();
	}

}
