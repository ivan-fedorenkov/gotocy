package org.gotocy.beans;

import org.gotocy.domain.Asset;

/**
 * An adapter interface to deal with various backend asset providers.
 *
 * @author ifedorenkov
 */
public interface AssetsProvider {

	/**
	 * Returns an asset url.
	 */
	String getUrl(Asset asset);

	/**
	 * Returns an image {@link Asset} url for specified {@link ImageSize}.
	 */
	String getImageUrl(Asset asset, ImageSize size);

}
