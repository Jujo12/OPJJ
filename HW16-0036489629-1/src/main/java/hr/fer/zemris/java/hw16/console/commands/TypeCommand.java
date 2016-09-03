package hr.fer.zemris.java.hw16.console.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import hr.fer.zemris.java.hw16.console.ConsoleContext;

/**
 * The command outputs the content of the requested file to console. Accepts one
 * argument: ID of document to output, as provided by results command.
 *
 * @author Juraj Juričić
 */
public class TypeCommand extends AbstractCommand {
	
	/**
	 * Instantiates a new type command.
	 *
	 * @param consoleContext the console context
	 */
	public TypeCommand(ConsoleContext consoleContext) {
		super(consoleContext);
	}

	@Override
	public void execute(String line) {
		try {
			int id = Integer.parseInt(line);

			Path path = consoleContext.getSearchEngine().getResultPath(id);
			if (path == null) {
				System.out.println("No results yes.");
				return;
			}

			Files.readAllLines(path, StandardCharsets.UTF_8)
					.forEach(System.out::println);

		} catch (NumberFormatException e) {
			System.out.println("Invalid ID provided");
			return;
		} catch (IOException e) {
			System.out.println("An error occurred while reading from file.");
			return;
		}
	}
}
