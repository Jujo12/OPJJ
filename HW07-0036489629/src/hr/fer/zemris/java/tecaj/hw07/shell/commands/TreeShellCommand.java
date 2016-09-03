package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
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
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String args) {
		List<String> arguments = MyShell.spaceSplitter(args);
		MyShell.checkCommandArguments(arguments, 1);

		Path path = Paths.get(arguments.get(0));

		if (!Files.isDirectory(path)) {
			throw new MyShellException("Target is not a directory.");
		}

		try {
			Files.walkFileTree(path, new TreePrinter(env));
		} catch (IOException e) {
			throw new MyShellException("File IO error: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("Prints a directory tree. Accepts a single argument");

		return description;
	}

	/**
	 * The Class TreePrinter. Walks through a directory tree and outputs
	 * structure to the given environment.
	 *
	 * @author Juraj Juričić
	 */
	private class TreePrinter implements FileVisitor<Path> {

		/** Current indentation. */
		private int indentation;

		/** The environment to output the tree to. */
		private Environment env;

		/**
		 * Instantiates a new tree printer.
		 *
		 * @param env
		 *            the environment to output the tree to.
		 */
		public TreePrinter(Environment env) {
			super();
			MyShell.throwIfNullArguments(env);

			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException e)
				throws IOException {
			indentation -= 2;

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indentation == 0) {
				env.writeln(dir.toFile().getAbsolutePath());
			} else {
				env.writeln(String.format("%" + indentation + "s%s", "",
						dir.toFile().getName()));
			}

			indentation += 2;

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1)
				throws IOException {
			String fileName = file.toFile().getName();

			env.writeln(String.format("%" + indentation + "s%s", "", fileName));

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException e)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}

}
