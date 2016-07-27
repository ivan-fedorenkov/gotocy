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
	 * Verifies that the given key matches this key and this key hasn't expired.
	 */
	public boolean verifyKey(String key) {
		return Objects.equals(this.key, key) && LocalDate.now().isBefore(eol);
	}

}
