package org.gotocy.helpers.property;

import org.gotocy.domain.Property;

/**
 * Base interface for various property fields' providers.
 *
 * @author ifedorenkov
 */
interface FieldsProvider {

	/**
	 * Returns the appropriate set of property fields to be formatted.
	 */
	FieldFormat[] getFields(Property property);

}
