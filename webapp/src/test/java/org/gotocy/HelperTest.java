package org.gotocy;

import org.gotocy.helpers.Helper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class HelperTest {

	@Test
	public void price() {
		Assert.assertEquals("$ 1", Helper.price(1));
		Assert.assertEquals("$ 100", Helper.price(100));
		Assert.assertEquals("$ 100,000", Helper.price(100000));
		Assert.assertEquals("$ 100,000,000", Helper.price(100000000));
	}

}
