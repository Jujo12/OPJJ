package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * The implementation of {@link IntegerStorageObserver} that counts how many
 * times data changed in an {@link IntegerStorage} object.
 * 
 * @author Juraj Juričić
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Stores the amount of changes within the target {@link IntegerStorage}
	 * object.
	 */
	private int count;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#
	 * valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorageChange ichange) {
		System.out.printf("Number of value changes since tracking: %d%n",
				++count);
	}

}
