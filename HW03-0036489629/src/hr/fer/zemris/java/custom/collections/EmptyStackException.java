package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that the stack is empty.
 * 
 * @author JJ
 *
 */
@SuppressWarnings("serial")
public class EmptyStackException extends RuntimeException {

	/**
	 * Instantiates EmptyStackException with the provided message
	 * 
	 * @param string the message
	 */
	public EmptyStackException(String string) {
		super(string);
	}
	
}
