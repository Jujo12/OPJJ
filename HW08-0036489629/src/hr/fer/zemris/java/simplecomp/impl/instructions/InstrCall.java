package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.ComputerUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Call instruction. call addr : calls the program located at the given addr
 * address.
 *
 * @author Juraj Juričić
 */
public class InstrCall implements Instruction {
	
	/** The location. */
	private int location;

	/**
	 * Instantiates a new call instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.location = (int) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {
		int currentPc = computer.getRegisters().getProgramCounter();
		ComputerUtil.stackPush(computer, currentPc);

		computer.getRegisters().setProgramCounter(location);

		return false;
	}
}