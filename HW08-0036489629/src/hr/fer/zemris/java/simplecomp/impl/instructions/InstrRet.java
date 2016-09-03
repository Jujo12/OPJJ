package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.ComputerUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Ret instruction. Accepts no arguments. Returns after the call program call.
 *
 * @author Juraj Juričić
 */
public class InstrRet implements Instruction {
	
	/**
	 * Instantiates a new call instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments!");
		}
	}

	public boolean execute(Computer computer) {
		int newPc = (int) ComputerUtil.stackPop(computer);

		computer.getRegisters().setProgramCounter(newPc);
		return false;
	}
}