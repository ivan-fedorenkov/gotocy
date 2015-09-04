package org.gotocy.dto;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;

import java.util.Locale;

import static org.gotocy.domain.QLocalizedProperty.localizedProperty;

/**
 * @author ifedorenkov
 */
public class PropertiesSearchForm {

	private static final int MIN_PRICE = 100;
	private static final int MAX_PRICE = 5000000;

	private final BooleanBuilder builder = new BooleanBuilder();

	private Location location;
	private PropertyStatus propertyStatus;
	private PropertyType propertyType;
	private Integer priceFrom = MIN_PRICE;
	private Integer priceTo = MAX_PRICE;


	public Predicate toPredicate() {
		return builder.getValue();
	}

	public PropertiesSearchForm setLocale(Locale locale) {
		builder.and(localizedProperty.locale.eq(locale.getLanguage()));
		return this;
	}

	/**
	 * Returns the url params that are constructed from the form fields.
	 */
	public String getParamsForUrl() {
		return "propertyStatus=" + (propertyStatus == null ? "" : propertyStatus.name()) +
			"&propertyType=" + (propertyType == null ? "" : propertyType.name()) +
			"&location=" + (location == null ? "" : location.name()) +
			"&price=" + (priceFrom == MIN_PRICE && priceTo == MAX_PRICE ? "" : priceFrom + ";" + priceTo);
	}

	/**
	 * Returns true of any of the form parameters was changed.
	 */
	public boolean isChanged() {
		return location != null || propertyStatus != null || propertyType != null || priceFrom != MIN_PRICE ||
			priceTo != MAX_PRICE;
	}

	public void setLocation(Location location) {
		if (location != null) {
			this.location = location;
			builder.and(localizedProperty.property.location.eq(location));
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		if (propertyStatus != null) {
			this.propertyStatus = propertyStatus;
			builder.and(localizedProperty.property.propertyStatus.eq(propertyStatus));
		}
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyType(PropertyType propertyType) {
		if (propertyType != null) {
			this.propertyType = propertyType;
			builder.and(localizedProperty.property.propertyType.eq(propertyType));
		}
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPriceFrom(Integer priceFrom) {
		if (priceFrom > MIN_PRICE) {
			this.priceFrom = priceFrom;
			builder.and(localizedProperty.property.price.goe(priceFrom));
		}
	}

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public void setPriceTo(Integer priceTo) {
		if (priceTo < MAX_PRICE) {
			this.priceTo = priceTo;
			builder.and(localizedProperty.property.price.loe(priceTo));
		}
	}

	public Integer getPriceTo() {
		return priceTo;
	}

	public void setPrice(String price) {
		if (price != null && !price.isEmpty()) {
			int semiPos = price.indexOf(';');
			int priceFrom;
			int priceTo;
			try {
				priceFrom = Integer.parseInt(price.substring(0, semiPos));
				priceTo = Integer.parseInt(price.substring(semiPos + 1));
			} catch (NumberFormatException nfe) {
				// TODO: log error
				priceFrom = MIN_PRICE;
				priceTo = MAX_PRICE;
			}
			setPriceFrom(priceFrom);
			setPriceTo(priceTo);
		}
	}

}
