package org.gotocy.crawl;

import lombok.Getter;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Filter that could be configured to crawl only specific properties.
 *
 * @author ifedorenkov
 */
@Getter
public class PropertyFilter {

	/**
	 * Max price (exclusive)
	 */
	@Min(0)
	private int maxPrice = Integer.MAX_VALUE;

	/**
	 * Allowed property statuses
	 */
	@NotEmpty
	@NestedConfigurationProperty
	private List<PropertyStatus> propertyStatuses;

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setPropertyStatuses(List<PropertyStatus> propertyStatuses) {
		this.propertyStatuses = propertyStatuses;
	}

	/**
	 * Determines if the given property passes the filter.
	 *
	 * @param property to be tested
	 * @return true if passed, false if not passed
	 */
	public boolean isPassingFilter(Property property) {

		if (maxPrice != Integer.MAX_VALUE && property.getPrice() >= maxPrice)
			return false;

		for (PropertyStatus propertyStatus : propertyStatuses) {
			if (propertyStatus == property.getPropertyStatus())
				return true;
		}
		return false;
	}

}
