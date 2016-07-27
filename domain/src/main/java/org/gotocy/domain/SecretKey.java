package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * An embeddable secret key.
 *
 * @author ifedorenkov
 */
@Embeddable
@Getter
@Setter
public class SecretKey {

	/**
	 * Key string (secret)
	 */
	private String key;

	/**
	 * Key end of life date
	 */
	private LocalDate eol;

	/**
	 * Determines if this key has expired.
	 */
	public boolean isExpired() {
		return LocalDate.now().isAfter(eol);
	}

}
