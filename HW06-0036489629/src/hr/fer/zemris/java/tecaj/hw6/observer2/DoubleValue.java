package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * The implementation of {@link IntegerStorageObserver} that calculates the
 * double value of an {@link IntegerStorage} and outputs it to the standard
 * output. Additionally, has the lifespan of n calls: after writing the double
 * value for the n-th time, the observer automatically de-registers itself from
 * the subject.
 * 
 * @author Juraj Juričić
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * The remaining number of calls. Initialized to the lifespan and reduced by
	 * one on every iteration.
	 */
	int remaining;

	/**
	 * Instantiates a new double value.
	 *
	 * @param lifespan
	 *            the lifespan of this observer object
	 */
	public DoubleValue(int lifespan) {
		if (lifespan < 1) {
			throw new IllegalArgumentException(
					"Lifespan must be 1 or greater.");
		}
		this.remaining = lifespan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#
	 * valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorageChange ichange) {
		int value = ichange.getNewValue();
		System.out.printf("Double value: %d%n", value * 2);

		if (--remaining == 0) {
			ichange.getIstorage().removeObserver(this);
		}
	}

}
