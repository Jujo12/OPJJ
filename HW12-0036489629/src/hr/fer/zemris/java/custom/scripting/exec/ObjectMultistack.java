package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The Class ObjectMultistack. Represents a multistack that allows the user to
 * store multiple values for the same key in a stack-like abstraction. The keys
 * are String objects, and values are wrapped in a {@link ValueWrapper}.
 *
 * @author Juraj Juričić
 */
public class ObjectMultistack {

	/** The stack map. */
	private Map<String, MultistackEntry> stack;

	/**
	 * Instantiates a new object multistack.
	 */
	public ObjectMultistack() {
		stack = new HashMap<>();
	}

	/**
	 * Pushes the element to the top of the stack with the given name.
	 *
	 * @param name
	 *            the name of the stack
	 * @param valueWrapper
	 *            the element to push
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		throwIfNullArgument(name, valueWrapper);

		MultistackEntry entry;
		MultistackEntry topOfStack = stack.get(name);

		if (topOfStack != null) {
			entry = new MultistackEntry(topOfStack, valueWrapper);
		} else {
			entry = new MultistackEntry(null, valueWrapper);
		}

		stack.put(name, entry);
	}

	/**
	 * Removes and returns the element from the top of the stack with the given
	 * name.
	 *
	 * @param name
	 *            the name of the stack
	 * @return the value from the top of the stack
	 */
	public ValueWrapper pop(String name) {
		throwIfNullArgument(name);

		MultistackEntry entry = stack.get(name);

		if (entry != null) {
			ValueWrapper value = entry.getValue();
			MultistackEntry nextEntry = entry.getNextEntry();
			if (nextEntry == null) {
				stack.remove(name);
			} else {
				stack.put(name, nextEntry);
			}

			return value;
		}

		throw new NoSuchElementException("Stack " + name + " is empty");
	}

	/**
	 * Returns the element at the top of the stack with the given name.
	 *
	 * @param name
	 *            the name of the stack
	 * @return the value at the top of the stack
	 */
	public ValueWrapper peek(String name) {
		throwIfNullArgument(name);

		MultistackEntry entry = stack.get(name);
		if (entry != null) {
			return entry.getValue();
		}

		throw new NoSuchElementException("Stack " + name + " is empty");
	}

	/**
	 * Checks if the stack with the given name is empty.
	 *
	 * @param name
	 *            the name of the stack
	 * @return true, if is empty
	 */
	public boolean isEmpty(String name) {
		throwIfNullArgument(name);

		return !stack.containsKey(name);
	}

	/**
	 * Throws {@link IllegalArgumentException} if any of the given arguments is
	 * a null pointer.
	 *
	 * @param objects
	 *            the objects to check for null pointers
	 */
	private static void throwIfNullArgument(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				throw new IllegalArgumentException("Cannot be null.");
			}
		}
	}

	/**
	 * The private Class MultistackEntry. Represents a single-linked list of
	 * entries in the stack of {@link ObjectMultistack}.
	 *
	 * @author Juraj Juričić
	 */
	private static class MultistackEntry {

		/** The next entry in the list. */
		private MultistackEntry nextEntry;

		/** The value stored in the entry. */
		private ValueWrapper value;

		/**
		 * Instantiates a new multistack entry.
		 *
		 * @param nextEntry
		 *            the next entry in the list
		 * @param value
		 *            the value to store in the entry
		 */
		public MultistackEntry(MultistackEntry nextEntry, ValueWrapper value) {
			this.value = value;
			this.nextEntry = nextEntry;
		}

		/**
		 * Gets the next entry in the list.
		 *
		 * @return the next entry
		 */
		public MultistackEntry getNextEntry() {
			return nextEntry;
		}

		/**
		 * Gets the value of entry
		 *
		 * @return the value
		 */
		public ValueWrapper getValue() {
			return value;
		}
	}
}
