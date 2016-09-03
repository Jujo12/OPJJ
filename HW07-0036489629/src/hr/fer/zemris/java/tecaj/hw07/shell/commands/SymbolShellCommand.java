package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The symbol command. Allows change or checking of PROMPT, MULTILINE, and MORELINES symbols.
 *
 * @author Juraj Juričić
 */
public class SymbolShellCommand implements ShellCommand {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#executeCommand(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1, 2);

		try {
			String code;
			Character symbol;

			switch (arguments.size()) {
			case 1:
				code = arguments.get(0);

				symbol = symbolGetter(code, env);
				if (symbol == null) {
					throw new MyShellException("Unknown symbol.");
				}

				env.writeln("Symbol for " + code.toUpperCase() + " is '"
						+ symbol + "'");
				break;
			case 2:
				code = arguments.get(0);
				Character newSymbol = arguments.get(1).charAt(0);

				symbol = symbolSetter(code, newSymbol, env);
				if (symbol == null) {
					throw new MyShellException("Unknown symbol.");
				}

				env.writeln(
						"Symbol for " + code.toUpperCase() + " changed from '"
								+ symbol + "' to '" + newSymbol + "'");
				break;
			}
		} catch (IOException e) {
			throw new MyShellException(
					"An error occured while writing to output.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Symbol getter.
	 *
	 * @param symbolCode the symbol code to get
	 * @param env the environment to get the symbol from
	 * @return the character; null if code is invalid.
	 */
	private Character symbolGetter(String symbolCode, Environment env) {
		switch (symbolCode.toLowerCase()) {
		case "prompt":
			return env.getPromptSymbol();
		case "morelines":
			return env.getMorelinesSymbol();
		case "multiline":
			return env.getMultilineSymbol();
		default:
			return null;
		}
	}

	/**
	 * Symbol setter.
	 *
	 * @param symbolCode the symbol code to set
	 * @param newSymbol the new symbol to set
	 * @param env the environment to set the symbol to
	 * @return the old symbol; null if code is invalid.
	 */
	private Character symbolSetter(String symbolCode, Character newSymbol,
			Environment env) {
		Character old;
		switch (symbolCode.toLowerCase()) {
		case "prompt":
			old = env.getPromptSymbol();
			env.setPromptSymbol(newSymbol);
			break;
		case "morelines":
			old = env.getMorelinesSymbol();
			env.setMorelinesSymbol(newSymbol);
			break;
		case "multiline":
			old = env.getMultilineSymbol();
			env.setMultilineSymbol(newSymbol);
			break;
		default:
			return null;
		}

		return old;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add(
				"Allows changing of symbols for PROMPT, MORELINES and MULTILINE.");

		return description;
	}

}
