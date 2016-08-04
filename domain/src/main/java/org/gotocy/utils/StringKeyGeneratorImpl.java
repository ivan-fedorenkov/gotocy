package org.gotocy.utils;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

/**
 * Key generator that returns a string of alphanumeric characters.
 *
 * @author ifedorenkov
 */
public class StringKeyGeneratorImpl implements StringKeyGenerator {

	private final BytesKeyGenerator bytesKeyGenerator;

	public StringKeyGeneratorImpl(int length) {
		bytesKeyGenerator = KeyGenerators.secureRandom(length);
	}

	@Override
	public String generateKey() {
		return new String(Hex.encode(bytesKeyGenerator.generateKey()));
	}
}
