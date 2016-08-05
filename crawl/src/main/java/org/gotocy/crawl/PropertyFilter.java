package org.gotocy.crawl;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Property;
import org.gotocy.domain.OfferType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter that could be configured to crawl only specific properties.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertyFilter {

	/**
	 * Min price (exclusive)
	 */
	@Min(0)
	private int minPrice = 0;

	/**
	 * Max price (exclusive)
	 */
	@Min(0)
	private int maxPrice = Integer.MAX_VALUE;

	/**
	 * Allowed offer types
	 */
	@NotEmpty
	private List<OfferType> offerTypes = new ArrayList<>();

	/**
	 * Determines if the given property passes the filter.
	 *
	 * @param property to be tested
	 * @return true if passed, false if not passed
	 */
	public boolean isPassingFilter(Property property) {
		boolean isPassing = maxPrice == Integer.MAX_VALUE || property.getPrice() < maxPrice;
		isPassing &= minPrice == 0 || property.getPrice() > minPrice;
		isPassing &= offerTypes.stream().filter(ps -> ps == property.getOfferType()).findAny().isPresent();
		return isPassing;
	}

}
