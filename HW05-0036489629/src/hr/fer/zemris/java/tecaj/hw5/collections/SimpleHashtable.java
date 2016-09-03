package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A simple hash table.
 * 
 * @author Juraj Juričić
 *
 * @param <K>
 *            Key type
 * @param <V>
 *            Value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/** The table size. */
	private int size;

	/** The slot count. */
	private int slotCount;

	/** The mod counter (used for iterator). */
	private int modCount = 0;

	/** The table. */
	private TableEntry<K, V> table[];

	/** The default slot count. */
	private final static int DEFAULT_SLOTS = 16;

	/**
	 * Instantiates a new simple hashtable.
	 *
	 * @param slotCount
	 *            the number of slots
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int slotCount) {
		if (slotCount < 1) {
			throw new IllegalArgumentException("Slot count cannot be less than 1.");
		}
		this.slotCount = (int) Math.pow(2, Math.ceil(Math.log(slotCount) / Math.log(2)));

		this.table = (TableEntry<K, V>[]) new TableEntry[slotCount];
	}

	/**
	 * Instantiates a new simple hashtable with the default amount of slots (16)
	 */
	public SimpleHashtable() {
		this(DEFAULT_SLOTS);
	}

	/**
	 * Gets the slot count.
	 *
	 * @return the slot count
	 */
	public int getSlotCount() {
		return slotCount;
	}

	/**
	 * Size of the table.
	 *
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds the (key, value) pair to the table
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}

		// check for 75% fill
		if ((size + 1) / (1.0 * slotCount) >= 0.75) {
			reAllocate();
		}

		addToTable(new TableEntry<K, V>(key, value, null), table, true);
	}

	/**
	 * Reallocates the table
	 */
	private void reAllocate() {
		int newSlotCount = slotCount * 2;

		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newArray = (TableEntry<K, V>[]) new TableEntry[newSlotCount];

		for (TableEntry<K, V> el : this) {
			addToTable(el, newArray, false);
		}
		slotCount = newSlotCount;

		this.table = newArray;
	}

	/**
	 * Calculates the slot index based on the given key and slotCount.
	 *
	 * @param key
	 *            the key
	 * @param slotCount
	 *            the slot count
	 * @return the slot index
	 */
	private int getSlot(K key, int slotCount) {
		return Math.abs(key.hashCode()) % slotCount;
	}

	/**
	 * Helper method for adding entry to the table (used by put and reAllocate
	 * methods).
	 *
	 * @param inEntry
	 *            the entry to add
	 * @param table
	 *            the table to add to
	 * @param updateMemberVar
	 *            if true, will update member vars (size and modCount).
	 */
	private void addToTable(TableEntry<K, V> inEntry, TableEntry<K, V>[] table, boolean updateMemberVar) {
		// copy entry
		TableEntry<K, V> entry = new TableEntry<>(inEntry.getKey(), inEntry.getValue(), null);

		int slot = getSlot(entry.key, table.length);

		if (table[slot] == null) {
			table[slot] = entry;
			if (updateMemberVar) {
				size++;
				modCount++;
			}
			return;
		}
		TableEntry<K, V> currentEntry = table[slot];

		do {
			if (currentEntry.getKey().equals(entry.getKey())) {
				// just update the value
				currentEntry.setValue(entry.getValue());

				// don't update modCount
				return;
			}

			if (currentEntry.next != null) {
				currentEntry = currentEntry.next;
			} else
				break;
		} while (true);

		currentEntry.next = entry;

		if (updateMemberVar) {
			size++;
			modCount++;
		}
	}

	/**
	 * Removes the element with the given key from the table.
	 *
	 * @param key
	 *            the key of the element to remove.
	 */
	public void remove(K key) {
		if (key == null) {
			return;
		}
		int slot = Math.abs(key.hashCode()) % this.slotCount;
		TableEntry<K, V> currentEntry = this.table[slot];

		// first entry
		if (currentEntry.getKey().equals(key)) {
			this.table[slot] = currentEntry.next;
			this.size--;
			this.modCount++;
			return;
		}
		// iterate through the slot
		while (currentEntry.next != null) {
			if (currentEntry.next.getKey().equals(key)) {
				currentEntry.next = currentEntry.next.next;
				this.size--;
				this.modCount++;
				return;
			}

			currentEntry = currentEntry.next;
		}
	}

	/**
	 * Gets the element from the table
	 *
	 * @param key
	 *            the key of the element to get from the table.
	 * @return the v
	 */
	@SuppressWarnings("unchecked")
	public V get(K key) {
		return (V) slotIterator(key, true);
	}

	/**
	 * Returns true if the table contains the key.
	 *
	 * @param key
	 *            the key
	 * @return true if the table contains the key, false otherwise.
	 */
	public boolean containsKey(K key) {
		return (boolean) slotIterator(key, false);
	}

	/**
	 * Slot iterator. Iterates through a slot looking for the given key.
	 *
	 * @param key
	 *            the key to look for.
	 * @param returnValue
	 *            the return value
	 * @return the object
	 */
	private Object slotIterator(K key, boolean returnValue) {
		if (key == null) {
			if (returnValue) {
				return null;
			} else {
				return false;
			}
		}

		int pretinac = Math.abs(key.hashCode()) % this.slotCount;
		TableEntry<K, V> currentEntry = this.table[pretinac];

		// iterate through the slot
		while (currentEntry != null) {
			if (currentEntry.getKey().equals(key)) {
				if (returnValue) {
					return currentEntry.getValue();
				} else {
					return true;
				}
			}

			currentEntry = currentEntry.next;
		}

		if (returnValue) {
			return null;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if the table contains the value.
	 *
	 * @param value
	 *            the value
	 * @return true if the table contains the value, false otherwise.
	 */
	public boolean containsValue(V value) {
		for (TableEntry<K, V> entry : this) {
			if (entry != null) {
				if (value == null) {
					if (entry.getValue() == null) {
						return true;
					}
				} else if (entry.getValue() != null && entry.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the table is empty.
	 *
	 * @return true if the table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return (this.size == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int i = 0;
		for (TableEntry<K, V> entry : this) {
			if (i++ > 0)
				sb.append(", ");
			sb.append("" + entry.getKey() + "=" + entry.getValue() + "");
		}
		sb.append(']');

		return sb.toString();
	}

	/**
	 * Clears the table.
	 */
	public void clear() {
		for (int i = 0; i < slotCount; i++) {
			table[i] = null;
		}
		size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	// iterator
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Iterator Implementation
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {

		/** The current count. */
		private int currentCount;

		/** The current slot. */
		private int currentSlot;

		/** The cached modCount. */
		private int cachedModCount;

		/** The current entry. */
		private TableEntry<K, V> currentEntry = null;

		/** The previous entry. */
		private TableEntry<K, V> previousEntry = null;

		/** Whether the previously gathered element was removed. */
		private boolean removed = true;

		/**
		 * Instantiates a new iterator implementation.
		 */
		public IteratorImpl() {
			super();
			this.cachedModCount = modCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if (modCount != cachedModCount) {
				throw new ConcurrentModificationException();
			}

			return currentCount < size;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements");
			}

			previousEntry = currentEntry;

			if (currentEntry == null) {
				currentEntry = findNextSlot();
			} else {
				currentEntry = currentEntry.next;

				if (currentEntry == null) {
					currentEntry = findNextSlot();
				}
			}

			currentCount++;
			removed = false;

			return currentEntry;
		}

		/**
		 * Finds the next slot.
		 *
		 * @return the next slot
		 */
		private TableEntry<K, V> findNextSlot() {
			previousEntry = null;
			TableEntry<K, V> element = null;
			while (element == null) {
				element = table[currentSlot++];
			}

			return element;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			if (removed) {
				throw new IllegalStateException("No element to remove.");
			}

			if (previousEntry == null) {
				// first element in slot
				if (currentSlot - 1 < 0) {
					throw new IllegalStateException("Unexpected: no element to remove.");
				}
				table[currentSlot - 1] = currentEntry.next;
			} else {
				previousEntry.next = currentEntry.next;
			}
			currentEntry = previousEntry;

			removed = true;
			size--;
		}
	}

	/**
	 * The Class TableEntry.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	public static class TableEntry<K, V> {

		/** The key. */
		private final K key;

		/** The value. */
		private V value;

		/** The next element in slot (in case of overflow). */
		TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @param next
		 *            the next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Key cannot be null.");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return this.value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value
		 *            the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return (String) this.value;
		}
	}
}
