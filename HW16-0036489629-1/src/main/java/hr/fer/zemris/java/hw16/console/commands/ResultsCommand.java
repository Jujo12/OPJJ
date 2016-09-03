package hr.fer.zemris.java.hw16.console.commands;

import hr.fer.zemris.java.hw16.console.ConsoleContext;

/**
 * The command outputs the search results to the console. Accepts no arguments.
 *
 * @author Juraj Juričić
 */
public class ResultsCommand extends AbstractCommand{
	
	/**
	 * Instantiates a new results command.
	 *
	 * @param consoleContext the console context
	 */
	public ResultsCommand(ConsoleContext consoleContext){
		super(consoleContext);
	}

	public void execute(String line){
		consoleContext.getSearchEngine().printResults(10);
	}
}
