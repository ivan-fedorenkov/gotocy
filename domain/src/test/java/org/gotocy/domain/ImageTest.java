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
		String expectedMediumKey = "/resized_images/path/to/image/MEDIUM/picture.jpg";
		String expectedBigKey = "/resized_images/path/to/image/BIG/picture.jpg";

		Image image = new Image();
		image.setKey(initialKey);

		Assert.assertEquals(initialKey, image.getKey());
		Assert.assertEquals(expectedMediumKey, image.getSized(ImageSize.MEDIUM));
		Assert.assertEquals(expectedBigKey, image.getSized(ImageSize.BIG));

	}

}
