package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.ComputerUtil;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Pop instruction. pop rx : pops the value from the top of the stack and saves
 * it into register rx.
 *
 * @author Juraj Juričić
 */
public class InstrPop implements Instruction {

	/** The register rx index. */
	private int registerIndex;

	/**
	 * Instantiates a new pop instruction with the given arguments.
	 *
	 * @param arguments
	 *            the arguments
	 */
	public InstrPop(List<InstructionArgument> arguments) {
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
		Object value = ComputerUtil.stackPop(computer);

		computer.getRegisters().setRegisterValue(registerIndex, value);

		return false;
	}
}