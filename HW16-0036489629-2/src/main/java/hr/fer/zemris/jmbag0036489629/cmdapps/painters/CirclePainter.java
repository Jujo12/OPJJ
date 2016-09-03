package hr.fer.zemris.jmbag0036489629.cmdapps.painters;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.Circle;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;

/**
 * The {@link IPainter} for the circle (CIRCLE).
 *
 * @author Juraj Juričić
 */
public class CirclePainter implements IPainter {
	@Override
	public GeometricalObject paintObject(Point first, Point second, Color foregroundColor, Color backgroundColor, boolean isFinal) {
		int radius = (int)Math.hypot(first.x-second.x, first.y -second.y);

		return new Circle(first, radius, foregroundColor, isFinal);
	}
}
