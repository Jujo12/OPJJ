package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function setMimeType. This function allows setting MIME type.
 *
 * @author Juraj Juričić
 */
public class FunctionSetMimeType implements SmartScriptFunction {

	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		String type = stack.pop().toString();
		rcx.setMimeType(type);
	}

}
