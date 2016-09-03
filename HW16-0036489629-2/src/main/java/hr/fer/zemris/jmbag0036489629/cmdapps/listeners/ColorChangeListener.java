package hr.fer.zemris.jmbag0036489629.cmdapps.listeners;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.IColorProvider;

import java.awt.*;

/**
 * The listener interface for receiving colorChange events.
 * The class that is interested in processing a colorChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addColorChangeListener<code> method. When
 * the linked {@link IColorProvider} changes a color, the method
 * newColorSelected is invoked.
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Invoked upon color change by {@link IColorProvider}.
	 *
	 * @param source the source of change
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
