package hr.fer.zemris.java.graphics.raster;

/**
 * The Interface BWRaster. Letters BW stand for Black-and-White raster. This is
 * an abstraction for all raster devices of fixed width and height for which
 * each pixel can be painted with only two colors: black (when pixel is turned
 * off) and white (when pixel is turned on).
 */
public interface BWRaster {

	/**
	 * Gets the width of the used raster.
	 *
	 * @return the width
	 */
	public int getWidth();

	/**
	 * Gets the height of the used raster.
	 *
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Clears the raster. Turns all pixels off (black).
	 */
	public void clear();

	/**
	 * Turns on the pixel at the given location. If the flip mode is on, flips
	 * the state of the given pixel instead.
	 *
	 * @param x
	 *            the x coordinate of the target pixel
	 * @param y
	 *            the y coordinate of the target pixel
	 */
	public void turnOn(int x, int y);

	/**
	 * Turns off the pixel at the given location.
	 *
	 * @param x
	 *            the x coordinate of the target pixel
	 * @param y
	 *            the y coordinate of the target pixel
	 */
	public void turnOff(int x, int y);

	/**
	 * Enables flip mode. {@link BWRaster#turnOn(int, int)}
	 */
	public void enableFlipMode();

	/**
	 * Disables flip mode. {@link BWRaster#turnOn(int, int)}
	 */
	public void disableFlipMode();

	/**
	 * Checks if the pixel is turned on.
	 *
	 * @param x
	 *            the x coordinate of the target pixel
	 * @param y
	 *            the y coordinate of the target pixel
	 * @return true if the pixel is turned on (white), false otherwise (black)
	 */
	public boolean isTurnedOn(int x, int y);
}
