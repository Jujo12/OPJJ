package hr.fer.zemris.java.webserver;

/**
 * The exception thrown by HTTP server if the user tries to modify the header info after the headers have already been sent.
 *
 * @author Juraj Juričić
 */
public class HeadersAlreadySentException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4542936035162135716L;

	/**
	 * Instantiates a new headers already sent exception.
	 */
	public HeadersAlreadySentException() {
		super();
	}
	
	/**
	 * Instantiates a new headers already sent exception.
	 *
	 * @param message the message
	 */
	public HeadersAlreadySentException(String message){
		super(message);
	}
	
	/**
	 * Instantiates a new headers already sent exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public HeadersAlreadySentException(String message, Throwable cause) {
        super(message, cause);
    }
	
	/**
	 * Instantiates a new headers already sent exception.
	 *
	 * @param cause the cause
	 */
	public HeadersAlreadySentException(Throwable cause) {
        super(cause);
    }
}
