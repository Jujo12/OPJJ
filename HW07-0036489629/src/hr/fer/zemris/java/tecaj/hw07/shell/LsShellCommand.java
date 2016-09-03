package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.MyShellException;

/**
 * ls command. Takes a single argument – directory path – and writes a directory
 * listing (not recursive).
 *
 * @author Juraj Juričić
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1);

		Path path = null;
		try {
			path = Paths.get(arguments.get(0).trim());
		} catch (InvalidPathException e) {
			throw new MyShellException(
					"Invalid path provided: " + e.getMessage());
		}

		printDir(env, path.toFile());

		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints the files in a directory to environment's output.
	 *
	 * @param env the environment to print the content to
	 * @param dir the directory to output
	 */
	private void printDir(Environment env, File dir) {
		if (!dir.isDirectory()) {
			throw new MyShellException("Target is not a directory.");
		}

		File[] children = dir.listFiles();

		if (children == null) {
			return;
		}

		Arrays.sort(children, (f1, f2) -> f1.getName().compareTo(f2.getName()));
		try {
			for (File f : children) {
				printLine(env, f);
			}
		} catch (IOException e) {
			throw new MyShellException(
					"An error occured while trying to write to output.");
		}
	}

	/**
	 * Prints a single line (file or directory).
	 *
	 * @param env the environment to output the line to
	 * @param file the file to parse and print attributes of
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void printLine(Environment env, File file) throws IOException {
		String permissions = permissions(file);
		long size = file.length();
		String creationDateTime = creationDateTime(file);
		String name = file.getName();

		env.writeln(String.format("%s %10d %s %s", permissions, size,
				creationDateTime, name));
	}

	/**
	 * Generates the permissions attribute string (drwx)
	 *
	 * @param file the file to check
	 * @return the generated string (---- / drwx)
	 */
	private String permissions(File file) {
		Path path = file.toPath();

		char d = (Files.isDirectory(path)) ? 'd' : '-';
		char r = (Files.isReadable(path)) ? 'r' : '-';
		char w = (Files.isWritable(path)) ? 'w' : '-';
		char x = (Files.isExecutable(path)) ? 'x' : '-';

		return new StringBuilder().append(d).append(r).append(w).append(x)
				.toString();
	}

	/**
	 * Returns the file's creation date and time in YYYY-MM-DD HH:MM:SS format
	 *
	 * @param file the file
	 * @return the creation date and time
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String creationDateTime(File file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();

		BasicFileAttributeView faView = Files.getFileAttributeView(path,
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();

		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		return formattedDateTime;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add(
				"Takes a single argument – directory path – and writes a directory listing (not recursive).");

		return description;
	}

}
