package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * @author ifedorenkov
 */
//@Entity
@Getter
@Setter
public class Registration /*extends BaseEntity*/ {

//	@OneToOne
	private Contact contact;

//	@OneToOne
	private Property relatedProperty;

}
