package hr.fer.zemris.jmbag0036489629.cmdapps.geometry;

import java.awt.*;

/**
 * The abstract class that represents a single geometrical object. Classes that
 * extend this class provide methods for drawing the objects.
 *
 * @author Juraj Juričić
 */
public abstract class GeometricalObject {
	
	/** The index. Classes should implement their own id. */
	protected static int id;

	/** The object name. */
	private String name;

	/**
	 * Instantiates a new geometrical object.
	 *
	 * @param name the name
	 */
	public GeometricalObject(String name) {
		this.name = name;
	}

	/**
	 * Draws the object to given Graphics.
	 *
	 * @param g the graphics
	 * @param offset the top left offset
	 */
	public abstract void draw(Graphics g, Point offset);

	/**
	 * Summons a frame for setting object properties.
	 */
	public abstract void properties();

	/**
	 * Gets the object's line definition.
	 *
	 * @return the text
	 */
	public abstract String getText();

	/**
	 * Gets the top left point.
	 *
	 * @return the top left
	 */
	public abstract Point getTopLeft();

	/**
	 * Gets the object's dimensions.
	 *
	 * @return the size
	 */
	public abstract Dimension getSize();

	/**
	 * Gets the bottom right point.
	 *
	 * @return the bottom right
	 */
	public Point getBottomRight() {
		return new Point(getTopLeft().x + (int) getSize().width,
				getTopLeft().y + (int) getSize().height);
	}

	@Override
	public String toString() {
		return name;
	}

}
