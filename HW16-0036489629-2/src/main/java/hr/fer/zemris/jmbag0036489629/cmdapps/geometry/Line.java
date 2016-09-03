package hr.fer.zemris.jmbag0036489629.cmdapps.geometry;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.JColorArea;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

/**
 * The GeometricalObject that represents a single line. A line is defined by two
 * points and a color.
 *
 * @author Juraj Juričić
 */
public class Line extends GeometricalObject {
	static {
		id = 1;
	}

	/** The first point. */
	private Point p1;

	/** The second point. */
	private Point p2;

	/** The color. */
	private Color color;

	/**
	 * Instantiates a new line.
	 *
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @param color
	 *            the color
	 * @param isFinal
	 *            if true, the object should be added to the model permanently
	 */
	public Line(Point p1, Point p2, Color color, boolean isFinal) {
		super("Line " + id);
		if (isFinal) {
			id++;
		}

		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
	}

	/**
	 * Resets the local static object counter.
	 */
	public static void resetId() {
		id = 1;
	}

	public Dimension getSize() {
		return new Dimension(Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
	}

	public Point getTopLeft() {
		return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
	}

	@Override
	public void draw(Graphics g, Point offset) {
		g.setColor(color);
		g.drawLine(p1.x - offset.x, p1.y - offset.y, p2.x - offset.x,
				p2.y - offset.y);
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
		JFormattedTextField p1x = new JFormattedTextField(formatter);
		p1x.setText(Integer.toString(p1.x));
		p1x.setPreferredSize(tfDim);
		JFormattedTextField p1y = new JFormattedTextField(formatter);
		p1y.setText(Integer.toString(p1.y));
		p1y.setPreferredSize(tfDim);

		JFormattedTextField p2x = new JFormattedTextField(formatter);
		p2x.setText(Integer.toString(p2.x));
		p2x.setPreferredSize(tfDim);
		JFormattedTextField p2y = new JFormattedTextField(formatter);
		p2y.setText(Integer.toString(p2.y));
		p2y.setPreferredSize(tfDim);

		JColorArea newColor = new JColorArea("foreground", color);

		JPanel p1Line = new JPanel();
		p1Line.add(new JLabel("First point:"));
		p1Line.add(p1x);
		p1Line.add(new JLabel(","));
		p1Line.add(p1y);
		panel.add(p1Line);

		JPanel p2Line = new JPanel();
		p2Line.add(new JLabel("Second point:"));
		p2Line.add(p2x);
		p2Line.add(new JLabel(","));
		p2Line.add(p2y);
		panel.add(p2Line);

		JPanel colorLine = new JPanel();
		colorLine.add(new JLabel("Color:"));
		colorLine.add(newColor);
		panel.add(colorLine);

		int status = JOptionPane.showConfirmDialog(null, panel, this.toString(),
				JOptionPane.OK_CANCEL_OPTION);
		if (status == JOptionPane.OK_OPTION) {
			try {
				p1 = new Point(Integer.parseInt(p1x.getText().trim()),
						Integer.parseInt(p1y.getText().trim()));
				p2 = new Point(Integer.parseInt(p2x.getText().trim()),
						Integer.parseInt(p2y.getText().trim()));
			} catch (NumberFormatException e) {
			}

			color = newColor.getCurrentColor();
		}
	}

	@Override
	public String getText() {
		String s = String.format("LINE %d %d %d %d %d %d %d", p1.x, p1.y, p2.x,
				p2.y, color.getRed(), color.getGreen(), color.getBlue());
		return s;
	}
}
