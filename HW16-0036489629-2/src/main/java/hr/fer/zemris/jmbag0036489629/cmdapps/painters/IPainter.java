package hr.fer.zemris.jmbag0036489629.cmdapps.painters;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;

/**
 * The objects that implement this interface provide method for generating an
 * object of respective type (e.g. line).
 *
 * @author Juraj Juričić
 */
public interface IPainter {
	
	/**
	 * The method that creates a respective object.
	 *
	 * @param first the first point
	 * @param second the second point
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 * @param isFinal if true, will store the object permanently in the model
	 * @return the generated geometrical object
	 */
	public GeometricalObject paintObject(Point first, Point second,
			Color foregroundColor, Color backgroundColor, boolean isFinal);
}
