package org.gotocy.domain;

/**
 * The available image asset sizes.
 *
 * @author ifedorenkov
 */
public enum ImageSize {

	// -quality 70 -resize 1920x1080^
	BIG,

	// -quality 50 -resize 524x394^ -crop 524x394+0+0 : 1.32 - looks like the ideal proportion
	MEDIUM;

}
