package hr.fer.zemris.jmbag0036489629.cmdapps.components;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.DrawingModelListener;

import java.awt.*;

/**
 * The model that contains GeometricalObjects to be drawn.
 *
 * @author Juraj Juričić
 */
public interface DrawingModel extends Iterable<GeometricalObject> {
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize();
	
	/**
	 * Gets the object at given index.
	 *
	 * @param index the index
	 * @return the object
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds the object to the model.
	 *
	 * @param object the object
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the object from model.
	 *
	 * @param index the index
	 */
	public void removeObject(int index);
	
	/**
	 * Opens the properties frame for the object at given index.
	 *
	 * @param index the index
	 */
	public void openPropertiesBox(int index);
	
	/**
	 * Clears the model.
	 */
	public void clear();

	/**
	 * Adds the {@link DrawingModelListener}.
	 *
	 * @param l the listener to add
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes the {@link DrawingModelListener}.
	 *
	 * @param l the listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Paints the model to given {@link Graphics}.
	 *
	 * @param g the graphics
	 * @param topLeft the top left point
	 * @param boundingBox the bounding box dimensions
	 */
	public void paintModel(Graphics g, Point topLeft, Dimension boundingBox);

	/**
	 * Gets the bounding box size.
	 *
	 * @return the bounding box
	 */
	public Dimension getBoundingBox();
	
	/**
	 * Gets the top left point.
	 *
	 * @return the top left point
	 */
	public Point getTopLeft();
}