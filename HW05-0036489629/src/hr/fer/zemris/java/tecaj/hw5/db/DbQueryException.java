package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The Runtime exception DbQueryException. Thrown in case of a query error or
 * database structural error.
 * 
 * @author Juraj Juričić
 */
public class DbQueryException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7988583453850844982L;

	/**
	 * Instantiates a new DbQueryException.
	 *
	 * @param string
	 *            the string
	 */
	public DbQueryException(String string) {
		super(string);
	}

	/**
	 * Instantiates a new DbQueryException.
	 */
	public DbQueryException() {
		super();
	}

}
