package hr.fer.zemris.java.custom.collections;

/**
 * A linked list-backed collection of Objects. Duplicate elements <b>are
 * allowed</b>. Storage of null references <b>is not allowed</b>.
 * 
 * @author JJ
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Current size of collection (number of elements actually stored).
	 */
	private int size;

	/**
	 * The first node in the list.
	 */
	private ListNode first;

	/**
	 * The last node in the list.
	 */
	private ListNode last;

	/**
	 * The Class ListNode that represents a list node.
	 */
	private static class ListNode {

		/** The previous. */
		public ListNode previous;

		/** The next. */
		public ListNode next;

		/** The value. */
		public Object value;
	}

	/**
	 * Constructs the collection. First = last = null
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}

	/**
	 * Constructs the collection from some other Collection. The elements from
	 * the other Collection are copied into this newly constructed collection.
	 *
	 * @param source
	 *            the source
	 */
	public LinkedListIndexedCollection(Collection source) {
		this.addAll(source);
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

		ListNode node = first;
		for (int i = 0; i < size; i++, node = node.next) {
			array[i] = node.value;
		}

		return array;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris.java.custom.collections.Processor)
	 */
	public void forEach(Processor processor) {
		ListNode node = first;
		for (int i = 0; i < size; i++, node = node.next) {
			processor.process(node.value);
		}
	}

	/**
	 * Adds the given object into this collection at the end of collection.
	 * Newly added element becomes the element at the biggest index.
	 * [Complexity: O(1)].
	 *
	 * @param value
	 *            the value
	 */
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("The value cannot be null.");
		}

		ListNode node = new ListNode();
		node.value = value;
		if (first == null) {
			first = last = node;
		} else {
			node.previous = last;
			last.next = node;

			last = node;
		}

		size++;
	}

	/**
	 * Returns the object that is stored in linked list at position index. Valid
	 * indexes are 0 to size-1. If index is invalid, throws
	 * IndexOutOfBoundsException. [Complexity: O(n)]
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The index should be between 0 and " + (size - 1));
		}

		ListNode node;
		if (index < size / 2) {
			node = first;
			for (int i = 0; i < index; i++, node = node.next) {
			}
		} else {
			node = last;
			for (int i = size; i > index; i--, node = node.previous) {
			}
		}

		return node.value;
	}

	/**
	 * Removes all elements from the collection.
	 */
	public void clear() {
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * linked-list. Elements starting from this position are shifted one
	 * position. The legal positions are 0 to size. If position is invalid
	 * throws IllegalArgumentException. [Complexity: O(n)].
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

		ListNode node = first;
		for (int i = 0; i < position; i++, node = node.next) {
		}

		ListNode newNode = new ListNode();
		newNode.value = value;
		newNode.next = node;
		newNode.previous = node.previous;

		node.previous.next = newNode;
		node.previous = newNode;

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
		ListNode node = first;
		for (int i = 0; i < size; i++, node = node.next) {
			if (node.value.equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index throws
	 * IndexOutOfBoundsException.
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Legal indexes are between 0 and " + (size - 1));
		}

		ListNode node = first;
		for (int i = 0; i < index; i++, node = node.next) {
		}

		node.previous.next = node.next;
		node.next.previous = node.previous;

		size--;
	}
}
