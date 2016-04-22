package org.gotocy.forms.validation;

import org.gotocy.domain.Page;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.PageForm;
import org.gotocy.repository.PageRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates {@link PageForm}. Currently validation is about verifying that there is no other page with the same
 * url as it should be unique.
 * TODO: unit test
 *
 * @author ifedorenkov
 */
public class PageFormValidator implements Validator {

	private final PageRepository pageRepository;

	public PageFormValidator(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PageForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PageForm pageForm = (PageForm) target;
		Page possibleDuplicate = pageRepository.findByUrl(pageForm.getUrl());

		if (possibleDuplicate != null) {
			Page page = null;
			if (pageForm.getId() != null)
				page = pageRepository.findOne(pageForm.getId());
			if (page == null) {
				errors.rejectValue("url", ValidationConstraints.NON_UNIQUE, null);
			} else if (possibleDuplicate.getId() != page.getId()) {
				errors.rejectValue("url", ValidationConstraints.NON_UNIQUE, null);
			}
		}
	}

}
