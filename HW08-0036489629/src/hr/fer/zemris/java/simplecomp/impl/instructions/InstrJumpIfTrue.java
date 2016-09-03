package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * JumpIfTrue instruction. jumpIfTrue addr : px <- addr, but only if flag=1.
 *
 * @author Juraj Juričić
 */
public class InstrJumpIfTrue implements Instruction {
	
	/** The target location. */
	private int location;

	/**
	 * Instantiates a new jumpIfTrue instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.location = (int) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {
		boolean flag = computer.getRegisters().getFlag();

		if (flag) {
			computer.getRegisters().setProgramCounter(location);
		}
		return false;
	}
}