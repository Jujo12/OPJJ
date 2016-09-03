package hr.fer.zemris.java.webserver;

/**
 * The Interface that represents a single worker. Workers can be used by
 * configuration or convention-over-configuration.
 *
 * @author Juraj Juričić
 */
public interface IWebWorker {
	
	/**
	 * The method called to process the request.
	 *
	 * @param context the request context, to which output data should be written.
	 */
	public void processRequest(RequestContext context);
}