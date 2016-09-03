package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Observer for the {@link IntegerStorage} objects. Provides a single method
 * valueChanged to be called whenever a change has occured in the associated
 * {@link IntegerStorage} object.<br>
 * Simplified by design: the observer objects will not be reused, or associated
 * to multiple {@link IntegerStorage} objects at any given time.
 * 
 * @author Juraj Juričić
 */
public interface IntegerStorageObserver {

	/**
	 * The method to be called when the value of the associated
	 * {@link IntegerStorage} has been changed.
	 *
	 * @param istorage
	 *            the respective {@link IntegerStorage}
	 */
	public void valueChanged(IntegerStorage istorage);
}
