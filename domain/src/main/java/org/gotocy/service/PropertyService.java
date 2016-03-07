package org.gotocy.service;

import org.gotocy.domain.Image;
import org.gotocy.domain.Property;

import java.util.List;

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

}
