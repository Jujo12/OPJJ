package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Command-line application. Accepts a single command-line argument: expression
 * which should be evaluated. Expression must be in postfix representation.
 * 
 * @author JJ
 *
 */
public class StackDemo {

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		
		String[] expression;
		try{
			expression = args[0].split("\\s+");
			System.out.println(evaluate(expression));
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Expression needed.");
			System.exit(0);
		}catch(IllegalArgumentException | ArithmeticException e){
			System.err.println("Error: "+e.getMessage());
		}

	}
	
	/**
	 * Evaluates the expression.
	 * 
	 * @param expression The array of commands that form the expression.
	 * @return the integer the expression evaluates to
	 */
	private static int evaluate(String[] expression){
		ObjectStack stack = new ObjectStack();
		
		for (String command : expression) {
			if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/") || command.equals("%")){
				//pop two elements from stack, perform operation and push result back on stack
				Integer first = (Integer) stack.pop();
				Integer second = (Integer) stack.pop();
				
				int result = 0;
				switch(command){
					case "+":
						result = first+second;
						break;
					case "-":
						result = second-first;
						break;
					case "*":
						result = first*second;
						break;
					case "/":
						if (first == 0){
							throw new ArithmeticException("Division by zero.");
						}
						result = second/first;
						break;
					case "%":
						if (first == 0){
							throw new ArithmeticException("Division by zero.");
						}
						result = second%first;
						break;
				}
				stack.push(result);
			}else{
				//command is not an operator, try to convert to a number.
				Integer element;
				try {
					element = Integer.parseInt(command);
				} catch (NumberFormatException e) {
					//mask NFE as IAE
					throw new IllegalArgumentException("Unknown command: " + command);
				}
				
				stack.push(element);
			}
		}
		
		if (stack.size() != 1){
			throw new IllegalArgumentException("Invalid expression.");
		}
		return (Integer) stack.pop();
	}

}
