package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * TestEquals instruction. testEquals rx, ry : flag=1, if content of rx is equal
 * to content of ry.
 *
 * @author Juraj Juričić
 */
public class InstrTestEquals implements Instruction {

	/** The register rx. */
	private int registerIndexX;

	/** The register ry. */
	private int registerIndexY;

	/**
	 * Instantiates a new testEquals instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}

		for (int i = 0; i < 1; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + (i + 1) + "!");
			}
		}

		this.registerIndexX = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndexY = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	public boolean execute(Computer computer) {
		boolean equal = computer.getRegisters().getRegisterValue(registerIndexX)
				.equals(computer.getRegisters()
						.getRegisterValue(registerIndexY));

		computer.getRegisters().setFlag(equal);
		return false;
	}
}