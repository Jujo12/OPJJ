package hr.fer.zemris.java.tecaj.hw5.db.console;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.db.DbQueryException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

/**
 * The database console environment.
 * 
 * @author Juraj Juričić
 */
public class DBConsole {

	/** The terminator command. */
	private static final String TERMINATOR = "exit";

	/**
	 * Reads the commands from the standard input. Parses the line and outputs
	 * the result.
	 *
	 * @param db
	 *            the database
	 */
	public static void readLines(StudentDatabase db) {
		String line = null;
		
		try(Scanner s = new Scanner(System.in)) {
			do {
				line = s.nextLine().trim();
				if (line.length() == 0) {
					continue;
				}

				if (line.equals(TERMINATOR)) {
					System.out.println("Goodbye!");
					return;
				}

				LineProcessor.process(line, db);
			} while (true);
		} catch (DbQueryException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An unexpected error occured.");
		}
	}

	/**
	 * The line processor class.
	 */
	private static class LineProcessor {

		/**
		 * Processes the line. Throws DbQueryException in case of a syntax or
		 * semantic error.
		 *
		 * @param line
		 *            the line to process.
		 * @param db
		 *            the database
		 */
		public static void process(String line, StudentDatabase db) {
			String[] data = line.split("\\s", 2);
			if (data.length < 2) {
				throw new DbQueryException("Invalid syntax.");
			}

			List<StudentRecord> results = new LinkedList<>();

			switch (data[0]) {
			case "indexquery":
				// Complexity: O(1)
				String jmbag = IndexQueryParser.parse(data[1]);
				StudentRecord row = db.forJmbag(jmbag);
				if (row != null) {
					results.add(row);
				}
				break;

			case "query":
				results = db.filter(new QueryFilter(data[1]));
				break;

			default:
				throw new DbQueryException("Unknown command: " + data[0]);
			}

			// print results
			TablePrinter printer = new TablePrinter(results);
			printer.printTable();
		}

		/**
		 * PArses the indexquery command.
		 */
		private static class IndexQueryParser {

			/** The length of JMBAG. Defaults to 10. */
			private static final int JMBAG_LENGTH = 10;

			/**
			 * Parses the query, returning the parsed JMBAG.
			 *
			 * @param queryString
			 *            the query string
			 * @return the parsed JMBAG
			 */
			public static String parse(String queryString) {
				String pattern = "jmbag\\s*=\\s*\"([0-9]{" + JMBAG_LENGTH
						+ "})\"";

				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(queryString.trim());

				try {
					if (!m.matches()) {
						throw new IllegalStateException();
					}

					return m.group(1);
				} catch (IllegalStateException e) {
					throw new DbQueryException(
							"Invalid command (indexquery) syntax.");
				}
			}
		}

		/**
		 * Helper class for printing the table with results.
		 */
		private static class TablePrinter {

			/** The records. */
			private List<StudentRecord> records;

			/** The jmbag size. */
			private int jmbagSize = 0;

			/** The first name size. */
			private int firstNameSize = 0;

			/** The last name size. */
			private int lastNameSize = 0;

			/** The final grade size. */
			private final int finalGradeSize = 1; // always 1

			/**
			 * Instantiates a new table printer.
			 *
			 * @param records
			 *            the records to print
			 */
			public TablePrinter(List<StudentRecord> records) {
				if (records == null) {
					throw new IllegalArgumentException("Null pointer");
				}
				this.records = records;

				for (StudentRecord record : records) {
					jmbagSize = Math.max(jmbagSize, record.getJmbag().length());
					firstNameSize = Math.max(firstNameSize,
							record.getFirstName().length());
					lastNameSize = Math.max(lastNameSize,
							record.getLastName().length());
				}
			}

			/**
			 * Prints the table with results to the standard output.
			 */
			private void printTable() {
				if (records.isEmpty()) {
					System.out.println("Records selected: 0");
					return;
				}

				String wrapper = createWrapper();
				System.out.println(wrapper);

				for (StudentRecord record : records) {
					String output = createRowOutput(record);
					System.out.println(output);
				}

				System.out.println(wrapper);
			}

			/**
			 * Creates the wrapper (header and footer) for the given results
			 * table.
			 *
			 * @return the generated wrapper.
			 */
			private String createWrapper() {
				StringBuilder builder = new StringBuilder();
				builder.append("+").append(padCharString('=', jmbagSize + 2))
						.append("+")
						.append(padCharString('=', lastNameSize + 2))
						.append("+")
						.append(padCharString('=', firstNameSize + 2))
						.append("+")
						.append(padCharString('=', finalGradeSize + 2))
						.append("+");

				return builder.toString();
			}

			/**
			 * Creates the row output for the given record
			 *
			 * @param record
			 *            the record to generate the row for.
			 * @return the generated row.
			 */
			private String createRowOutput(StudentRecord record) {
				StringBuilder builder = new StringBuilder();
				builder.append("| ").append(record.getJmbag())
						.append(padCharString(' ',
								jmbagSize - record.getJmbag().length()))
						.append(" | ").append(record.getLastName())
						.append(padCharString(' ',
								lastNameSize - record.getLastName().length()))
						.append(" | ").append(record.getFirstName())
						.append(padCharString(' ',
								firstNameSize - record.getFirstName().length()))
						.append(" | ").append(record.getFinalGrade())
						.append(" |");

				return builder.toString();
			}

			/**
			 * Constructs a string with the given amount of characters c.
			 *
			 * @param c
			 *            the padding character
			 * @param len
			 *            the length of the string
			 * @return the padded string
			 */
			private static String padCharString(char c, int len) {
				char[] chars = new char[len];
				for (int i = 0; i < len; i++) {
					chars[i] = c;
				}
				return new String(chars);
			}
		}
	}
}
