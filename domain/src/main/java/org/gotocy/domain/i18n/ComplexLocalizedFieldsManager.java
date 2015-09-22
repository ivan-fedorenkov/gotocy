package org.gotocy.domain.i18n;

import org.gotocy.domain.Complex;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
public class ComplexLocalizedFieldsManager extends LocalizedFieldsManager {

	private static final String DESCRIPTION_KEY = "description";
	private static final String SPECIFICATION_KEY = "specification";

	private final Complex complex;

	public ComplexLocalizedFieldsManager(Complex complex) {
		this.complex = complex;
	}

	@Override
	public void setFields(Locale locale) {
		complex.setDescription(getFieldValue(DESCRIPTION_KEY, locale).orElse(""));
		complex.setSpecifications(getFieldValues(SPECIFICATION_KEY, locale));
	}

	@Override
	public List<LocalizedField> getFields() {
		return complex.getLocalizedFields();
	}

	public void setDescription(String description, Locale locale) {
		updateTextField(DESCRIPTION_KEY, description, locale);
	}

	public Optional<String> getDescription(Locale locale) {
		return getFieldValue(DESCRIPTION_KEY, locale);
	}

	public List<String> getSpecifications(Locale locale) {
		return getFieldValues(SPECIFICATION_KEY, locale);
	}

	public void setSpecifications(List<String> specifications, Locale locale) {
		updateStringFields(SPECIFICATION_KEY, specifications, locale);
	}

}
