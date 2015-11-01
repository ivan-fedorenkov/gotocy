package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue(value = "pdf_file")
public class PdfFile extends Asset {

	public PdfFile() {
	}

	public PdfFile(String key) {
		super(key);
	}

	@Override
	public String getContentType() {
		return "application/pdf";
	}

	/**
	 * Special getter method that returns user friendly file name.
	 * Unit test: PdfFileTest#getDisplayName
	 */
	public String getDisplayName() {
		return getKey() == null || getKey().isEmpty() ? "" : getKey().substring(getKey().lastIndexOf('/') + 1);
	}

}
