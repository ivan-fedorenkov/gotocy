package org.gotocy.service;

import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.PropertyFactory;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ifedorenkov
 */
public class PropertyServiceTest {

	private PropertyService propertyService;

	@Before
	public void init() {
		AssetsService assetsService = mock(AssetsService.class);
		when(assetsService.createAssets(anyString(), anyCollection(), anyBoolean())).then(returnsSecondArg());

		PropertyRepository propertyRepository = mock(PropertyRepository.class);
		when(propertyRepository.save(any(Property.class))).then(returnsFirstArg());

		propertyService = new PropertyServiceImpl(assetsService, propertyRepository);
	}

	@Test
	public void testCreate() {
		Property property = PropertyFactory.INSTANCE.get(p -> p.setCreationDate(null));
		Assert.assertNull(property.getCreationDate());
		propertyService.create(property);
		Assert.assertEquals(LocalDate.now(), property.getCreationDate());
	}

	/**
	 * When attaching images to a property that has no representative image,
	 * the first image should be set as the representative.
	 */
	@Test
	public void testAttachingImagesWithRepresentative() {
		Property property = PropertyFactory.INSTANCE.get();
		Assert.assertNull(property.getRepresentativeImage());

		Image firstImage = new Image("first image");
		Image secondImage = new Image("second image");

		property = propertyService.attachImages(property, Arrays.asList(firstImage, secondImage));

		Assert.assertEquals(firstImage, property.getRepresentativeImage());
		Assert.assertThat(property.getImages(), Matchers.contains(firstImage, secondImage));
	}

	/**
	 * When detaching images from a property then the representative image should
	 * be updated accordingly:
	 * - if images to be detached do not contain representative then do nothing
	 * - if images to be detached contain representative then update representative to the first remaining image
	 * - if images to be detached contain all the property images then set representative as {@code null}
	 */
	@Test
	public void testDetachingImagesWithRepresentative() {
		Property property = PropertyFactory.INSTANCE.get();

		Image representative = new Image("representative");
		Image firstImage = new Image("first image");
		Image secondImage = new Image("second image");

		property.setImages(Arrays.asList(representative, firstImage, secondImage));
		property.setRepresentativeImage(representative);

		propertyService.detachImages(property, Collections.singleton(representative));
		Assert.assertEquals(firstImage, property.getRepresentativeImage());
		Assert.assertThat(property.getImages(), Matchers.contains(firstImage, secondImage));

		propertyService.detachImages(property, Arrays.asList(firstImage, secondImage));
		Assert.assertNull(property.getRepresentativeImage());
		Assert.assertThat(property.getImages(), Matchers.hasSize(0));
	}

}
