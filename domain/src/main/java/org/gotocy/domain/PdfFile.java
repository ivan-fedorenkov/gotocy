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
@Getter
@Setter
public class PdfFile extends Asset<byte[]> {

	@NotNull
	private String displayName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PdfFile pdfFile = (PdfFile) o;
		return Objects.equals(displayName, pdfFile.displayName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), displayName);
	}

}
