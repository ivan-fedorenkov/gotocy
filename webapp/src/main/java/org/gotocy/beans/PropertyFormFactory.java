package org.gotocy.beans;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A factory for {@link org.gotocy.forms.PropertyForm} objects.
 *
 * @author ifedorenkov
 */
@Component
public class PropertyFormFactory {

	private final ApplicationProperties applicationProperties;

	@Autowired
	public PropertyFormFactory(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	/**
	 * Creates form from the given english and russian localized property objects. The objects must be fully populated,
	 * all the required fields must be set.
	 */
	public PropertyForm create(LocalizedProperty enLP, LocalizedProperty ruLP) {
		return new PropertyForm(enLP, ruLP);
	}

	/**
	 * Creates form using the default (configured by application properties) values.
	 */
	public PropertyForm create() {
		Property p = new Property();

		ImageSet imageSet = new ImageSet();
		p.setImageSet(imageSet);

		p.setLocation(Location.FAMAGUSTA);
		p.setPropertyType(PropertyType.APARTMENT);
		p.setPropertyStatus(PropertyStatus.LONG_TERM);
		p.setOfferStatus(OfferStatus.ACTIVE);

		p.setLatitude(applicationProperties.getDefaultLatitude());
		p.setLongitude(applicationProperties.getDefaultLongitude());

		LocalizedProperty enLP = new LocalizedProperty();
		enLP.setProperty(p);
		LocalizedProperty ruLP = new LocalizedProperty();
		ruLP.setProperty(p);

		return create(enLP, ruLP);
	}

}
