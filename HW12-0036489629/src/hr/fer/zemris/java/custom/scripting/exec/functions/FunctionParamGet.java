package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function paramGet. This function
 * allows getting of parameters.
 *
 * @author Juraj Juričić
 */
public class FunctionParamGet implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		Object dv = stack.pop();
		String name = stack.pop().toString();

		String value = rcx.getParameter(name);
		stack.push(value == null ? dv : value);
	}

}
