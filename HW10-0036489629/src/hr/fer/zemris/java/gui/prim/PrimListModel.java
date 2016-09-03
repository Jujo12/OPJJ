package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The Class PrimListModel. Used as a model for {@link JList}, containing prime
 * numbers. A new prime number is calculated upon next() method call.
 *
 * @author Juraj Juričić
 */
public class PrimListModel implements ListModel<Integer> {

	/** The list of calculated primes. */
	private List<Integer> primes;

	/** The set of listeners. */
	private Set<ListDataListener> listeners;

	/**
	 * Instantiates a new model.
	 */
	public PrimListModel() {
		primes = new ArrayList<>();
		primes.add(1);

		listeners = new HashSet<>();
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Adds the next prime number to the model's local list.
	 */
	public void next() {
		int oldCount = primes.size();
		primes.add(getNextPrime());

		listeners.forEach(l -> {
			l.intervalAdded(new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, oldCount, oldCount));
		});
	}

	/**
	 * Gets the next prime number. List of previous prime numbers is stored
	 * locally.
	 *
	 * @return the next prime number
	 */
	private int getNextPrime() {
		int oldCount = primes.size();

		if (oldCount == 1) {
			return 2;
		}
		if (oldCount == 2) {
			return 3;
		}

		int prime = primes.get(oldCount - 1);

		do {
			prime += 2;
		} while (!isPrime(prime));

		return prime;
	}

	/**
	 * Checks if a number is prime.
	 *
	 * @param n
	 *            the number to check
	 * @return true, if is prime
	 */
	private boolean isPrime(long n) {
		if (n == 1) {
			return false;
		}
		if (n == 2 || n == 3) {
			return true;
		}
		if (n % 2 == 0 || n % 3 == 0) {
			return false;
		}

		long numberSqrt = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= numberSqrt; i += 6) {
			// checks against (6k-1) and (6k+1) division, since 2k and 3k
			// are already checked.
			if (n % (i - 1) == 0 || n % (i + 1) == 0) {
				return false;
			}
		}
		return true;
	}

}
