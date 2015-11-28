package org.gotocy.utils;

import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public class ImageConverterTest {

	@Test
	public void testConvert() throws Exception {
		Image original = new Image("cyprus.jpg");
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(original.getKey())) {
			original.setBytes(StreamUtils.copyToByteArray(is));
		}

		// Check all image sizes so that if someone would add a new size the test should fail
		for (ImageSize imageSize : ImageSize.values()) {
			Image expected = new Image(original.getKeyForSize(imageSize));
			Optional<Image> actual = ImageConverter.convertToSize(original, imageSize);
			Assert.assertTrue(actual.isPresent());
			Assert.assertEquals(expected.getKey(), actual.get().getKey());
			Assert.assertTrue(actual.get().getBytes().length > 0);
		}
	}

}
