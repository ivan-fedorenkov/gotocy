package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.Localized;
import org.gotocy.domain.i18n.LocalizedField;
import org.gotocy.domain.i18n.LocalizedPage;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * An instance of static web page.
 *
 * @author ifedorenkov
 */
@Entity
@NamedNativeQuery(
	name = "Page.findByUrl",
	query = "select p.* from page p, page_localized_fields plf, localized_field lf " +
		"where plf.localized_fields_id = lf.id and plf.page_id = p.id " +
		"and lf.field_type = 'string' and lf.field_key = 'url' and lf.string_value = ?",
	resultClass = Page.class
)
@Getter
@Setter
public class Page extends BaseEntity implements Localized<Page, LocalizedPage> {

	private boolean visible;

	// Localized

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	@Override
	public LocalizedPage localize(Locale locale) {
		return new LocalizedPage(this, locale);
	}

}
