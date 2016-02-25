package org.gotocy.crawl;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.Set;

/**
 * Filter that could be configured to crawl only specific properties.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class PropertyFilter {

	/**
	 * Max price (exclusive)
	 */
	@Min(0)
	private int maxPrice = Integer.MAX_VALUE;

	/**
	 * Allowed property statuses
	 */
	@NotNull
	private Set<PropertyStatus> propertyStatuses = EnumSet.allOf(PropertyStatus.class);

	/**
	 * Determines if the given property passes the filter.
	 *
	 * @param property to be tested
	 * @return true if passed, false if not passed
	 */
	public boolean isPassingFilter(Property property) {
		return property.getPrice() < maxPrice && propertyStatuses.contains(property.getPropertyStatus());
	}

}
