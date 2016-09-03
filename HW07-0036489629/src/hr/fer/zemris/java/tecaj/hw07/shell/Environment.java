package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Map;

/**
 * The Interface Environment.
 *
 * @author Juraj Juričić
 */
public interface Environment {
	
	/**
	 * Reads line(s) from the input.
	 *
	 * @return the read string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String readLine() throws IOException;

	/**
	 * Writes the text to environment's output.
	 *
	 * @param text the text to write
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes the text to environment's output and enters a new line.
	 *
	 * @param text the text to write
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void writeln(String text) throws IOException;

	/**
	 * Returns an Iterable object containing available commands.
	 *
	 * @return the commands.
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns the map of commands (name, ShellCommand).
	 *
	 * @return the map
	 */
	Map<String, ShellCommand> commandsMap();

	/**
	 * Gets the multiline symbol.
	 *
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol.
	 *
	 * @param symbol the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets the prompt symbol.
	 *
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 *
	 * @param symbol the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets the morelines symbol.
	 *
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
