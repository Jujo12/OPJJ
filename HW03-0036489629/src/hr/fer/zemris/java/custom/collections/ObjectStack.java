package hr.fer.zemris.java.custom.collections;

/**
 * A stack Class. Represents a stack-like object collection.
 * 
 * @author JJ
 *
 */
public class ObjectStack {
	/**
	 * The local ArrayIndexedCollection (adaptee)
	 */
	private ArrayIndexedCollection array;
	
	/**
	 * Instantiates a new object stack.
	 */
	public ObjectStack(){
		array = new ArrayIndexedCollection();
	}

	/**
	 * Returns true if the stack contains no objects and false otherwise.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Returns the number of currently stored objects on the stack
	 *
	 * @return the number of currently stored objects.
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Pushes given value onto the stack. The value cannot be null.
	 * 
	 * @param value
	 *            the value to push onto the stack
	 */
	public void push(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("The value cannot be null.");
		}
		array.add(value);
	}

	/**
	 * Removes last value pushed on stack from stack and returns it. If the
	 * stack is empty when method pop is called, throws EmptyStackException.
	 * 
	 * @return the last value pushed on stack
	 */
	public Object pop() {
		Object value = peek();
		array.remove(size() - 1);

		return value;
	}

	/**
	 * Returns last element placed on stack. Does not delete the element from
	 * stack. If the stack is empty when method peek is called, throws
	 * EmptyStackException.
	 * 
	 * @return the last value pushed on stack
	 */
	public Object peek() {
		int size = size();
		
		if (size == 0){
			throw new EmptyStackException("The Stack is empty.");
		}
		return array.get(size() - 1);
	}
	
	/**
	 * Removes all elements from stack. 
	 */
	public void clear(){
		array.clear();
	}
}
