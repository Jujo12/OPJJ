package hr.fer.zemris.java.gui.calc;

/**
 * The Inversible Button interface, represents a button that offers an alternate (inversed) label.
 *
 * @author Juraj Juričić
 */
public interface InversibleButton {
	
	/**
	 * Gets the regular label.
	 *
	 * @return the regular label
	 */
	public String getRegularLabel();
	
	/**
	 * Gets the inverse (alternate) label.
	 *
	 * @return the inverse label
	 */
	public String getInverseLabel();
}
