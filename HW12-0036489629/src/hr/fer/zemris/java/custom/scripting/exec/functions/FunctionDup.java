package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function dup. This function duplicates
 * the value located at the top of the stack.
 *
 * @author Juraj Juričić
 */
public class FunctionDup implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		Object x = stack.pop();

		stack.push(x);
		stack.push(x);
	}

}
