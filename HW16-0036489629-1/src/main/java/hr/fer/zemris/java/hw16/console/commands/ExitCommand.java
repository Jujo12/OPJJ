package hr.fer.zemris.java.hw16.console.commands;

import hr.fer.zemris.java.hw16.console.ConsoleContext;

/**
 * The command exits the console when called. Does not expect any arguments.
 *
 * @author Juraj Juričić
 */
public class ExitCommand extends AbstractCommand{
	
	/**
	 * Instantiates a new exit command.
	 *
	 * @param consoleContext the console context
	 */
	public ExitCommand(ConsoleContext consoleContext){
		super(consoleContext);
	}

	@Override
	public void execute(String line){
		System.exit(0);
	}
}
