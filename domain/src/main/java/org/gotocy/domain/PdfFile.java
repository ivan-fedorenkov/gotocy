package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

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

}
