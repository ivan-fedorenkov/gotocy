package org.gotocy.service;

import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ifedorenkov
 */
@Component
public class PropertyServiceImpl implements PropertyService {

	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

	private final AssetsManager assetsManager;
	private final PropertyRepository propertyRepository;

	@Autowired
	public PropertyServiceImpl(AssetsManager assetsManager, PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
		this.assetsManager = assetsManager;
	}

	@Transactional
	@Override
	public Property create(Property property) {

		// We need property id to create full image's paths, so first of all create a property without assets,
		// then create assets and update property.

		List<Image> images = new ArrayList<>(property.getImages());
		Image representativeImage = property.getRepresentativeImage();
		property.setImages(Collections.emptyList());
		property.setRepresentativeImage(null);

		property = propertyRepository.save(property);

		if (!images.isEmpty()) {
			List<Image> createdImages = new ArrayList<>(images.size());
			try {
				for (Image image : images) {
					image.setKey("property/" + property.getId() + "/" + image.getKey());
					assetsManager.saveAsset(image);
					createdImages.add(image);
				}

				property.setImages(createdImages);
				property.setRepresentativeImage(representativeImage);
				property = propertyRepository.save(property);
			} catch (NullPointerException | IOException | DataAccessException e) {
				// Log error
				logger.error("Failed to attach property's assets.", e);

				// Clean up created objects
				try {
					propertyRepository.delete(property);
					for (Image image : createdImages)
						assetsManager.deleteAsset(image);
				} catch (DataAccessException | IOException ee) {
					logger.error("Failed to clean up resources.", ee);
				}

				// Rethrow so that the user would be notified appropriately (this is kind of critical error)
				throw new ServiceMethodExecutionException(e);
			}
		}

		return property;
	}

}
