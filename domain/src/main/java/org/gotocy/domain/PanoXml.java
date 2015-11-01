package org.gotocy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.nio.charset.Charset;

/**
 * A configuration xml file of a 360 pano.
 *
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("pano_xml")
public class PanoXml extends Asset {

	public PanoXml() {
	}

	public PanoXml(String key) {
		super(key);
	}

	@Override
	public String getContentType() {
		return "application/xml";
	}

	/**
	 * Decodes the underlying asset bytes into an xml {@link String}.
	 */
	public String decodeToXml() {
		return getBytes() == null ? "" : new String(getBytes(), Charset.forName("UTF-8"));
	}
}
