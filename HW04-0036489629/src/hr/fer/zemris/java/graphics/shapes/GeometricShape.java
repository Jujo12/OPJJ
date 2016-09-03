package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Class GeometricShape. Represents the abstract geometric shape, which all
 * other shapes should inherit.
 * 
 * @author Juraj Juričić
 */
public abstract class GeometricShape {
	/**
	 * Draws the filled geometric shape to the provided BWRaster object.
	 *
	 * @param r
	 *            the BWRaster to draw the shape onto.
	 */
	public void draw(BWRaster r) {
		for (int x = 0; x < r.getWidth(); x++) {
			for (int y = 0; y < r.getHeight(); y++) {
				if (containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}

	/**
	 * Checks if given point belongs to the geometric shape. Returns false only
	 * if the location is outside of the geometric shape. For all other cases,
	 * returns true.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public abstract boolean containsPoint(int x, int y);
}
