package hr.fer.zemris.java.cstr;

/**
 * The Exception CStringIndexOutOfBoundsException. Thrown when the provided
 * CString index is out of legal range.
 * 
 * @author Juraj Juričić
 */
public class CStringIndexOutOfBoundsException extends IndexOutOfBoundsException {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 6903939427619536881L;

	/**
	 * Instantiates a new CString index out of bounds exception without the
	 * message.
	 */
	public CStringIndexOutOfBoundsException() {
		this("");
	}

	/**
	 * Instantiates a new c string index out of bounds exception with the
	 * provided message.
	 *
	 * @param message
	 *            the message
	 */
	public CStringIndexOutOfBoundsException(String message) {
		super(message);
	}
}
