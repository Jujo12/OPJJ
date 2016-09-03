package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function tParamSet. This function allows setting of temporary parameters.
 *
 * @author Juraj Juričić
 */
public class FunctionTparamSet implements SmartScriptFunction{

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		String name = stack.pop().toString();
		String value = stack.pop().toString();
		
		rcx.setTemporaryParameter(name, value);
	}

}
