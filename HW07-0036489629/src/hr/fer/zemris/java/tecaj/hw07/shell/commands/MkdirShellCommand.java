package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Mkdir command. Takes a single argument: directory name, and creates the
 * appropriate directory structure.
 *
 * @author Juraj Juričić
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1);

		File dir = new File(arguments.get(0).trim());
		if (!dir.mkdirs()) {
			throw new MyShellException("Could not create directory structure.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add(
				"Takes a single argument: directory name, and creates the appropriate directory structure.");

		return description;
	}

}
