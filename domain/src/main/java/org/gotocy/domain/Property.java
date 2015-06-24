package org.gotocy.domain;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ifedorenkov
 */
@Entity
public class Property extends BaseEntity {

	private Integer price;

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
