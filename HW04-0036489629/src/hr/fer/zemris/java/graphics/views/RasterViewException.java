package hr.fer.zemris.java.graphics.views;

/**
 * RasterViewException, thrown in case of a Raster error. Detailed information documented when used.
 * 
 * @author Juraj Juričić
 *
 */
public class RasterViewException extends Exception {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7303709127663477612L;
	
	/**
	 * Instantiates a new raster view exception without the message.
	 */
	public RasterViewException(){ 
		super();
	}
	
	/**
	 * Instantiates a new raster view exception with the provided message.
	 *
	 * @param message the exception message.
	 */
	public RasterViewException(String message){ 
		super(message);
	}
}
