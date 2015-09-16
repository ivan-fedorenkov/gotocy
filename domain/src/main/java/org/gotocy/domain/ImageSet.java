package org.gotocy.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An embeddable class that represents associations between an object and its images.
 *
 * @author ifedorenkov
 */
@Embeddable
public class ImageSet {

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images.clear();
		this.images.addAll(images);
	}

	public Image getImage(int index) {
		// Ensure that the index is in the images list bounds
		if (index >= images.size())
			index = index % images.size();
		return images.get(index);
	}
}
