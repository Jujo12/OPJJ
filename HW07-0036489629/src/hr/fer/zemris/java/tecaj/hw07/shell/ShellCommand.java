package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;

/**
 * MyShell commands implement this interface.
 * 
 * @author Juraj Juričić
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command.
	 *
	 * @param env
	 *            the environment that called the command
	 * @param args
	 *            the command arguments (everthing input after the command name)
	 * @return the shell status (state after the command is executed)
	 */
	ShellStatus executeCommand(Environment env, String args);

	/**
	 * Gets the all-lowercase command name.
	 *
	 * @return the command name
	 */
	String getCommandName();

	/**
	 * Gets the command description.
	 *
	 * @return the command description
	 */
	List<String> getCommandDescription();
}
