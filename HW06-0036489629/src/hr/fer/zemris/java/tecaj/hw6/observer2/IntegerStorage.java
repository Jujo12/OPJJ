package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Integer wrapper that stores a value and can register
 * {@link IntegerStorageObserver} objects as observers. Notifies all registered
 * {@link IntegerStorageObserver} objects upon a value change.
 * 
 * @author Juraj Juričić
 */
public class IntegerStorage {

	/** The wrapped value. */
	private int value;

	/** The list of observers to be notified upon a change of value. */
	// CopyOnWriteArrayList used to fix the concurrent modification problem
	private List<IntegerStorageObserver> observers = new CopyOnWriteArrayList<>();

	/**
	 * Instantiates a new {@link IntegerStorage} with the given initial value.
	 *
	 * @param initialValue
	 *            the initial value to set.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds the {@link IntegerStorageObserver} objects to the list of observers
	 * to notify on value change.<br>
	 * If the given observer already exists in the list, will fail silently.
	 *
	 * @param observer
	 *            the observer to add to the list. Cannot be null.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		illegalNullObjects(observer);

		// addIfAbsent supported by CopyOnWriteArrayList
		((CopyOnWriteArrayList<IntegerStorageObserver>) observers)
				.addIfAbsent(observer);
	}

	/**
	 * Removes the observer from the list. Guarantees that after the execution,
	 * the given observer will no longer be in the list of observers, and thus
	 * not notified upon the value change.<br>
	 * If the given observer is not present in the list, nothing will happen
	 * (will fail silently).
	 *
	 * @param observer
	 *            the observer to remove from the list. Cannot be null.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		illegalNullObjects(observer);

		observers.remove(observer);
	}

	/**
	 * Removes all observers from the observers list. After the execution, none
	 * of the previously notifiable observers will be called upon a value
	 * change.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}

	/**
	 * Helper method that checks if any of the given objects are null. If any of
	 * the given objects are null, will throw an
	 * {@link IllegalArgumentException}.
	 *
	 * @param objects
	 *            the objects to check for the null pointer.
	 * @throws IllegalArgumentException
	 *             thrown if any of the given objects are null.
	 */
	private static void illegalNullObjects(Object... objects)
			throws IllegalArgumentException {
		for (Object o : objects) {
			if (o == null) {
				throw new IllegalArgumentException("Non-null value expected.");
			}
		}
	}
}