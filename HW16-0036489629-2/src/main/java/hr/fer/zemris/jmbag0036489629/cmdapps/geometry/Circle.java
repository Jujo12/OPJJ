package hr.fer.zemris.jmbag0036489629.cmdapps.geometry;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.JColorArea;

/**
 * The GeometricalObject that represents a single circle. A circle is defined by
 * a center point, radius, and an outline color.
 *
 * @author Juraj Juričić
 */
public class Circle extends GeometricalObject {
	static {
		id = 1;
	}

	/** The center point. */
	private Point center;

	/** The radius. */
	private int radius;

	/** The outline color. */
	private Color color;

	/**
	 * Instantiates a new circle.
	 *
	 * @param center
	 *            the center point
	 * @param radius
	 *            the radius
	 * @param color
	 *            the outline color
	 * @param isFinal
	 *            if true, the object should be added to the model permanently
	 */
	public Circle(Point center, int radius, Color color, boolean isFinal) {
		super("Circle " + id);
		if (isFinal) {
			id++;
		}

		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	/**
	 * Resets the local static object counter.
	 */
	public static void resetId() {
		id = 1;
	}

	public Dimension getSize() {
		return new Dimension(radius * 2, radius * 2);
	}

	public Point getTopLeft() {
		return new Point(center.x - radius, center.y - radius);
	}

	@Override
	public void draw(Graphics g, Point offset) {
		g.setColor(color);
		g.drawOval(center.x - radius - offset.x, center.y - radius - offset.y,
				radius * 2, radius * 2);
	}

	@Override
	public void properties() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		NumberFormatter formatter = new NumberFormatter(
				NumberFormat.getInstance());
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		Dimension tfDim = new Dimension(35, 20);
		JFormattedTextField cx = new JFormattedTextField(formatter);
		cx.setText(Integer.toString(center.x));
		cx.setPreferredSize(tfDim);
		JFormattedTextField cy = new JFormattedTextField(formatter);
		cy.setText(Integer.toString(center.y));
		cy.setPreferredSize(tfDim);

		JFormattedTextField r = new JFormattedTextField(formatter);
		r.setText(Integer.toString(radius));
		r.setPreferredSize(tfDim);

		JColorArea newColor = new JColorArea("foreground", color);

		JPanel cLine = new JPanel();
		cLine.add(new JLabel("Center point:"));
		cLine.add(cx);
		cLine.add(new JLabel(","));
		cLine.add(cy);
		panel.add(cLine);

		JPanel radiusLine = new JPanel();
		radiusLine.add(new JLabel("Radius:"));
		radiusLine.add(r);
		panel.add(radiusLine);

		JPanel colorLine = new JPanel();
		colorLine.add(new JLabel("Color:"));
		colorLine.add(newColor);
		panel.add(colorLine);

		int status = JOptionPane.showConfirmDialog(null, panel, this.toString(),
				JOptionPane.OK_CANCEL_OPTION);
		if (status == JOptionPane.OK_OPTION) {
			try {
				center = new Point(Integer.parseInt(cx.getText().trim()),
						Integer.parseInt(cy.getText().trim()));
				radius = Integer.parseInt(r.getText());
			} catch (NumberFormatException e) {
			}

			color = newColor.getCurrentColor();
		}
	}

	@Override
	public String getText() {
		String s = String.format("CIRCLE %d %d %d %d %d %d", center.x, center.y,
				radius, color.getRed(), color.getGreen(), color.getBlue());
		return s;
	}
}
