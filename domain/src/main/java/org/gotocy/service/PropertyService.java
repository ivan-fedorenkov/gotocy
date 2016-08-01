package org.gotocy.service;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.Property;
import org.gotocy.domain.SecretKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author ifedorenkov
 */
public interface PropertyService {

	/**
	 * Creates property object. Saves the given property's images using the configured {@link AssetsManager}.
	 * Note: pass property's images via field
	 * Note: the first image will be used as representative.
	 *
	 * @param property to be created
	 * @return create instance
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	Property create(Property property) throws ServiceMethodExecutionException;

	/**
	 * Generates secret key that could be used for registration purposes.
	 */
	SecretKey generateRegistrationSecret();

	Property findOne(Long id);

	Iterable<Property> find(Predicate predicate, Sort sort);

	Page<Property> findPubliclyVisible(Predicate predicate, Pageable pageable);

	Iterable<Property> getFeatured();

}
