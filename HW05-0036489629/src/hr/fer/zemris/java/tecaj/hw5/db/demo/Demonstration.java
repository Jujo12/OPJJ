package hr.fer.zemris.java.tecaj.hw5.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.console.DBConsole;

/**
 * The Demonstration program for the database. Loads the entries from
 * database.txt and runs the console.
 * 
 * @author Juraj Juričić
 */
public class Demonstration {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);

			StudentDatabase db = new StudentDatabase(lines);

			DBConsole.readLines(db);
		} catch (IOException e) {
			System.err.println("File could not be read.");
		} catch (Exception e) {
			System.err.println("An unknown error occured.");
		}

	}
}
