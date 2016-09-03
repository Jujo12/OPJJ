package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Classes that implement this interface are responsible for visualization of
 * created images.
 */
public interface RasterView {

	/**
	 * Displays raster on the screen with predefined characters.
	 * 
	 * @param raster
	 *            Raster with the data you want to print
	 * @return the produced Object based on raster input
	 */
	Object produce(BWRaster raster);
}
