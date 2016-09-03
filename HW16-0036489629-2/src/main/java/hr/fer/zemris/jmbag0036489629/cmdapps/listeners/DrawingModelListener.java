package hr.fer.zemris.jmbag0036489629.cmdapps.listeners;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.DrawingModel;

/**
 * The listener interface for receiving {@link DrawingModel} change events. When
 * the objects set (or list) of a DrawingModel is changed, the appropriate
 * method is invoked.
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Invoked when the objects are added to the model.
	 *
	 * @param source the source
	 * @param index0 the low index
	 * @param index1 the high index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Invoked when the objects are removed from the model.
	 *
	 * @param source the source
	 * @param index0 the low index
	 * @param index1 the high index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Invoked when the objects in the model are changed.
	 *
	 * @param source the source
	 * @param index0 the low index
	 * @param index1 the high index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
