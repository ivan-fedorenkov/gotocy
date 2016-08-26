package org.gotocy.forms.user;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Image;
import org.gotocy.utils.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class ImagesEditorForm {

	private List<ImageState> imagesState;

	public ImagesEditorForm(List<Image> images) {
		imagesState = images.stream().map(ImageState::new).collect(toList());
	}

	public ImagesEditorForm() {
		imagesState = new ArrayList<>();
	}

	/**
	 * Returns removed images that are in the existing images collection.
	 */
	public Collection<Image> getRemoved(Collection<Image> existing) {
		Set<Long> toRemove = imagesState.stream()
			.filter(state -> state.removed)
			.map(state -> state.getImage().getId())
			.collect(toSet());

		return existing.stream()
			.filter(image -> toRemove.contains(image.getId()))
			.collect(toList());
	}

	@Getter
	@Setter
	public static class ImageState {

		private Image image;
		private Boolean removed;

		public ImageState() {
		}

		public ImageState(Image image) {
			this.image = image;
			this.removed = Boolean.FALSE;
		}

	}

}
