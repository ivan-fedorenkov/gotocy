package org.gotocy.forms;

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

	private final BooleanBuilder builder = new BooleanBuilder();

	private Location location;
	private PropertyStatus propertyStatus;
	private PropertyType propertyType;
	private Integer priceFrom = 0;
	private Integer priceTo = Integer.MAX_VALUE;

	public Predicate toPredicate() {
		return builder.getValue();
	}

	public PropertiesSearchForm setLocale(Locale locale) {
		builder.and(localizedProperty.locale.eq(locale.getLanguage()));
		return this;
	}

	public void setLocation(Location location) {
		this.location = location;
		builder.and(localizedProperty.property.location.eq(location));
	}

	public Location getLocation() {
		return location;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
		builder.and(localizedProperty.property.propertyStatus.eq(propertyStatus));
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
		builder.and(localizedProperty.property.propertyType.eq(propertyType));
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPriceFrom(Integer priceFrom) {
		if (priceFrom > 0) {
			this.priceFrom = priceFrom;
			builder.and(localizedProperty.property.price.goe(priceFrom));
		}
	}

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public void setPriceTo(Integer priceTo) {
		if (priceTo < Integer.MAX_VALUE) {
			this.priceTo = priceTo;
			builder.and(localizedProperty.property.price.loe(priceTo));
		}
	}

	public Integer getPriceTo() {
		return priceTo;
	}
}
