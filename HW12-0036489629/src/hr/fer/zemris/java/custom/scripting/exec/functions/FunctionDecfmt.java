package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function decfmt. This function allows
 * formatting of decimal numbers.
 *
 * @author Juraj Juričić
 */
public class FunctionDecfmt implements SmartScriptFunction {
	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		String format = stack.pop().toString();
		Object value = stack.pop();

		DecimalFormat decFormat = new DecimalFormat(format);
		stack.push(decFormat.format(value));
	}
}
