package org.gotocy.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class ImageTest {

	@Test
	public void getKeyForSize() {
		String initialKey = "/path/to/image/picture.jpg";
		String expectedMediumKey = "/path/to/image/MEDIUM/picture.jpg";
		String expectedBigKey = "/path/to/image/BIG/picture.jpg";

		Image image = new Image();
		image.setKey(initialKey);

		Assert.assertEquals(initialKey, image.getKey());
		Assert.assertEquals(expectedMediumKey, image.getKeyForSize(ImageSize.MEDIUM));
		Assert.assertEquals(expectedBigKey, image.getKeyForSize(ImageSize.BIG));

	}

}
