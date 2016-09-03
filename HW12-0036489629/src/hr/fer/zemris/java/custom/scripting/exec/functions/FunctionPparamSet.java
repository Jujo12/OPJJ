package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function pparamSet. This function allows setting of permanent parameters.
 *
 * @author Juraj Juričić
 */
public class FunctionPparamSet implements SmartScriptFunction{

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		String name = stack.pop().toString();
		String value = stack.pop().toString();
		
		rcx.setPersistentParameter(name, value);
	}

}
