package hr.fer.zemris.java.hw14.dao;

/**
 * The exception thrown when {@link DAO} encounters a problem.
 */
public class DAOException extends RuntimeException {

	/**
	 * The constant serial version ID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new DAO Exception.
	 */
	public DAOException() {
	}

	/**
	 * Instantiates a new DAO Exception.
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new DAO Exception.
	 *
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO Exception.
	 *
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new DAO Exception.
	 *
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}