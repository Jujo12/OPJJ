package hr.fer.zemris.jmbag0036489629.cmdapps.components;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.DrawingModelListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * The implementation of a {@link DrawingModel}.
 *
 * @author Juraj Juričić
 */
public class DrawingModelImpl implements DrawingModel {

	/** The list of stored @link{GeometricalObject}s. */
	private List<GeometricalObject> objectList = new ArrayList<>();

	/** The list of DrawingModelListeners. */
	private List<DrawingModelListener> listenerList = new ArrayList<>();

	/** The top left point of all the objects in the model. */
	private Point topLeft;

	/** The dimensions of the bounding box of all objects in the model. */
	private Dimension boundingSize = new Dimension(0, 0);

	@Override
	public int getSize() {
		return objectList.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objectList.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objectList.add(object);

		updateBoundingBox(object);

		int i = objectList.size() - 1;
		listenerList.forEach(l -> l.objectsAdded(this, i, i));
	}

	@Override
	public void removeObject(int index) {
		objectList.remove(index);

		updateBoundingBox(null);

		listenerList.forEach(l -> l.objectsRemoved(this, index, index));
	}

	@Override
	public void openPropertiesBox(int index) {
		objectList.get(index).properties();

		updateBoundingBox(null);

		listenerList.forEach(l -> l.objectsChanged(this, index, index));
	}

	/**
	 * Updates the bounding box variables (topLeft and boundingSize).
	 *
	 * @param object
	 *            the object that is added. If null, all elements of the model
	 *            are used for determining the bounding box.
	 */
	private void updateBoundingBox(GeometricalObject object) {
		Consumer<GeometricalObject> boundingBoxConsumer = geometricalObject -> {
			Point bottomRight = null;

			// top left point
			if (topLeft == null) {
				topLeft = geometricalObject.getTopLeft();
			}
			if (geometricalObject.getTopLeft().x < topLeft.x) {
				topLeft.x = geometricalObject.getTopLeft().x;
			}
			if (geometricalObject.getTopLeft().y < topLeft.y) {
				topLeft.y = geometricalObject.getTopLeft().y;
			}

			// bottom right point
			if (bottomRight == null) {
				bottomRight = geometricalObject.getBottomRight();
			}
			if (geometricalObject.getBottomRight().x > bottomRight.x) {
				bottomRight.x = geometricalObject.getBottomRight().x;
			}
			if (geometricalObject.getBottomRight().y > bottomRight.y) {
				bottomRight.y = geometricalObject.getBottomRight().y;
			}

			// bounding box dimensions
			boundingSize.width = Math.max(bottomRight.x - topLeft.x,
					boundingSize.width);
			boundingSize.height = Math.max(bottomRight.y - topLeft.y,
					boundingSize.height);
		};

		if (object == null) {
			// go through all
			topLeft = null;
			boundingSize = new Dimension(0, 0);
			forEach(boundingBoxConsumer);
		} else {
			boundingBoxConsumer.accept(object);
		}
	}

	@Override
	public void clear() {
		int oldSize = objectList.size();
		if (oldSize == 0) {
			return;
		}
		objectList.clear();
		listenerList.forEach(l -> l.objectsRemoved(this, 0, oldSize - 1));
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listenerList.remove(l);
	}

	@Override
	public void paintModel(Graphics g, Point topLeft, Dimension boundingBox) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, boundingBox.width, boundingBox.height);

		this.forEach(o -> o.draw(g, topLeft));
	}

	public Dimension getBoundingBox() {
		return boundingSize;
	}

	public Point getTopLeft() {
		return topLeft;
	}

	@Override
	public Iterator<GeometricalObject> iterator() {
		return objectList.iterator();
	}
}
