package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.i18n.DeveloperLocalizedFieldsManager;
import org.gotocy.domain.i18n.LocalizedField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Developer extends BaseEntity {

	@Column(name = "developer_name")
	@NotEmpty
	private String name;

	@OneToMany(mappedBy = "developer")
	@JsonManagedReference
	private List<Complex> complexes = new ArrayList<>();

	@OneToMany(mappedBy = "developer")
	@JsonManagedReference
	private List<Property> properties = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedField> localizedFields = new ArrayList<>();

	// Localized fields

	private transient DeveloperLocalizedFieldsManager localizedFieldsManager;
	private transient String description;

	public void initLocalizedFields(Locale locale) {
		getLocalizedFieldsManager().setFields(locale);
	}

	public String getDescription(Locale locale) {
		return getLocalizedFieldsManager().getDescription(locale).orElse("");
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description);
		getLocalizedFieldsManager().setDescription(description, locale);
	}

	// Private stuff

	private DeveloperLocalizedFieldsManager getLocalizedFieldsManager() {
		if (localizedFieldsManager == null)
			localizedFieldsManager = new DeveloperLocalizedFieldsManager(this);
		return localizedFieldsManager;
	}

}
