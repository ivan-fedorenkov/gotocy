package org.gotocy.service;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.domain.SecretKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;

/**
 * @author ifedorenkov
 */
public interface PropertyService {

	/**
	 * Creates property object.
	 *
	 * @param property to be created
	 * @return created instance
	 */
	Property create(Property property);

	/**
	 * Creates property object and attaches images. This method is just a 'transaction script'
	 * of calling {@link #create(Property)} and then calling {@link #attachImages(Property, Collection)}.
	 *
	 * @param property to be created
	 * @param images to be attached
	 * @return created property with attached images
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	Property createWithAttachments(Property property, Collection<Image> images)
		throws ServiceMethodExecutionException;

	/**
	 * Updates the given property.
	 *
	 * @param property to be updated
	 * @return updated property
	 */
	Property update(Property property);

	/**
	 * Attaches provided images to the given property.
	 *
	 * @param property whose images should be attached
	 * @param images to be attached
	 * @return instance with attached images
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	Property attachImages(Property property, Collection<Image> images)
		throws ServiceMethodExecutionException;

	/**
	 * Detaches provided images from the given property.
	 *
	 * @param property whose images should be detached
	 * @param images to be detached
	 * @return instance with detached images
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	Property detachImages(Property property, Collection<Image> images)
		throws ServiceMethodExecutionException;

	/**
	 * Generates secret key that could be used for registration purposes.
	 */
	SecretKey generateRegistrationSecret();

	Property findOne(Long id);

	Iterable<Property> find(Predicate predicate, Sort sort);

	Page<Property> findPubliclyVisible(Predicate predicate, Pageable pageable);

	Iterable<Property> getFeatured();

}
