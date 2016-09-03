package hr.fer.zemris.java.gui.charts;

/**
 * The helper class that provides utility method(s).
 *
 * @author Juraj Juričić
 */
public class JJHelper {
	
	/**
	 * Checks if any of the objects given as arguments are null.
	 *
	 * @param objects the objects
	 * @throws IllegalArgumentException Thrown if any of the given objects is null.
	 */
	static void checkNotNullArguments(Object ...objects) throws IllegalArgumentException{
		for(Object o : objects){
			if (o == null){
				throw new IllegalArgumentException("Argument cannot be null.");
			}
		}
	}
}
