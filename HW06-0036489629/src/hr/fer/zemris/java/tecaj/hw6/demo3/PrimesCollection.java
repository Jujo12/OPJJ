package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The iterable container of prime numbers. Calculates the next prime number
 * on-call.
 *
 * @author Juraj Juričić
 */
public class PrimesCollection implements Iterable<Integer> {

	/** The size of the collection. */
	private int size;

	/** The current index. */
	private int index = 0;

	/**
	 * Instantiates a new primes collection.
	 *
	 * @param count
	 *            the count of primes
	 */
	public PrimesCollection(int count) {
		if (count < 1) {
			throw new IllegalArgumentException("Count must be at least 1.");
		}
		this.size = count;
	}

	/**
	 * Checks if a number is prime. Improved algorithm (v2).
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}

	/**
	 * The Class PrimeIterator. Iterates over the prime numbers provided by the
	 * collection.
	 *
	 * @author Juraj Juričić
	 */
	private class PrimeIterator implements Iterator<Integer> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return index < size;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			Integer prime = 2;

			if (index == 0) {
				prime = 2;
			} else {
				int counter = 1;

				for (int i = 3; counter <= index; i += 2) {
					if (isPrime(i)) {
						prime = i;
						counter++;
					}
				}
			}

			index++;
			return prime;
		}

	}

}
