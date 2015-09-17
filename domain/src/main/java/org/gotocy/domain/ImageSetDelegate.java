package org.gotocy.domain;

import java.util.List;

/**
 * Convenient interface that implements image set delegation logic.
 *
 * @author ifedorenkov
 */
public interface ImageSetDelegate {

	ImageSet getImageSet();

	default List<Image> getImages() {
		return getImageSet().getImages();
	}

	default void setImages(List<Image> images) {
		getImageSet().setImages(images);
	}

}
