package hr.fer.zemris.jmbag0036489629.cmdapps.components;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.DrawingModelListener;

import javax.swing.*;

/**
 * The {@link ListModel} that stores {@link GeometricalObject} objects for
 * display in JVDraw application.
 *
 * @author Juraj Juričić
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
		implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4412956646059877102L;
	
	/** The decorated model. */
	private DrawingModel model;

	/**
	 * Instantiates a new drawing object list model.
	 *
	 * @param model
	 *            the model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		super();
		this.model = model;
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	/**
	 * Removes the element at the given index.
	 *
	 * @param index
	 *            the index
	 */
	public void removeElementAt(int index) {
		model.removeObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}
}
