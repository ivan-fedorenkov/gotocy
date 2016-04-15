package org.gotocy.forms;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.springframework.context.MessageSourceResolvable;

import java.util.StringJoiner;

import static org.gotocy.domain.QProperty.property;

/**
 * @author ifedorenkov
 */
public class PropertiesSearchForm implements MessageSourceResolvable {

	public static final PropertiesSearchForm EMPTY_FORM = new PropertiesSearchForm();
	public static final PropertiesSearchForm SALE_FORM = new PropertiesSearchForm(PropertyStatus.SALE);
	public static final PropertiesSearchForm SHORT_TERM_FORM = new PropertiesSearchForm(PropertyStatus.SHORT_TERM);
	public static final PropertiesSearchForm LONG_TERM_FORM = new PropertiesSearchForm(PropertyStatus.LONG_TERM);

	private static final int MIN_PRICE = 100;
	private static final int MAX_PRICE = 5000000;
	private static final String MESSAGES_PREFIX = "org.gotocy.domain.PropertiesSearchForm";
	private static final String EMPTY_FORM_SUFFIX = "empty";

	private final BooleanBuilder builder = new BooleanBuilder();

	private Location location;
	private PropertyStatus propertyStatus;
	private PropertyType propertyType;
	private int priceFrom = MIN_PRICE;
	private int priceTo = MAX_PRICE;

	public PropertiesSearchForm() {}

	public PropertiesSearchForm(PropertyStatus propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public Predicate toPredicate() {
		return builder.getValue();
	}

	/**
	 * Returns true of any of the form parameters was changed.
	 */
	public boolean isChanged() {
		return location != null || propertyStatus != null || propertyType != null || priceFrom != MIN_PRICE ||
			priceTo != MAX_PRICE;
	}

	/**
	 * Returns true if price was changed.
	 */
	public boolean isPriceChanged() {
		return priceFrom != MIN_PRICE || priceTo != MAX_PRICE;
	}

	public void setLocation(Location location) {
		if (location != null) {
			this.location = location;
			builder.and(property.location.eq(location));
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setPropertyStatus(PropertyStatus propertyStatus) {
		if (propertyStatus != null) {
			this.propertyStatus = propertyStatus;
			builder.and(property.propertyStatus.eq(propertyStatus));
		}
	}

	public PropertyStatus getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyType(PropertyType propertyType) {
		if (propertyType != null) {
			this.propertyType = propertyType;
			builder.and(property.propertyType.eq(propertyType));
		}
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPriceFrom(int priceFrom) {
		if (priceFrom > MIN_PRICE) {
			this.priceFrom = priceFrom;
			builder.and(property.price.goe(priceFrom));
		}
	}

	public int getPriceFrom() {
		return priceFrom;
	}

	public void setPriceTo(int priceTo) {
		if (priceTo < MAX_PRICE) {
			this.priceTo = priceTo;
			builder.and(property.price.loe(priceTo));
		}
	}

	public int getPriceTo() {
		return priceTo;
	}

	public void setPrice(String price) {
		if (price != null && !price.isEmpty()) {
			int semiPos = price.indexOf(';');
			try {
				setPriceFrom(Integer.parseInt(price.substring(0, semiPos)));
				setPriceTo(Integer.parseInt(price.substring(semiPos + 1)));
			} catch (NumberFormatException nfe) {
				// TODO: log error
				priceFrom = MIN_PRICE;
				priceTo = MAX_PRICE;
			}
		}
	}

	// MessageSourceResolvable

	@Override
	public String[] getCodes() {
		StringJoiner sj = new StringJoiner("_");
		sj.setEmptyValue(EMPTY_FORM_SUFFIX);
		if (location != null)
			sj.add(location.name());
		if (propertyStatus != null)
			sj.add(propertyStatus.name());
		if (propertyType != null)
			sj.add(propertyType.name());
		return new String[] {MESSAGES_PREFIX + "." + sj.toString()};
	}

	@Override
	public Object[] getArguments() {
		return new Object[0];
	}

	@Override
	public String getDefaultMessage() {
		return null;
	}

}
