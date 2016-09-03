package hr.fer.zemris.java.graphics.shapes;

/**
 * The instantiable rectangle, which width and height can be set independently.
 * 
 * @author Juraj Juričić
 *
 */
public class Rectangle extends AbstractRectangle {

	/**
	 * Instantiates a new rectangle with the given coordinates and dimensions.
	 *
	 * @param x the x coordinate of the top left corner of the rectangle.
	 * @param y the y coordinate of the top left corner of the rectangle.
	 * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Sets the width of the rectangle.
	 *
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		if (width < 1){
			throw new IllegalArgumentException("Width cannot be less than 1.");
		}
		
		this.width = width;
	}

	/**
	 * Sets the height of the rectangle.
	 *
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		if (height < 1){
			throw new IllegalArgumentException("Height cannot be less than 1.");
		}
		
		this.height = height;
	}

}
