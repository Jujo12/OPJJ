package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function swap. This function switches
 * the places of two variables placed at the top of the stack.
 *
 * @author Juraj Juričić
 */
public class FunctionSwap implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		Object a = stack.pop();
		Object b = stack.pop();

		stack.push(a);
		stack.push(b);
	}
}
