package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MyShellException;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

/**
 * The MyShell shell. Version 1.0. Provides the following commands: charsets,
 * cat, ls, tree, copy, mdir, hexdump.
 *
 * @author Juraj Juričić
 */
public class MyShell {

	/** The available commands map. */
	private static Map<String, ShellCommand> commands;

	/** The environment used for input / output. */
	public static Environment environment;

	static {
		commands = new HashMap<>();

		ShellCommand[] tempCommands = { new ExitShellCommand(),
				new HelpShellCommand(), new CharsetsShellCommand(),
				new CatShellCommand(), new LsShellCommand(),
				new TreeShellCommand(), new CopyShellCommand(),
				new MkdirShellCommand(), new HexdumpShellCommand(),
				new SymbolShellCommand()};

		for (ShellCommand i : tempCommands) {
			commands.put(i.getCommandName(), i);
		}

		environment = new MyEnvironment();
	}

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		try {
			environment.writeln("Welcome to MyShell v 1.0");
		} catch (IOException e) {
			System.err.println(
					"Error writing to output buffer, exiting the shell.");
			return;
		}

		ShellStatus status = ShellStatus.CONTINUE;
		do {
			try {
				environment.write(environment.getPromptSymbol() + " ");

				String userInput = environment.readLine();
				String[] query = userInput.split(" ", 2);

				ShellCommand command = commands.get(query[0].toLowerCase());
				if (command == null) {
					environment.writeln("Unknown command.");
					continue;
				}

				status = command.executeCommand(environment,
						query.length == 2 ? query[1] : null);
			} catch (IOException e) {
				System.err.println(
						"Error writing to output buffer, exiting the shell.");
				return;
			} catch (MyShellException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		} while (status == ShellStatus.CONTINUE);
	}

	/**
	 * The implementation of the environment. Outputs / inputs to / from
	 * standard input / output.
	 *
	 * @author Juraj Juričić
	 */
	public static class MyEnvironment implements Environment {

		/** The reader. Reads from standard output. */
		private BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		/** The writer. Writes to standard output. */
		private BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(System.out));

		/** The multiline symbol. Defaults to | . */
		private char multilineSymbol = '|';

		/** The prompt symbol. Defaults to > . */
		private char promptSymbol = '>';

		/** The more lines symbo. Defaults to \ . */
		private char moreLinesSymbol = '\\';

		/**
		 * Instantiates a new MyEnvironment.
		 */
		public MyEnvironment() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#readLine()
		 */
		@Override
		public String readLine() throws IOException {
			String input = "";

			int i = 0;
			do {
				if (i++ != 0) {
					write(Character.toString(multilineSymbol));
					input = input.substring(0, input.length() - 1);
				}
				input += reader.readLine();
			} while (input.endsWith(Character.toString(moreLinesSymbol)));

			return input;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#write(java.lang.
		 * String)
		 */
		@Override
		public void write(String text) throws IOException {
			throwIfNullArguments(text);

			writer.write(text);
			writer.flush();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#writeln(java.lang.
		 * String)
		 */
		@Override
		public void writeln(String text) throws IOException {
			throwIfNullArguments(text);

			write(text);
			writer.newLine();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#commands()
		 */
		@Override
		public Iterable<ShellCommand> commands() {
			return MyShell.commands.values();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#commandsMap()
		 */
		@Override
		public Map<String, ShellCommand> commandsMap() {
			return MyShell.commands;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#getMultilineSymbol()
		 */
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#setMultilineSymbol(
		 * java.lang.Character)
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			throwIfNullArguments(symbol);

			this.multilineSymbol = symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#getPromptSymbol()
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#setPromptSymbol(java.
		 * lang.Character)
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			throwIfNullArguments(symbol);

			this.promptSymbol = symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#getMorelinesSymbol()
		 */
		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.tecaj.hw07.shell.Environment#setMorelinesSymbol(
		 * java.lang.Character)
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			throwIfNullArguments(symbol);

			this.moreLinesSymbol = symbol;
		}

	}

	/**
	 * Throws {@link IllegalArgumentException} if any of the given arguments is
	 * a null pointer.
	 *
	 * @param objects
	 *            the objects to check for null pointers
	 */
	public static void throwIfNullArguments(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				throw new IllegalArgumentException("Cannot be null.");
			}
		}
	}

	/**
	 * Space splitter. Splits the arguments on spaces. Allows quotes and
	 * escaping of quotes with backslash (\).
	 *
	 * @param args
	 *            the arguments string
	 * @return the list with split arguments
	 */
	public static List<String> spaceSplitter(String args) {
		if (args == null) {
			return new ArrayList<String>();
		}
		String ESCAPE_SEQUENCE_QUOT = "\\'''J'*'";
		String ESCAPE_SEQUENCE_BACKSLASH = "\\'''K'*'";

		args = args.replace("\\\\", ESCAPE_SEQUENCE_BACKSLASH);
		args = args.replace("\\\"", ESCAPE_SEQUENCE_QUOT);
		String[] parsedArgs = args.split("[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

		List<String> arguments = new ArrayList<>();
		for (String i : parsedArgs) {
			arguments
					.add(i.replace("\"", "").replace(ESCAPE_SEQUENCE_QUOT, "\"")
							.replace(ESCAPE_SEQUENCE_BACKSLASH, "\\"));
		}

		return arguments;
	}

	/**
	 * Checks the command argument count against the given limits. Throws a
	 * MyShellException if the provided argument count is not equal to one of
	 * the allowed argument counts.
	 *
	 * @param arguments
	 *            the arguments
	 * @param expected
	 *            the expected argument count
	 * @param expected1
	 *            the expected argument count
	 */
	public static void checkCommandArguments(List<String> arguments,
			int expected, int... expected1) {
		int argc = arguments.size();
		if (argc == expected) {
			return;
		} else {
			for (int i : expected1) {
				if (argc == i) {
					return;
				}
			}
		}

		if (expected1.length == 0 && expected == 0) {
			throw new MyShellException(
					"This command does not accept arguments.");
		} else {
			StringBuilder message = new StringBuilder(
					"Invalid number of arguments provided. Expected ");

			message.append(expected);
			for (int i = 0; i < expected1.length; i++) {
				message.append(", ");
				if (i == expected1.length - 1) {
					message.append("or ");
				}
				message.append(expected1[i]);
			}
			message.append(" arguments");

			throw new MyShellException(message.toString());
		}
	}
}
