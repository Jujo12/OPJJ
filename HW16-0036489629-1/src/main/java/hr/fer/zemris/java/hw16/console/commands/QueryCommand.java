package hr.fer.zemris.java.hw16.console.commands;

import java.util.StringJoiner;

import hr.fer.zemris.java.hw16.console.ConsoleContext;

/**
 * The command queries the search engine for the given words. Accepts n
 * arguments: words to search the documents for.
 *
 * @author Juraj Juričić
 */
public class QueryCommand extends AbstractCommand {
	
	/**
	 * Instantiates a new query command.
	 *
	 * @param consoleContext the console context
	 */
	public QueryCommand(ConsoleContext consoleContext) {
		super(consoleContext);
	}

	@Override
	public void execute(String line) {
		String query = line;

		String[] queryArr = consoleContext.getSearchEngine().splitQuery(query,
				true);
		StringJoiner sj = new StringJoiner(", ");
		for (String s : queryArr) {
			sj.add(s);
		}

		System.out.println("Query is: [" + sj.toString() + "]");

		consoleContext.getSearchEngine().search(queryArr);
		consoleContext.getSearchEngine().printResults(10);
	}
}
