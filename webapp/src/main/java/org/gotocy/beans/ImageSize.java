package org.gotocy.beans;

/**
 * The available image asset sizes.
 *
 * @author ifedorenkov
 */
public enum ImageSize {

	MEDIUM("-resize 848x474^ -gravity center -crop 848x474+0+0 +repage"),
	SMALL("-resize 262x197^ -gravity center -crop 262x197+0+0 +repage"),
	THUMBNAIL("-resize 100x75^ -gravity center -crop 100x75+0+0 +repage");

	private String resizeImageMagickCommand;

	ImageSize(String command) {
		resizeImageMagickCommand = command;
	}

	public String getResizeImageMagickCommand() {
		return resizeImageMagickCommand;
	}

}
