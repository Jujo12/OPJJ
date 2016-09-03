package hr.fer.zemris.java.graphics.shapes;

/**
 * The instantiable square. Really an AbstractRectangle which side sizes are
 * linked to the same value.
 * 
 * @author Juraj Juričić
 *
 */
public class Square extends AbstractRectangle {

	/**
	 * Instantiates a new square with the given coordinates and side size.
	 * 
	 * @param x
	 *            the x coordinate of the top left corner of the rectangle.
	 * @param y
	 *            the y coordinate of the top left corner of the rectangle.
	 * @param size
	 *            the size of the square's side
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	/**
	 * Sets the size of the square's sides to a new value. Modifies both width
	 * and height.
	 * 
	 * @param width
	 *            the new width (and height)
	 */
	public void setWidth(int width) {
		setSize(width);
	}

	/**
	 * Sets the size of the square's sides to a new value. Modifies both width
	 * and height.
	 * 
	 * @param height
	 *            the new height (and width)
	 */
	public void setHeight(int height) {
		setSize(height);
	}

	/**
	 * Sets the size of the square's sides to a new value. Modifies both width
	 * and height.
	 * 
	 * @param size
	 *            the new size
	 */
	public void setSize(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("The size cannot be less than 1.");
		}
	}

}
