package hr.fer.zemris.jmbag0036489629.cmdapps.painters;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.FilledCircle;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;

/**
 * The {@link IPainter} for the filled circle (FCIRCLE). 
 *
 * @author Juraj Juričić
 */
public class FilledCirclePainter implements IPainter {
	@Override
	public GeometricalObject paintObject(Point first, Point second, Color foregroundColor, Color backgroundColor, boolean isFinal) {
		int radius = (int)Math.hypot(first.x-second.x, first.y -second.y);

		return new FilledCircle(first, radius, foregroundColor, backgroundColor, isFinal);
	}
}
