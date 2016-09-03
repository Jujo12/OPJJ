package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.io.IOException;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class that offers functionality of function sin. This function calculates the sine value of a number.
 *
 * @author Juraj Juričić
 */
public class FunctionSin implements SmartScriptFunction {
	
	@Override
	public void calculate(RequestContext rcx, Stack<Object> stack) {
		try{
			try{
				Object value = stack.pop();
				Double rez = Math.sin(Math.toRadians(Double.valueOf(value.toString())));
				
				stack.push(rez);
			}catch(NumberFormatException e){
				rcx.write("Sin value cannot be parsed.");
			}
		}catch(IOException e){
			System.err.println("An error occured while writing to output: "
					+ e.getMessage());
		}
	}
}
