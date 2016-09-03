package hr.fer.zemris.java.graphics.shapes;

/**
 * The ENUM containing {@link GeometricShape} types and their info.
 * 
 * @author Juraj Juričić
 *
 */
public enum ShapeType {

	/**
	 * The circle.
	 */
	CIRCLE(Circle.class, 3),
	/**
	 * The ellipse.
	 */
	ELLIPSE(Ellipse.class, 4),
	/**
	 * The rectangle.
	 */
	RECTANGLE(Rectangle.class, 4),
	/**
	 * The square.
	 */
	SQUARE(Square.class, 3);

	/**
	 * The class of the {@link GeometricShape}.
	 */
	private Class<? extends GeometricShape> shapeClass;
	
	/** The number of arguments for the shape. */
	private int paramCount;

	/**
	 * Instantiates a new shape type ENUM.
	 *
	 * @param shapeClass
	 *            the class of the {@link GeometricShape}.
	 * @param paramCount
	 *            the number of arguments for the shape.
	 */
	ShapeType(Class<? extends GeometricShape> shapeClass, int paramCount) {
		this.shapeClass = shapeClass;
		this.paramCount = paramCount;
	}

	/**
	 * Gets the shape class.
	 *
	 * @return the shape class
	 */
	public Class<? extends GeometricShape> getShapeClass() {
		return shapeClass;
	}

	/**
	 * Gets the param count.
	 *
	 * @return the param count
	 */
	public int getParamCount() {
		return paramCount;
	}
}
