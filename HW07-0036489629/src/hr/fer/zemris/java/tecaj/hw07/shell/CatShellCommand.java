package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.MyShellException;

/**
 * Cat command. Opens given file and writes its content to console. Accepts one
 * or two arguments. The first argument is path to the file and is mandatory.
 * The second argument is charset name that should be used to interpret chars
 * from bytes. If not provided, a default platform charset is used
 *
 * @author Juraj Juričić
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1, 2);

		Charset charset = null;
		if (arguments.size() == 2) {
			charset = Charset.forName(arguments.get(1).trim());
		} else {
			charset = Charset.defaultCharset();
		}

		if (charset == null) {
			throw new MyShellException("Unsupported charset.");
		}

		Path file = Paths.get(arguments.get(0));
		try {
			readFileContent(env, file, charset);
		} catch (IOException e) {
			throw new MyShellException("File read error: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method that outputs the content of the file at the given path to
	 * the environment output. Uses the given charset for reading the file.
	 * 
	 * @param env the environment to output the content to
	 * @param path the path to file to read
	 * @param charset the charset used for reading the file
	 * @throws IOException Thrown in case of file IO error.
	 */
	private void readFileContent(Environment env, Path path, Charset charset)
			throws IOException {

		File file = path.toFile();

		try (BufferedReader fileReader = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(new FileInputStream(file)),
						charset))) {
			do {
				String line = fileReader.readLine();
				if (line == null) {
					break;
				}

				env.writeln(line);
			} while (true);
		} catch (FileNotFoundException e) {
			throw new MyShellException("File not found.");
		}
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Opens given file and writes its content to console.");
		description.add("Accepts one or two arguments.");
		description.add(
				"The first argument is path to the file and is mandatory.");
		description
				.add("The second argument is charset name that should be used to interpret"
						+ "chars from bytes. If not provided, a default platform charset is used.");

		return description;
	}

}
