package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * The instances of this class represent a single change of
 * {@link IntegerStorage} value. Stores the information which storage caused the
 * change, as well as the old and new values.
 *
 * @author Juraj Juričić
 */
public class IntegerStorageChange {

	/** The storage that called the change. */
	private IntegerStorage istorage;

	/** The old value. */
	private int oldValue;

	/** The new value. */
	private int newValue;

	/**
	 * Instantiates a new change from the given storage object with the given
	 * old and new values.
	 *
	 * @param istorage
	 *            the istorage
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue,
			int newValue) {
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Gets the {@link IntegerStorage} object.
	 *
	 * @return the istorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Gets the old value.
	 *
	 * @return the oldValue
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the newValue
	 */
	public int getNewValue() {
		return newValue;
	}

}