package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Utility class for stack push and pop
 *
 * @author Juraj Juričić
 */
public class ComputerUtil {
	
	/**
	 * Pushes the value onto computer's stack.
	 *
	 * @param computer the computer
	 * @param value the value to push onto the stack.
	 */
	public static void stackPush(Computer computer, Object value){
		int topOfStack = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getMemory().setLocation(topOfStack, value);
		
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, topOfStack-1);
	}
	
	/**
	 * Pops the value from the computer's stack.
	 *
	 * @param computer the computer
	 * @return the object from the top of the stack
	 */
	public static Object stackPop(Computer computer){
		int stackAddr = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX) + 1;
		
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackAddr);
		
		return computer.getMemory().getLocation(stackAddr);
	}
}
