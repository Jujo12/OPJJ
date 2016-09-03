package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The hexdump command. Expects a single argument: file name, and outputs hex-output of that file to the environment.
 *
 * @author Juraj Juričić
 */
public class HexdumpShellCommand implements ShellCommand {

	/** Size of single line (split into half in a single line. */
	private static final int GROUP_SIZE = 16;

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1);

		Path path = Paths.get(arguments.get(0));
		if (!path.toFile().isFile()) {
			throw new MyShellException("Provided argument is not a file.");
		}

		try {
			hexDump(env, path);
		} catch (IOException e) {
			throw new MyShellException("IO exception: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Dumps file hex data to envronment's output.
	 *
	 * @param env the environment to output the file's hex to.
	 * @param path the path to file.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void hexDump(Environment env, Path path) throws IOException {
		try(InputStream stream = new BufferedInputStream(
				new FileInputStream(path.toFile()))){

			int read = 0;
			int counter = 0;
	
			do {
				byte[] data = new byte[GROUP_SIZE];
				read = stream.read(data);
	
				if (read == -1) {
					break;
				}
	
				String hex = bytesToHexdump(data, read);
				String alphaNum = bytesToAlphanum(data, read);
	
				env.writeln(String.format("%08x: %s| %s", counter,
						hex.toUpperCase(), alphaNum));
				counter += read;
			} while (true);
		}
	}

	/**
	 * Converts bytes array to hex representation.
	 *
	 * @param data the bytes array
	 * @param read the size of filled data in array
	 * @return the hex representation
	 */
	private static String bytesToHexdump(byte[] data, int read) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i < read) {
				buffer.append(Integer.toString((data[i] & 0xff) + 0x100, 16)
						.substring(1));
			} else {
				buffer.append("  ");
			}

			if (i == GROUP_SIZE / 2 - 1)
				buffer.append("|");
			else
				buffer.append(" ");
		}

		return buffer.toString();
	}

	/**
	 * Converts bytes array to alphanum representation (ASCII table). Bytes out
	 * of range (37-127) are shown as a dot (.).
	 *
	 * @param data
	 *            the bytes array
	 * @param read
	 *            the size of filled data
	 * @return the string
	 */
	private static String bytesToAlphanum(byte[] data, int read) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < read; i++) {
			int val = Byte.valueOf(data[i]).intValue();
			if (val < 37 || val > 127) {
				builder.append(".");
			} else {
				builder.append(Character.toChars(val));
			}
		}

		return builder.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add(
				"Expects a single argument: file name, and produces hex-output of that file.");

		return description;
	}

}
