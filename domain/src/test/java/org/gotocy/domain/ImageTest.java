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
		String expectedThumbnailKey = "/path/to/image/THUMBNAIL_picture.jpg";
		String expectedSmallKey = "/path/to/image/SMALL_picture.jpg";
		String expectedMediumKey = "/path/to/image/MEDIUM_picture.jpg";

		Image image = new Image();
		image.setKey(initialKey);

		Assert.assertEquals(expectedThumbnailKey, image.getKeyForSize(ImageSize.THUMBNAIL));
		Assert.assertEquals(expectedSmallKey, image.getKeyForSize(ImageSize.SMALL));
		Assert.assertEquals(expectedMediumKey, image.getKeyForSize(ImageSize.MEDIUM));
		Assert.assertEquals(initialKey, image.getKey());
	}

}
