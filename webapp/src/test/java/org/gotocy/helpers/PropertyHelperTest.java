package org.gotocy.helpers;

import org.gotocy.domain.PropertyType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class PropertyHelperTest {

	@Test
	public void getPropertyTypeIcon() {
		Assert.assertEquals("apartment", PropertyHelper.typeIcon(PropertyType.APARTMENT));
		Assert.assertEquals("single-family", PropertyHelper.typeIcon(PropertyType.HOUSE));
		Assert.assertEquals("land", PropertyHelper.typeIcon(PropertyType.LAND));
	}

}
