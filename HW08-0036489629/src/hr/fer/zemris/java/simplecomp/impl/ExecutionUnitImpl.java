package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * The implementation of ExecutionUnit interface.
 *
 * @author Juraj Juričić
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Registers registers = computer.getRegisters();
		Memory memory = computer.getMemory();
		registers.setProgramCounter(0);
		try{
			while(true){
				Instruction instruction = (Instruction) memory.getLocation(registers.getProgramCounter());
				registers.incrementProgramCounter();
				
				if (instruction.execute(computer)){
					break;
				}
			}
		}catch(Exception e){
			System.err.println("Error on PC " + registers.getProgramCounter());
			e.printStackTrace(System.err);
		}
		
		return true;
	}

}
