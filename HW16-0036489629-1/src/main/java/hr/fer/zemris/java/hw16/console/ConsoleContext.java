package hr.fer.zemris.java.hw16.console;

import hr.fer.zemris.java.hw16.trazilica.SearchEngine;

/**
 * The context for search engine console. Wraps the related {@link SearchEngine} object.
 *
 * @author Juraj Juričić
 */
public class ConsoleContext {
	
	/** The search engine. */
	private SearchEngine searchEngine;

	/**
	 * Instantiates a new console context.
	 *
	 * @param engine the engine
	 */
	public ConsoleContext(SearchEngine engine){
		this.searchEngine = engine;
	}

	/**
	 * Gets the search engine.
	 *
	 * @return the search engine
	 */
	public SearchEngine getSearchEngine(){
		return searchEngine;
	}
}
