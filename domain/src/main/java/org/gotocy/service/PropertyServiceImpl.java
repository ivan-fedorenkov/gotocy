package org.gotocy.service;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.domain.SecretKey;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.utils.StringKeyGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

import static org.gotocy.repository.PropertyPredicates.featured;
import static org.gotocy.repository.PropertyPredicates.publiclyVisible;

/**
 * @author ifedorenkov
 */
@Component
public class PropertyServiceImpl implements PropertyService {

	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

	private static final int REGISTRATION_KEY_LENGTH = 28;

	private final AssetsService assetsService;
	private final PropertyRepository propertyRepository;
	private final StringKeyGenerator keyGenerator;

	@Autowired
	public PropertyServiceImpl(AssetsService assetsService, PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
		this.assetsService = assetsService;
		keyGenerator = new StringKeyGeneratorImpl(REGISTRATION_KEY_LENGTH);
	}

	@Override
	@Transactional
	public Property create(Property property) {
		return propertyRepository.save(property);
	}

	@Override
	@Transactional
	public Property createWithAttachments(Property property, Collection<Image> images) {
		property = create(property);
		property = attachImages(property, images);
		return property;
	}

	@Override
	@Transactional
	public Property update(Property property) {
		return propertyRepository.save(property);
	}

	@Override
	@Transactional
	public Property attachImages(Property property, Collection<Image> images)
		throws ServiceMethodExecutionException
	{
		Collection<Image> createdImages = assetsService.createAssets("property/" + property.getId(), images, true);
		property.getImages().addAll(createdImages);
		// If the property is missing representative image then use the first created image as representative
		if (property.getRepresentativeImage() == null && !property.getImages().isEmpty())
			property.setRepresentativeImage(property.getImage(0));
		return propertyRepository.save(property);
	}

	@Override
	public SecretKey generateRegistrationSecret() {
		SecretKey key = new SecretKey();
		key.setKey(keyGenerator.generateKey());
		// Key should be valid for one week
		key.setEol(LocalDate.now().plusWeeks(1));
		return key;
	}

	@Override
	@Transactional(readOnly = true)
	public Property findOne(Long id) {
		return propertyRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Property> find(Predicate predicate, Sort sort) {
		return propertyRepository.findAll(predicate, sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Property> findPubliclyVisible(Predicate predicate, Pageable pageable) {
		return propertyRepository.findAll(publiclyVisible().and(predicate), pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Property> getFeatured() {
		return propertyRepository.findAll(publiclyVisible().and(featured()));
	}

}
