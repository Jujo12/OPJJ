package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Charsets command. Accepts no arguments. Lists names of all supported charsets
 * for this platform.
 * 
 * @author Juraj Juričić
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#executeCommand(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);

		MyShell.checkCommandArguments(arguments, 0, 1);

		Map<String, Charset> charsets = Charset.availableCharsets();

		for (Entry<String, Charset> charset : charsets.entrySet()) {
			try {
				env.writeln(charset.getKey());
			} catch (IOException e) {
				throw new MyShellException(
						"An error occured while trying to write to output.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Accepts no arguments.");
		description.add(
				"Lists names of all supported charsets for this platform.");

		return description;
	}

}
