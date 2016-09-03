package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * The implementation of Computer interface.
 *
 * @author Juraj Juričić
 */
public class ComputerImpl implements Computer {
	
	/** The computer memory object. */
	private Memory memory;
	
	/** The computer registers. */
	private Registers registers;
	
	/**
	 * Instantiates a new Computer with the given memory size and register count.
	 *
	 * @param memorySize the size of memory
	 * @param regsLen the register count
	 */
	public ComputerImpl(int memorySize, int regsLen){
		this.memory = new MemoryImpl(memorySize);
		this.registers = new RegistersImpl(regsLen);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
