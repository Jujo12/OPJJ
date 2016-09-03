package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Decrement instruction. decrement rx : rx <- rx - 1
 *
 * @author Juraj Juričić
 */
public class InstrDecrement implements Instruction {
	
	/** The register index. */
	private int registerIndex;

	/**
	 * Instantiates a new decrement instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		
		if (!arguments.get(0).isRegister() || RegisterUtil
				.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	public boolean execute(Computer computer) {
		int value = (int) computer.getRegisters().getRegisterValue(registerIndex);
		
		computer.getRegisters().setRegisterValue(registerIndex, value-1);
		return false;
	}
}