package hr.fer.zemris.jmbag0036489629.cmdapps.components;

import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.ColorChangeListener;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * The component that allows the user to pick a color using JColorChooser.
 *
 * @author Juraj Juričić
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7490241507021734244L;

	/** The preferred size of component, in pixels. */
	private static final int SIZE = 15;

	/** The currently selected color. */
	private Color selectedColor;

	/** The list of ColorChangeListeners. */
	private List<ColorChangeListener> listeners;

	/**
	 * Instantiates a new JColorArea.
	 *
	 * @param name
	 *            the name
	 * @param initial
	 *            the initial color value
	 */
	public JColorArea(String name, Color initial) {
		selectedColor = initial;
		listeners = new LinkedList<>();

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color pick = JColorChooser.showDialog(JColorArea.this,
						"Choose " + name, selectedColor);
				if (pick == null) {
					return;
				}

				Color old = selectedColor;
				selectedColor = pick;
				fireColorChangeEvent(old, selectedColor);

				repaint();
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}

	/**
	 * Adds the color change listener to the list of listeners.
	 *
	 * @param l the listener
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	/**
	 * Removes the color change listener from the list of listeners.
	 *
	 * @param l the listener
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Fires the color change event to all listeners.
	 *
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	private void fireColorChangeEvent(Color oldColor, Color newColor) {
		listeners.forEach(l -> {
			l.newColorSelected(this, oldColor, newColor);
		});
	}

	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Gets the current color as RGB string.
	 *
	 * @return the current color in RGB format: (R, G, B)
	 */
	public String getCurrentColorRGBString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(selectedColor.getRed());
		sb.append(", ");
		sb.append(selectedColor.getGreen());
		sb.append(", ");
		sb.append(selectedColor.getBlue());
		sb.append(")");

		return sb.toString();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		Insets i = getInsets();

		g.fillRect(i.left, i.top, SIZE, SIZE);
	}
}
