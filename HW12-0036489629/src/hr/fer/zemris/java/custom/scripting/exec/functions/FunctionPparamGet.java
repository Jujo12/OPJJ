package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function pparamget. This function allows getting of permanent parameters.
 *
 * @author Juraj Juričić
 */
public class FunctionPparamGet implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		Object dv = stack.pop();
		String name = stack.pop().toString();
		
		String value = rcx.getPersistentParameter(name);
		stack.push(value == null ? dv : value);
	}

}
