package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The RasterView that outputs the textual representation of image to standard
 * output. Returns null as a result.
 */
public class SimpleRasterView implements RasterView {
	/**
	 * The default on character.
	 */
	private static final char DEFAULT_ON = '*';

	/**
	 * The default off character.
	 */
	private static final char DEFAULT_OFF = '.';
	
	/**
	 * The StringRasterView used for generating the string output.
	 */
	StringRasterView stringRasterView;

	/**
	 * Instantiate a new SimpleRasterView object with the provided on and off
	 * characters.
	 * 
	 * @param onChar
	 *            The character that will represent turned on pixel.
	 * @param offChar
	 *            The character that will represent turned off pixel.
	 */
	public SimpleRasterView(char onChar, char offChar) {
		stringRasterView = new StringRasterView(onChar, offChar);
	}

	/**
	 * Instantiate a new SimpleRasterView object with the default on and off
	 * characters.
	 */
	public SimpleRasterView() {
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
		String result = (String) stringRasterView.produce(raster);
		System.out.print(result);
		
		return null;
	}

}
