package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Halt instruction. Accepts no arguments. Stops the processor.
 *
 * @author Juraj Juričić
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Instantiates a new halt instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments!");
		}
	}

	public boolean execute(Computer computer) {
		return true;
	}
}