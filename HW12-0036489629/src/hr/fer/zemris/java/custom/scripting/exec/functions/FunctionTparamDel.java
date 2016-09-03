package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function tParamDel. This function allows removing temporary parameters.
 *
 * @author Juraj Juričić
 */
public class FunctionTparamDel implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		String name = stack.pop().toString();
		rcx.removeTemporaryParameter(name);
	}
	
}
