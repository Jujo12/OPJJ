package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Represents an abstract rectangle; does not have a defined contract for the
 * setWidth and setHeight methods.
 * 
 * @author Juraj Juričić
 *
 */
public abstract class AbstractRectangle extends GeometricShape {

	/** The x coordinate of the top left corner of the rectangle. */
	protected int x;

	/** The y coordinate of the top left corner of the rectangle. */
	protected int y;

	/** The width of the rectangle. */
	protected int width;

	/** The height of the rectangle. */
	protected int height;

	/**
	 * Instantiates a new abstract rectangle with the given coordinates and
	 * dimensions.
	 *
	 * @param x
	 *            the x coordinate of the top left corner of the rectangle.
	 * @param y
	 *            the y coordinate of the top left corner of the rectangle.
	 * @param width
	 *            the width of the rectangle.
	 * @param height
	 *            the height of the rectangle.
	 */
	protected AbstractRectangle(int x, int y, int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Rectangle dimensions cannot be less than 1.");
		}

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the x coordinate of the rectangle.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the rectangle.
	 *
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the rectangle.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the rectangle.
	 *
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the width of the rectangle.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the rectangle. Contract ambiguous.
	 *
	 * @param width
	 *            the width to set
	 */
	public abstract void setWidth(int width);

	/**
	 * Gets the height of the rectangle.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the rectangle. Contract ambiguous.
	 *
	 * @param height
	 *            the height to set
	 */
	public abstract void setHeight(int height);

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#containsPoint(int,
	 * int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x)
			return false;
		if (y < this.y)
			return false;
		if (x >= this.x + this.width)
			return false;
		if (y >= this.y + this.height)
			return false;

		return true;
	}
	
	@Override
	public void draw(BWRaster r) {
		int topLeftX = Math.max(0, this.x);
		int topLeftY = Math.max(0, this.y);
		int bottomRightX = this.width + this.x;
		int bottomRightY = this.height + this.y;
		
		for(int x = topLeftX; x < bottomRightX; x++) {
			for(int y = topLeftY; y < bottomRightY; y++) {
				r.turnOn(x, y);
			}
		}
	};

}
