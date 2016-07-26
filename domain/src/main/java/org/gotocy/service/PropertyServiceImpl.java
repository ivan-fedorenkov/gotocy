package org.gotocy.service;

import ch.qos.logback.core.util.Duration;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.SecretKey;
import org.gotocy.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gotocy.repository.PropertyPredicates.*;

/**
 * @author ifedorenkov
 */
@Component
@Transactional
public class PropertyServiceImpl implements PropertyService {

	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

	private final AssetsManager assetsManager;
	private final PropertyRepository propertyRepository;
	private final StringKeyGenerator keyGenerator;

	@Autowired
	public PropertyServiceImpl(AssetsManager assetsManager, PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
		this.assetsManager = assetsManager;
		this.keyGenerator = KeyGenerators.string();
	}

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

	@Override
	public SecretKey generateRegistrationSecret() {
		SecretKey key = new SecretKey();
		key.setKey(keyGenerator.generateKey());
		// Key should be valid for one week
		key.setEol(ZonedDateTime.now().plusWeeks(1).toInstant().toEpochMilli());
		return key;
	}

	@Transactional(readOnly = true)
	@Override
	public Property findOne(Long id) {
		return propertyRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Property> findRecent(PropertyStatus propertyStatus, Pageable pageable) {
		return propertyRepository.findAll(publiclyVisible().and(inStatus(propertyStatus)), pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<Property> getFeatured() {
		return propertyRepository.findAll(publiclyVisible().and(featured()));
	}
}
