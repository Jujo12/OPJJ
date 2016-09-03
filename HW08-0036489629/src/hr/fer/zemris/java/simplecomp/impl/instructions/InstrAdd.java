package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Add instruction. add rx, ry, rz : rx <- ry + rz
 *
 * @author Juraj Juričić
 */
public class InstrAdd implements Instruction {
	
	/** Destination register. */
	private int registerIndexX;
	
	/** Source register 1. */
	private int registerIndexY;
	
	/** Source register 2. */
	private int registerIndexZ;

	/**
	 * Instantiates a new add instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		
		for(int i = 0; i < 2; i++){
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument "+(i+1)+"!");
			}
		}

		this.registerIndexX = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndexY = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.registerIndexZ = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		int y = (int) computer.getRegisters().getRegisterValue(registerIndexY);
		int z = (int) computer.getRegisters().getRegisterValue(registerIndexZ);
		
		computer.getRegisters().setRegisterValue(registerIndexX, y+z);
		return false;
	}
}