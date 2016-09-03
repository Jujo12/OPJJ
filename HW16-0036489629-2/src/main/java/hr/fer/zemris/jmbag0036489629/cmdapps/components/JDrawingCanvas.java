package hr.fer.zemris.jmbag0036489629.cmdapps.components;

import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.Circle;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.FilledCircle;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.Line;
import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.DrawingModelListener;
import hr.fer.zemris.jmbag0036489629.cmdapps.painters.IPainter;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The JVDraw canvas component. Acts as a model listener.
 *
 * @author Juraj Juričić
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5025345354547079682L;

	/** The drawing model. */
	private DrawingModel model;

	/** The object that is currently being drawn. */
	private GeometricalObject active;

	/** The foreground color provider. */
	private IColorProvider foregroundColorProvider;

	/** The background color provider. */
	private IColorProvider backgroundColorProvider;

	/** The currently active painter. */
	private IPainter painter;

	/**
	 * The first point of the object that is being drawn. If null, no object is
	 * currently being drawn.
	 */
	private Point firstPoint;

	/** If true, the drawing has changed since the last save. */
	private boolean isChanged = false;

	/**
	 * Instantiates a new canvas.
	 *
	 * @param foregroundColorProvider
	 *            the foreground color provider
	 * @param backgroundColorProvider
	 *            the background color provider
	 * @param model
	 *            the model
	 */
	public JDrawingCanvas(IColorProvider foregroundColorProvider,
			IColorProvider backgroundColorProvider, DrawingModel model) {
		this.foregroundColorProvider = foregroundColorProvider;
		this.backgroundColorProvider = backgroundColorProvider;
		this.model = model;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (painter == null) {
					return;
				}

				if (firstPoint == null) {
					firstPoint = e.getPoint();
					return;
				}

				addObject(e.getPoint(), true);
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (firstPoint == null || painter == null) {
					return;
				}

				addObject(e.getPoint(), false);
			}
		});
	}

	/**
	 * Adds the object to the canvas.
	 *
	 * @param secondPoint
	 *            the second point
	 * @param isFinal
	 *            if true, the object will be permanently stored in the model
	 */
	private void addObject(Point secondPoint, boolean isFinal) {
		active = painter.paintObject(firstPoint, secondPoint,
				foregroundColorProvider.getCurrentColor(),
				backgroundColorProvider.getCurrentColor(), isFinal);

		if (isFinal) {
			model.add(active);
			reset();
			return;
		}

		repaint();
	}

	/**
	 * Resets the object being drawn.
	 */
	private void reset() {
		firstPoint = null;
		active = null;
	}

	/**
	 * Sets the painter.
	 *
	 * @param painter
	 *            the new painter
	 */
	public void setPainter(IPainter painter) {
		this.painter = painter;
	}

	@Override
	protected void paintComponent(Graphics g) {
		model.paintModel(g, new Point(0, 0),
				new Dimension(getWidth(), getHeight()));

		if (active != null) {
			active.draw(g, new Point(0, 0));
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		isChanged = true;
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		isChanged = true;
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		isChanged = true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		model.forEach(c -> {
			sb.append(c.getText());
			sb.append(System.lineSeparator());
		});

		return sb.toString();
	}

	/**
	 * Loads image from given lines. Syntax is as follows:<br>
	 * LINE x0 y0 x1 y1 red green blue<br>
	 * CIRCLE centerx centery radius red green blue<br>
	 * FCIRCLE centerx centery radius red green blue red green blue<br>
	 *
	 * @param lines
	 *            the lines
	 */
	public void loadFromLines(List<String> lines) {
		lines.forEach(l -> {
			String[] data = l.split(" ");
			try {
				int r, g, b, cx, cy, radius;
				GeometricalObject o = null;
				switch (data[0].toUpperCase()) {
				case "LINE":
					int x0 = Integer.parseInt(data[1]);
					int y0 = Integer.parseInt(data[2]);
					int x1 = Integer.parseInt(data[3]);
					int y1 = Integer.parseInt(data[4]);
					r = Integer.parseInt(data[5]);
					g = Integer.parseInt(data[6]);
					b = Integer.parseInt(data[7]);

					o = new Line(new Point(x0, y0), new Point(x1, y1),
							new Color(r, g, b), true);
					break;
				case "CIRCLE":
					cx = Integer.parseInt(data[1]);
					cy = Integer.parseInt(data[2]);
					radius = Integer.parseInt(data[3]);
					r = Integer.parseInt(data[4]);
					g = Integer.parseInt(data[5]);
					b = Integer.parseInt(data[6]);

					o = new Circle(new Point(cx, cy), radius,
							new Color(r, g, b), true);
					break;
				case "FCIRCLE":
					cx = Integer.parseInt(data[1]);
					cy = Integer.parseInt(data[2]);
					radius = Integer.parseInt(data[3]);
					r = Integer.parseInt(data[4]);
					g = Integer.parseInt(data[5]);
					b = Integer.parseInt(data[6]);
					int rFill = Integer.parseInt(data[7]);
					int gFill = Integer.parseInt(data[8]);
					int bFill = Integer.parseInt(data[9]);

					o = new FilledCircle(new Point(cx, cy), radius,
							new Color(r, g, b), new Color(rFill, gFill, bFill),
							true);
					break;
				default:
					// fail silently
					break;
				}

				if (o != null) {
					model.add(o);
				}
			} catch (Exception e) {
				// fail silently
			}
			repaint();
		});
	}

	/**
	 * Clears the canvas and resets all IDs
	 */
	public void clear() {
		Line.resetId();
		Circle.resetId();
		FilledCircle.resetId();
		model.clear();
	}

	/**
	 * Checks if the image has changed since last save.
	 *
	 * @return true, if is changed
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * Sets the changed.
	 *
	 * @param changed the new changed
	 */
	public void setChanged(boolean changed) {
		isChanged = changed;
	}
}
