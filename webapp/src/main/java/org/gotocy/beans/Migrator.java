package org.gotocy.beans;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.LocalizedPropertySpecification;
import org.gotocy.domain.Property;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
@Component
public class Migrator {

	@Autowired
	private LocalizedPropertyRepository lpr;
	@Autowired
	private PropertyRepository pr;

	@Transactional
	public void migrate() {
		List<LocalizedProperty> lproperties = lpr.findAll();
		Map<Long, Property> properties = new HashMap<>();
		Map<String, Locale> locales = new HashMap<>();
		for (LocalizedProperty lp : lproperties) {
			List<String> features = lp.getSpecifications().stream()
				.map(LocalizedPropertySpecification::getSpecification)
				.collect(toList());

			Property p = properties.get(lp.getProperty().getId());
			if (p == null) {
				p = lp.getProperty();
				properties.put(p.getId(), p);
			}

			Locale locale = locales.get(lp.getLocale());
			if (locale == null) {
				String language = lp.getLocale();
				locales.put(language, locale = new Locale(language));
			}

			p.setFeatures(features, locale);
			p.setDescription(lp.getDescription(), locale);
		}

		pr.save(properties.values());
	}

}
