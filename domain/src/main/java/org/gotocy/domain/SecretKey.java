package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

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
	 * Key end of life time (millis)
	 */
	private long eol;

	/**
	 * Determines if the secret key has expired.
	 */
	public boolean isExpired() {
		return System.currentTimeMillis() > eol;
	}

}
