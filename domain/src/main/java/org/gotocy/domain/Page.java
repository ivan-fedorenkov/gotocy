package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * An instance of static web page.
 *
 * @author ifedorenkov
 */
@Entity
@Getter
@Setter
public class Page extends BaseEntity {

	private String url;

	private String title;

	@Lob
	private String html;

	private boolean visible;

}
