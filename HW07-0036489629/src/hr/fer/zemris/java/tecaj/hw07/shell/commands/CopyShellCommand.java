package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Copy shell command. Accepts two arguments: source and destination. Copies the
 * file at source path to destination path. If the second argument is a
 * directory, the original file is copied into that directory using the original
 * file name.
 *
 * @author Juraj Juričić
 */
public class CopyShellCommand implements ShellCommand {

	/** The buffer size used for copying. Defaults to 4K. */
	private static final int BUFFER_SIZE = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 2);

		Path source = Paths.get(arguments.get(0));
		Path dest = Paths.get(arguments.get(1));
		try {
			copy(source, dest, env);
		} catch (IOException e) {
			throw new MyShellException("Copy error: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies the data from source to target path. If the target is a directory,
	 * the file name is preserved.
	 *
	 * @param source
	 *            the source path (file only)
	 * @param target
	 *            the target path (file or directory)
	 * @param env
	 *            the environment to output the messages to
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void copy(Path source, Path target, Environment env)
			throws IOException {
		if (!source.toFile().isFile()) {
			throw new IOException("Source is not a file.");
		}

		if (Files.isDirectory(target)) {
			String fileName = source.getFileName().toString();
			target = target.resolve(fileName).normalize();
		}

		if (Files.isRegularFile(target)) {
			env.writeln("Overwrite file? Y/N: ");
			String answer = env.readLine();
			if (!answer.trim().toLowerCase().equals("y")) {
				return;
			}
		}

		try (InputStream inputStream = new BufferedInputStream(
				new FileInputStream(source.toString()));
				OutputStream outputStream = new BufferedOutputStream(
						new FileOutputStream(target.toString()));) {
			byte[] data = new byte[BUFFER_SIZE];
			while (true) {
				int read = inputStream.read(data);
				if (read == -1)
					break;

				outputStream.write(data, 0, read);
			}
		}
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Accepts two arguments: source and destination.");
		description.add("Copies the file at source path to destination path.");
		description.add(
				"If the second argument is directory, the original file is copied into that directory using the original file name.");

		return description;
	}

}
