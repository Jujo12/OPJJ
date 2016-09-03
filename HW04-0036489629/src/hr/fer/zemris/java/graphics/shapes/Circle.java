package hr.fer.zemris.java.graphics.shapes;

/**
 * The instantiable circle. Really and AbstractEllipse element which a and b
 * axis are linked to the same value and represent the radius.
 * 
 * @author Juraj Juričić
 *
 */
public class Circle extends AbstractEllipse {

	/**
	 * Instantiates a new circle with the specified center and radius.
	 * 
	 * @param x
	 *            the x coordinate of the center.
	 * @param y
	 *            the ycoordinate of the center.
	 * @param r
	 *            circle radius.
	 */
	public Circle(int x, int y, int r) {
		super(x, y, r, r);
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) <= Math.pow(this.a, 2);
	}

	/**
	 * @return Circle radius.
	 */
	public int getRadius() {
		return this.a;
	}

	/**
	 * Set the circle radius.
	 * 
	 * @param radius
	 *            Circle radius.
	 */
	public void setRadius(int radius) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be greater than 0.");
		}

		this.a = radius;
		this.b = radius;
	}

}