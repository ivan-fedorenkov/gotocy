package org.gotocy.utils;

import org.gotocy.domain.Image;
import org.gotocy.domain.ImageSize;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IMOps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Optional;

/**
 * Converter of images. Requires write access to the TEMP directory.
 *
 * @author ifedorenkov
 */
public class ImageConverter {

	private static final Logger logger = LoggerFactory.getLogger(ImageConverter.class);

	private static final String TEMP_FILES_PREFIX = "IMAGE_CONVERTER";
	private static final String TEMP_FILES_SUFFIX = ".tmp";

	private static final EnumMap<ImageSize, IMOps> RESIZE_OPS = new EnumMap<>(ImageSize.class);

	static {
		IMOperation resizeBigOp = new IMOperation();
		resizeBigOp.addImage();
		resizeBigOp.quality(70d).resize(1920, 1080, "^");
		resizeBigOp.addImage();
		RESIZE_OPS.put(ImageSize.BIG, resizeBigOp);

		IMOperation resizeMediumOp = new IMOperation();
		resizeMediumOp.addImage();
		resizeMediumOp.quality(50d).resize(524, 394, "^").crop(524, 394, 0, 0);
		resizeMediumOp.addImage();
		RESIZE_OPS.put(ImageSize.MEDIUM, resizeMediumOp);
	}

	private ImageConverter() {
	}

	/**
	 * Converts the given source {@link Image} to the specified {@link ImageSize} and creates
	 * a new {@link Image} instance. Underlying bytes of the new image represents the converted image and the
	 * key is set in accordance with {@link Image#getKeyForSize(ImageSize)} method.
	 *
	 * @param sourceImage to be converted
	 * @param targetSize of the resulting image
	 * @return the converted image or {@link Optional#EMPTY} if something went wrong.
	 */
	public static Optional<Image> convertToSize(Image sourceImage, ImageSize targetSize) {
		logger.info("Converting {} to size {}", sourceImage, targetSize);
		Optional<Image> converted = Optional.empty();
		try {
			Path src = Files.createTempFile(TEMP_FILES_PREFIX, TEMP_FILES_SUFFIX);
			Files.write(src, sourceImage.getBytes());
			Path dst = Files.createTempFile(TEMP_FILES_PREFIX, TEMP_FILES_SUFFIX);

			ConvertCmd cmd = new ConvertCmd();
			cmd.run(RESIZE_OPS.get(targetSize), src.toString(), dst.toString());

			Image convertedImage = new Image(sourceImage.getKeyForSize(targetSize));
			convertedImage.setBytes(Files.readAllBytes(dst));
			converted = Optional.of(convertedImage);
		} catch (InterruptedException | IM4JavaException | IOException e) {
			logger.error("Failed to convert {} to size {}", sourceImage, targetSize, e);
		}
		return converted;
	}

}
