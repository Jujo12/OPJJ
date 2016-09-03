package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Iinput instruction. iinput addr : waits for user input and saves the input to
 * memory location addr.
 *
 * @author Juraj Juričić
 */
public class InstrIinput implements Instruction {
	
	/** The target location. */
	private int location;

	/**
	 * Instantiates a new iinput instruction with the given arguments.
	 *
	 * @param arguments the arguments
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.location = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	public boolean execute(Computer computer) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
			String line = br.readLine();

			Integer value = Integer.parseInt(line);

			computer.getMemory().setLocation(location, value);
			computer.getRegisters().setFlag(true);
		} catch (NumberFormatException e) {
			computer.getRegisters().setFlag(false);
		} catch (IOException e) {
			System.out.println("x");
			throw new RuntimeException(e);
		}

		return false;
	}
}