package hr.fer.zemris.jmbag0036489629.cmdapps.painters;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.Line;

import java.awt.*;

/**
 * The {@link IPainter} for the line (LINE).
 *
 * @author Juraj Juričić
 */
public class LinePainter implements IPainter {
	@Override
	public GeometricalObject paintObject(Point first, Point second, Color foregroundColor, Color backgroundColor, boolean isFinal) {
		return new Line(first, second, foregroundColor, isFinal);
	}
}
