package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * The Class LikeMedian.
 *
 * @author Juraj Juričić
 * @param <T>
 *            the generic type
 */
public class LikeMedian<T extends Comparable<T>> implements Iterable<T> {

	/** The list containing elements to get the median of, in original order. */
	private List<T> originalData;
	
	/**
	 * The list containing elements to get the median of, sorted.
	 */
	private List<T> sortedData;

	
	/**
	 * Instantiates a new LikeMedian class.
	 */
	public LikeMedian() {
		this.originalData = new ArrayList<>();
		this.sortedData = new ArrayList<>();
	}

	/**
	 * Adds the object to the list of elements used to calculate the median
	 * from.
	 *
	 * @param object
	 *            the object to add.
	 */
	public void add(T object) {
		originalData.add(object);
		sortedData.add(object);
		
		sortedData.sort(null);
	}

	/**
	 * Gets the median element wrapped in {@link Optional} wrapper. The returned
	 * element will be marked as empty if there are no elements in the list.
	 *
	 * @return the optional median element
	 */
	public Optional<T> get() {
		int size = sortedData.size();
		if (size == 0) {
			return Optional.empty();
		}

		T median;
		if (size % 2 != 0) {
			median = sortedData.get(size / 2);
		} else {
			median = sortedData.get(size / 2 - 1);
		}

		return Optional.of(median);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return originalData.iterator();
	}

}
