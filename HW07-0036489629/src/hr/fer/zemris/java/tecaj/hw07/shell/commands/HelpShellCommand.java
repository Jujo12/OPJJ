package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Help command. If called with no arguments, lists names of all supported
 * commands. If started with a single argument, prints the name and the
 * description of selected command. Throws a MyShellException if the command is
 * not found.
 * 
 * @author Juraj Juričić
 *
 */
public class HelpShellCommand implements ShellCommand {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#executeCommand(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		// arguments
		List<String> arguments = MyShell.spaceSplitter(args);

		MyShell.checkCommandArguments(arguments, 0, 1);

		try {
			if (arguments.size() == 0) {
				for (ShellCommand i : env.commands()) {
					env.writeln(i.getCommandName());
				}
			} else {
				String commandName = arguments.get(0);

				ShellCommand command = env.commandsMap().get(commandName);
				if (command == null) {
					throw new MyShellException(
							"Unknown command: " + commandName);
				}

				env.writeln(command.getCommandName() + ":");
				for (String line : command.getCommandDescription()) {
					env.write("  ");
					env.writeln(line);
				}
			}
		} catch (IOException e) {
			throw new MyShellException(
					"An error occured while trying to write to output.");
		}
		return ShellStatus.CONTINUE;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Accepts 0 or 1 argument.");
		description.add(
				"If called with 0 arguments, shows all available commands.");
		description.add(
				"If called with 1 argument (command), outputs the description of the available command.");

		return description;
	}

}
