package hr.fer.zemris.java.graphics.shapes;

/**
 * The instantiable ellipse, which a and b axis can be set independently.
 * 
 * @author Juraj Juričić
 *
 */
public class Ellipse extends AbstractEllipse {

	/**
	 * Instantiates a new ellipse with the specified center and axis sizes.
	 *
	 * @param x the x coordinate of the center.
	 * @param y the y coordinate of the center.
	 * @param a the horizontal axis size.
	 * @param b the vertical axis size.
	 */
	public Ellipse(int x, int y, int a, int b) {
		super(x, y, a, b);
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return Math.pow(x - this.x, 2) / Math.pow(this.a, 2)
				+ Math.pow(y - this.y, 2) / Math.pow(this.b, 2) <= 1;
	}

	/**
	 * Sets the horizontal axis dimension of the ellipse.
	 *
	 * @param a the horizontal axis size to set
	 */
	public void setA(int a) {
		if (a < 1){
			throw new IllegalArgumentException("A needs to be greater than 0.");
		}
		this.a = a;
	}

	/**
	 * Sets the vertical axis dimension of the ellipse.
	 *
	 * @param b the vertical axis size to set
	 */
	public void setB(int b) {
		if (b < 1){
			throw new IllegalArgumentException("B needs to be greater than 0.");
		}
		this.b = b;
	}

}
