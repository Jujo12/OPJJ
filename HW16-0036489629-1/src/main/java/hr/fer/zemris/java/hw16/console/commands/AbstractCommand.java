package hr.fer.zemris.java.hw16.console.commands;

import hr.fer.zemris.java.hw16.console.ConsoleContext;

/**
 * The abstract class for commands that provides console command funcitonality.
 *
 * @author Juraj Juričić
 */
public abstract class AbstractCommand {
	
	/** The console context. */
	protected ConsoleContext consoleContext;
	
	/**
	 * Instantiates a new abstract command.
	 *
	 * @param consoleContext the console context
	 */
	public AbstractCommand(ConsoleContext consoleContext){
		this.consoleContext = consoleContext;
	}

	/**
	 * Executes the command.
	 *
	 * @param line the arguments provided with the command (without the name itself)
	 */
	public abstract void execute(String line);
}
