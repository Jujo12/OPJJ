package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * A resizable array-backed collection of Objects. Duplicate elements <b>are
 * allowed</b>. Storage of null references <b>is not allowed</b>.
 * 
 * @author JJ
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Default capacity. Constant.
	 */
	public static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Current size of collection (number of elements actually stored).
	 */
	private int size;

	/**
	 * Current capacity of allocated array of object references.
	 */
	private int capacity;

	/**
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	private Object[] elements;

	/**
	 * Constructs an instance with <i>capacity</i> set to 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Instantiates a new ArrayIndexedCollection with the provided array
	 * capacity.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the array
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity has to be a number greater than zero.");
		}

		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Instantiates a new ArrayIndexedCollection copying all the elements from
	 * the provided other Collection.
	 *
	 * @param source
	 *            the other Collection to copy the elements from
	 */
	public ArrayIndexedCollection(Collection source) {
		// the initial capacity should be equal to the copied Collection's size
		this(source, source.size());
	}

	/**
	 * Instantiates a new ArrayIndexedCollection copying all the elements from
	 * the provided other Collection and setting the provided array capacity.
	 *
	 * @param source
	 *            the other Collection to copy the elements from
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public ArrayIndexedCollection(Collection source, int initialCapacity) {
		this(initialCapacity);

		this.addAll(source);
	}

	/**
	 * Reallocates the array (doubling the capacity).
	 */
	private void reallocate() {
		// reallocate array
		this.elements = Arrays.copyOf(elements, capacity * 2);
		this.capacity *= 2;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	public int size() {
		return this.size;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}

		return (indexOf(value) > -1);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}

		return array;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris.java.custom.collections.Processor)
	 */
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Adds the given object into this collection. If the elements array is
	 * full, it will be reallocated by doubling its size. The value cannot be
	 * null. [Complexity: O(1)].
	 *
	 * @param value
	 *            the value
	 */
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("The value cannot be null.");
		}

		if (this.size == this.capacity) {
			this.reallocate();
		}

		this.elements[size] = value;
		size++;
	}

	/**
	 * Returns the object that is stored in backing array at position index.
	 * Valid indexes are 0 to size-1. If index is invalid, throws the
	 * IndexOutOfBoundsException. [Complexity: O(1)].
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The index should be between 0 and " + (size - 1));
		}

		return elements[index];
	}

	/**
	 * Removes all elements from the collection. The allocated array is left at
	 * current capacity.
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * array. Elements at position and at greater positions will be shifted one
	 * place toward the end, so that an empty place is created at position. The
	 * legal positions are 0 to size. If position is invalid, throws
	 * IllegalArgumentException. [Complexity: O(n)].
	 *
	 * @param value
	 *            the value
	 * @param position
	 *            the position
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException("Position should be between 0 and " + size);
		}

		if (size == capacity) {
			reallocate();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;

		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. [Complexity: O(n)].
	 *
	 * @param value
	 *            the value
	 * @return the int
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index throws
	 * IndexOutOfBoundsException
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Legal indexes are between 0 and " + (size - 1));
		}

		elements[index] = null;
		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;

		size--;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	/* 
	 * @see hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object value){
		int index = indexOf(value);
		if (index == -1){
			return false;
		}
		
		remove(index);
		return true;
	}
}
