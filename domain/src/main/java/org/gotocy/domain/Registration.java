package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Registration extends BaseEntity {

	private String name;

	private String email;

	@Enumerated(EnumType.STRING)
	private BusinessForm businessForm;

	private int propertiesCount;

	@OneToOne
	private Property relatedProperty;

}
