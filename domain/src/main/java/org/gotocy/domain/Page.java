package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.LocalizedField;
import org.gotocy.domain.i18n.PageLocalizedFieldsManager;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
public class Page extends BaseEntity {

	private boolean visible;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	// Localized fields

	private transient PageLocalizedFieldsManager localizedFieldsManager;
	private transient String url;
	private transient String title;
	private transient String html;

	public void initLocalizedFields(Locale locale) {
		getLocalizedFieldsManager().setFields(locale);
	}

	public void initLocalizedFieldsFromTransients(Locale locale) {
		setUrl(getUrl(), locale);
		setTitle(getTitle(), locale);
		setHtml(getHtml(), locale);
	}

	public void setUrl(String url, Locale locale) {
		setUrl(url);
		getLocalizedFieldsManager().setUrl(url, locale);
	}

	public void setTitle(String title, Locale locale) {
		setTitle(title);
		getLocalizedFieldsManager().setTitle(title, locale);
	}

	public void setHtml(String html, Locale locale) {
		setHtml(html);
		getLocalizedFieldsManager().setHtml(html, locale);
	}

	// Private stuff

	private PageLocalizedFieldsManager getLocalizedFieldsManager() {
		if (localizedFieldsManager == null)
			localizedFieldsManager = new PageLocalizedFieldsManager(this);
		return localizedFieldsManager;
	}

}
