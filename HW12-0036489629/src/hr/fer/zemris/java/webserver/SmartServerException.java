package hr.fer.zemris.java.webserver;

/**
 * The runtime exception thrown by {@link SmartHttpServer} in case of an error.
 *
 * @author Juraj Juričić
 */
public class SmartServerException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3110139048750317115L;

	/**
	 * Instantiates a new smart server exception. Used for rethrowing other exceptions.
	 *
	 * @param e the e
	 */
	public SmartServerException(Exception e) {
		super(e);
	}
	
	/**
	 * Instantiates a new smart server exception.
	 *
	 * @param message the message
	 */
	public SmartServerException(String message){
		super(message);
	}
}
