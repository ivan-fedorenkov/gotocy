package org.gotocy.beans;

import org.gotocy.domain.Asset;

import java.net.URL;

/**
 * An adapter interface to deal with various backend asset providers.
 *
 * @author ifedorenkov
 */
public interface AssetsProvider {

	/**
	 * Returns an asset url.
	 */
	URL getUrl(Asset asset);

}
