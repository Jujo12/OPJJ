package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The RasterView that outputs the textual representation of image to String
 */
public class StringRasterView implements RasterView {

	/**
	 * The default on character.
	 */
	private static final char DEFAULT_ON = '*';

	/**
	 * The default off character.
	 */
	private static final char DEFAULT_OFF = '.';

	/**
	 * The character that will represent turned on pixel.
	 */
	private char onChar;

	/**
	 * The character that will represent turned off pixel.
	 */
	private char offChar;

	/**
	 * The string builder used for building the output.
	 */
	private StringBuilder builder;

	/**
	 * Instantiate a new StringRasterView object with the provided on and off
	 * characters.
	 * 
	 * @param onChar
	 *            The character that will represent turned on pixel.
	 * @param offChar
	 *            The character that will represent turned off pixel.
	 */
	public StringRasterView(char onChar, char offChar) {
		this.onChar = onChar;
		this.offChar = offChar;
		
		this.builder = new StringBuilder();
	}

	/**
	 * Instantiate a new StringRasterView object with the default on and off
	 * characters.
	 */
	public StringRasterView() {
		this(DEFAULT_ON, DEFAULT_OFF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.graphics.views.RasterView#produce(hr.fer.zemris.java.
	 * graphics.raster.BWRaster)
	 */
	@Override
	public Object produce(BWRaster raster) {
		for (int y = 0; y < raster.getWidth(); y++) {
			for (int x = 0; x < raster.getHeight(); x++) {
				if (raster.isTurnedOn(x, y)) {
					addOnPixel();
				} else {
					addOffPixel();
				}
			}
			addNewLine();
		}

		return builder.toString();
	}

	/**
	 * Appends the new line (row).
	 */
	private void addNewLine() {
		builder.append(System.lineSeparator());
	}

	/**
	 * Appends the on pixel.
	 */
	private void addOnPixel() {
		builder.append(onChar);
	}

	/**
	 * Appends the off pixel.
	 */
	private void addOffPixel() {
		builder.append(offChar);
	}

}
