package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Image;
import org.gotocy.utils.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

	public void mergeWithCollection(Collection<Image> images) {
		Set<Long> toRemove = imagesState.stream()
			.filter(state -> state.removed == Boolean.TRUE)
			.map(state -> state.getImage().getId())
			.collect(Collectors.toSet());
		images.removeIf(image -> toRemove.contains(image.getId()));
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
