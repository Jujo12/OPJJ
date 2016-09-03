package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Load instruction. load rx, addr : loads the value located at the memory
 * address addr into the register rx.
 *
 * @author Juraj Juričić
 */
public class InstrLoad implements Instruction {
	
	/** The register rx index. */
	private int registerIndex;
	
	/** The memory location addr. */
	private int memoryLocation;

	/**
	 * Instantiates a new load instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister() || RegisterUtil
				.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException(
					"Typee mismatch for argument 2!");
		}

		this.registerIndex = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.memoryLocation = (Integer) arguments.get(1).getValue();
	}

	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryLocation);

		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}
}