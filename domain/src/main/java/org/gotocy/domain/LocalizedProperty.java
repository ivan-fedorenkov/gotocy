package org.gotocy.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: implement add/remove specifications
 * TODO: relations management integration tests
 * TODO: validation / validation integration tests
 * TODO: remove the JsonManagedReference annotation
 *
 * @author ifedorenkov
 */
@Entity
@NamedEntityGraph(name = "LocalizedProperty.withProperty",
	attributeNodes = @NamedAttributeNode(value = "property", subgraph = "propertyGraph"),
	subgraphs = @NamedSubgraph(name = "propertyGraph", type = Property.class, attributeNodes = {
		@NamedAttributeNode(value = "owner"),
		@NamedAttributeNode(value = "panoXml"),
		@NamedAttributeNode(value = "representativeImage")
	})
)
public class LocalizedProperty extends BaseEntity {


	/*@Query("select lp from LocalizedProperty lp join lp.property join lp.property.imageSet.representativeImage " +
		"left join lp.property.panoXml where lp.property.id = ?1 and lp.locale = ?2")*/

	@ManyToOne(optional = false)
	private Property property;

	private String locale;

	@Lob
	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "localizedProperty", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LocalizedPropertySpecification> specifications = new ArrayList<>();

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LocalizedPropertySpecification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<LocalizedPropertySpecification> specifications) {
		this.specifications.clear();
		specifications.forEach(s -> s.setLocalizedProperty(this));
		this.specifications.addAll(specifications);
	}
}
