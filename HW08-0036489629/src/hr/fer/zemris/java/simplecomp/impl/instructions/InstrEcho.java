package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Echo instruction. echo rx : prints the content of registry rx to the standard
 * output.
 *
 * @author Juraj Juričić
 */
public class InstrEcho implements Instruction {
	
	/** The register index. */
	private int registerIndex;
	
	/** True if addressing is indirect. */
	private boolean isIndirect;
	
	/** The indirect-addressing offset. */
	private int offset;

	/**
	 * Instantiates a new echo instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		int index = (Integer) arguments.get(0).getValue();

		if (RegisterUtil.isIndirect(index)) {
			isIndirect = true;
		}

		this.registerIndex = RegisterUtil.getRegisterIndex(index);
	}

	public boolean execute(Computer computer) {
		Object reg = computer.getRegisters().getRegisterValue(registerIndex);
		Object value;

		if (isIndirect) {
			value = computer.getMemory().getLocation(
					(int) reg + RegisterUtil.getRegisterOffset(offset));
		} else {
			value = reg;
		}

		System.out.print(value.toString());
		return false;
	}
}
