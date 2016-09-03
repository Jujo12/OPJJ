package hr.fer.zemris.java.tecaj.hw07.shell.commands;

/**
 * The MyShellException is thrown when an error that can be handled by MyShell
 * occurs. The MyShell should catch it and parse appropriately.
 *
 * @author Juraj Juričić
 */
public class MyShellException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -348285314486969604L;

	/**
	 * Instantiates a new MyShellException.
	 *
	 * @param message
	 *            the message
	 */
	public MyShellException(String message) {
		super(message);
	}
}
