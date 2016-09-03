package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.ComputerUtil;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Push instruction. push rx : pushes the value located at register rx onto the
 * stack.
 *
 * @author Juraj Juričić
 */
public class InstrPush implements Instruction {

	/** The register rx index. */
	private int registerIndex;

	/**
	 * Instantiates a new push instruction with the given arguments.
	 *
	 * @param arguments
	 *            the arguments
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister() || RegisterUtil
				.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		ComputerUtil.stackPush(computer, value);

		return false;
	}
}