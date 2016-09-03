package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * The implementation of Memory interface.
 *
 * @author Juraj Juričić
 */
public class MemoryImpl implements Memory {
	
	/** The fixed-size memory array of objects. */
	private Object[] memory;
	
	/**
	 * Instantiates a new memory object with the given size.
	 *
	 * @param size the size
	 */
	public MemoryImpl(int size){
		if (size < 1){
			throw new IllegalArgumentException("Size has to be a positive integer.");
		}
		this.memory = new Object[size];
	}
	
	@Override
	public void setLocation(int location, Object value) {
		checkLegalLocation(location);
		
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		checkLegalLocation(location);
		
		return memory[location];
	}
	
	/**
	 * Checks if the given argument is a legal location.
	 *
	 * @param location the location
	 * @throws IndexOutOfBoundsException Thrown if the given location is not within the memory bounds.
	 */
	private void checkLegalLocation(int location) throws IndexOutOfBoundsException{
		if (location >= 0 && location < memory.length){
			return;
		}
		
		throw new IndexOutOfBoundsException("Memory location does not exist.");
	}

}
