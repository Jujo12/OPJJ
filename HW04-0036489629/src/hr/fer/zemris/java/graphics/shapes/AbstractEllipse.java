package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Represents an abstract ellipse.
 */
public abstract class AbstractEllipse extends GeometricShape {

	/** The x. */
	protected int x;

	/** The y. */
	protected int y;

	/** The a. */
	protected int a;

	/** The b. */
	protected int b;

	/**
	 * Instantiates a new abstract ellipse.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 */
	protected AbstractEllipse(int x, int y, int a, int b) {
		if (a < 1 || b < 1) {
			throw new IllegalArgumentException("Radius must be greater than 0.");
		}
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#containsPoint(int,
	 * int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the x coordinate of the center of the ellipse.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the center of the ellipse.
	 *
	 * @param x
	 *            the x coordinate to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the center of the ellipse.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the center of the ellipse.
	 *
	 * @param y
	 *            the y coordinate to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the horizontal axis dimension of the ellipse.
	 *
	 * @return the horizontal axis
	 */
	public int getA() {
		return a;
	}

	/**
	 * Gets the vertical axis dimension of the ellipse.
	 *
	 * @return the vertical axis
	 */
	public int getB() {
		return b;
	}
	
	@Override
	public void draw(BWRaster r) {
		int topLeftX = this.x - this.a;
		int topLeftY = this.y - this.b;
		
		int width = topLeftX + this.a*2;
		int height = topLeftY + this.b*2;
		
			if (topLeftY < 0) topLeftY = 0;
			if (topLeftX < 0) topLeftX = 0;
		
		for(int x = topLeftX; x < width; x++) {
			for(int y = topLeftY; y < height; y++) {
				if(this.containsPoint(x, y)){
					r.turnOn(x, y);
				}
			}
		}
	};

}
