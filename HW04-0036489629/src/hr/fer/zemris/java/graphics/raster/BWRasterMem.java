package hr.fer.zemris.java.graphics.raster;

/**
 * The class BWRasterMem is an implementation of BWRaster interface which keeps
 * all of its data in computer memory. On creation of new objects of this class,
 * all the pixels will be initially turned off.
 * 
 * @author Juraj Juričić
 *
 */
public class BWRasterMem implements BWRaster {
	/**
	 * The raster data 2D array. True values are "turned on".
	 */
	private boolean[][] rasterData;

	/**
	 * The width of the raster.
	 */
	private int width;

	/**
	 * The height of the raster.
	 */
	private int height;

	/**
	 * true if flip mode is on.
	 */
	private boolean flipMode = false;

	/**
	 * Instantiates a new BWRasterMem object. Constructor accepts raster width
	 * and height, both of which must me at least 1.
	 *
	 * @param width
	 *            the width of the raster. Must be at least 1.
	 * @param height
	 *            the height of the raster. Must be at least 1.
	 */
	public BWRasterMem(int width, int height) {
		super();
		if (width < 1) {
			throw new IllegalArgumentException("Width must be greater than 0");
		}
		if (height < 1) {
			throw new IllegalArgumentException("Height must be greater than 0");
		}

		this.width = width;
		this.height = height;

		rasterData = new boolean[width][height];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for (int x = 0; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				turnOff(x, y);
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0) {
			throw new IllegalArgumentException("Invalid pixel position: (" + x + "," + y + ").");
		}

		// check for flip mode
		if (flipMode) {
			rasterData[x][y] = !rasterData[x][y];
		} else {
			rasterData[x][y] = true;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0) {
			throw new IllegalArgumentException("Invalid pixel position: (" + x + "," + y + ").");
		}

		rasterData[x][y] = false;
	}

	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0) {
			throw new IllegalArgumentException("Invalid pixel position: (" + x + "," + y + ").");
		}

		return rasterData[x][y];
	}

}
