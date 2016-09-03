package hr.fer.zemris.java.custom.collections;

/**
 * The Collection which represents some general collection of objects
 * 
 * @author JJ
 */
public class Collection {

	/**
	 * Default constructor for the class.
	 */
	protected Collection(){
		
	}
	
	/**
	 * Returns true if collection contains no objects and false otherwise.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return (this.size() == 0);

	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 *
	 * @return the number of currently stored objects.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 *
	 * @param value
	 *            the value to add to the collection
	 */
	public void add(Object value) {
		return;
	}

	/**
	 * Returns true only if the collection contains given value. Determined by
	 * equals method. It is OK to ask if collection contains null.
	 *
	 * @param value
	 *            the value to check for in the collection
	 * @return true if the collection contains the searched value, false
	 *         otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it.
	 *
	 * @param value
	 *            the value to remove from the collection
	 * @return true, if successful
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections,
	 * fills it with collection content and returns the array. This method never
	 * returns null.
	 *
	 * @return the array of collection objects
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 *
	 * @param processor
	 *            the processor
	 */
	public void forEach(Processor processor) {
		return;
	}

	/**
	 * Method adds into itself all elements from given collection. This other
	 * collection remains unchanged.
	 *
	 * @param other
	 *            the other collection to copy the elements from
	 */
	public void addAll(Collection other) {
		/*
		 * Implement it here to define a local processor class whose method
		 * process will add each item into current collection by calling method
		 * add, and then call forEach on other collection with this processor as
		 * argument. You can define this new class directly in method addAll:
		 * such classes are called local classes.
		 */
		class AddAllProcessor implements Processor {
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new AddAllProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		return;
	}
}
