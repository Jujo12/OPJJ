package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Exit command. Quits the shell.
 *
 * @author Juraj Juričić
 */
public class ExitShellCommand implements ShellCommand {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#executeCommand(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 0);

		return ShellStatus.TERMINATE;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Summons a ghoul that destroys the shell instance.");

		return description;
	}

}
