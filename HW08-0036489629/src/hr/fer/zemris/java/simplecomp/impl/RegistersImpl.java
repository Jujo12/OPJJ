/*
 * 
 */
package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * The implementation of Registers interface.
 *
 * @author Juraj Juričić
 */
public class RegistersImpl implements Registers {
	
	/** The fixed-size registers array of objects. */
	private Object[] registers;
	
	/** The PC register. */
	private int programCounter;
	
	/** The single-bit flag register. */
	private boolean flag;

	/**
	 * Instantiates a new registers object with the given size.
	 *
	 * @param regsLen the count of registers (not including the PC and flag registers)
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < 1){
			throw new IllegalArgumentException("RegsLen has to be a positive integer.");
		}
		
		this.registers = new Object[regsLen];
	}
	
	@Override
	public Object getRegisterValue(int index) {
		checkLegalIndex(index);
		
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		checkLegalIndex(index);
		
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}
	
	/**
	 * Checks if the given argument is a legal register index.
	 *
	 * @param index the index
	 * @throws IndexOutOfBoundsException Thrown if the given index is not within the registers' bounds.
	 */
	private void checkLegalIndex(int index) throws IndexOutOfBoundsException{
		if (index >= 0 && index < registers.length){
			return;
		}
		
		throw new IndexOutOfBoundsException("Register index does not exist.");
	}
}
