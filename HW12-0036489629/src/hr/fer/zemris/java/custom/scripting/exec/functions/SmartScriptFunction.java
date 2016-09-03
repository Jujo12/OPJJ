package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The smartscript function interface used for executing function elements. Each
 * class that implements this interface should provide a single method that is
 * executed for the given context.
 *
 * @author Juraj Juričić
 */
public interface SmartScriptFunction {

	/**
	 * Performs the function. Outputs possible data to given request context's
	 * output stream. Uses the given stack for context.
	 *
	 * @param rcx
	 *            the request context to output the data to
	 * @param stack
	 *            the stack
	 */
	public void calculate(RequestContext rcx, Stack<Object> stack);
}
