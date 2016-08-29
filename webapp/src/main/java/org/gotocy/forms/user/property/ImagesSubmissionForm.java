package org.gotocy.forms.user.property;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Form to submit new property images.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class ImagesSubmissionForm {

	/**
	 * Related property identifier
	 */
	private Long id;
	private List<MultipartFile> images = new ArrayList<>();

	/**
	 * Returns a list of {@link Image} created from the attached {@link #images}.
	 */
	public List<Image> mapFilesToImages() throws IOException {
		if (images.isEmpty())
			return Collections.emptyList();

		List<Image> result = new ArrayList<>(images.size());
		for (MultipartFile file : images) {
			Image image = new Image(file.getOriginalFilename());
			image.setBytes(file.getBytes());
			result.add(image);
		}
		return result;
	}

	public void setImages(List<MultipartFile> images) {
		images.removeIf(MultipartFile::isEmpty);
		this.images.addAll(images);
	}

}
