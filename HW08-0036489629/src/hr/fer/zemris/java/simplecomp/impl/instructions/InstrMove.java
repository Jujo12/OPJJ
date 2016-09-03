package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Move instruction.<br>
 * move rx, ry : rx <- ry<br>
 * move rx, num : rx <- num ;e.g. move r7, 24 => will load number 24 into register r7<br>
 * move [rx+o1], [ry+o2]<br>
 * ... 
 *
 * @author Juraj Juričić
 */
public class InstrMove implements Instruction {

	/**
	 * Type of target. True if target is indirect address, false if
	 * target is a register.
	 */
	private boolean targetType;
	
	/** The target index. */
	private int target;
	
	/** The target offset, if targetType is true. */
	private int targetOffset;

	/**
	 * Type of source.<br>
	 * 0 - number<br>
	 * 1 - indirect address<br>
	 * 2 - register
	 */
	private int sourceType;
	
	/** The source address / index. */
	private int source;
	
	/** The source offset, if sourceType is 1. */
	private int sourceOffset;

	/**
	 * Instantiates a new move instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		// target (first argument)
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		int index = (int) arguments.get(0).getValue();
		target = RegisterUtil.getRegisterIndex(index);
		if (RegisterUtil.isIndirect(index)) {
			targetType = true;
			targetOffset = RegisterUtil.getRegisterOffset(index);
		} else {
			targetType = false;
		}

		if (arguments.get(1).isNumber()) {
			source = (int) arguments.get(1).getValue();
			sourceType = 0;
		}else if (arguments.get(1).isRegister()){
			index = (int) arguments.get(1).getValue();
			if (RegisterUtil.isIndirect(index)){
				sourceType = 1;
				sourceOffset = RegisterUtil.getRegisterOffset(index);
				source = RegisterUtil.getRegisterIndex(index);
			}else{
				sourceType = 2;
				source = RegisterUtil.getRegisterIndex(index);
			}
		}else{
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
	}

	public boolean execute(Computer computer) {
		Object value = null;
		switch (sourceType){
		case 0:
			//number
			value = source;
			break;
		case 1:
			//indirect address
			value = computer.getMemory().getLocation((int)computer.getRegisters().getRegisterValue(source) + sourceOffset); 
			break;
		case 2:
			//register
			value = computer.getRegisters().getRegisterValue(source);
			break;
		}
		
		if (targetType){
			//indirect address
			computer.getMemory().setLocation((int)computer.getRegisters().getRegisterValue(target) + targetOffset, value);
		}else{
			//register
			computer.getRegisters().setRegisterValue(target, value);
		}

		return false;
	}
}