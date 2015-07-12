package org.gotocy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A configuration xml file of a 360 pano.
 *
 * @author ifedorenkov
 */
@Entity
@DiscriminatorValue("pano_xml")
public class PanoXml extends Asset<String> {
}
