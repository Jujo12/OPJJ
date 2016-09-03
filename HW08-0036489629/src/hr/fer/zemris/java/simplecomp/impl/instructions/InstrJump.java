package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Jump instruction. jump addr : px <- addr
 *
 * @author Juraj Juričić
 */
public class InstrJump implements Instruction {
	
	/** The target location. */
	private int location;

	/**
	 * Instantiates a new jump instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.location = (int) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}
}