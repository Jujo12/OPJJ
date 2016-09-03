package hr.fer.zemris.java.tecaj.hw1;

/**
 * Calculates and writes n-th number of Hofstadter's Q sequence. The parameter
 * (n) is submitted as the command line argument.
 */
public class HofstadterQ {

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.printf("One argument excepted, got %d%n", args.length);
			return;
		}

		long i = Long.parseLong(args[0]);
		if (i < 0) {
			System.err.printf("Positive number expected, got %d%n", i);
			return;
		}

		System.out.printf(
				"You requested calculation od %d%s number of Hofstadter's Q-sequence. The requested number is %d.", i,
				orderSuffix((int) i), hofstadterQ(i));
	}

	/**
	 * Returns the two-character string that is the ordinal number suffix in
	 * English language. The suffixes are st for numbers ending with 1, nd for
	 * numbers ending with, rd for numbers ending with 3, and th for the rest.
	 * 
	 * @param n
	 *            the number to determine the suffix for.
	 * @return the suffix (st, nd, rd or th)
	 */
	private static String orderSuffix(int n) {
		int lastDigit = n % 10;
		switch (lastDigit) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	/**
	 * Returns the nth number of Hofstadter's Q sequence.
	 * 
	 * @param n
	 *            the n to calculate the Q(n) for
	 * @return the nth number of Hofstadter's Q sequence
	 */
	private static long hofstadterQ(long n) {
		// this could be optimized with a Map (as a caching mechanism)

		// Q(1) = Q(2) = 1
		if (n == 1 || n == 2) {
			return 1;
		}

		// Q(n) = Q(n - Q(n-1)) + Q(n - Q(n-2)), n > 2
		long q = hofstadterQ(n - hofstadterQ(n - 1)) + hofstadterQ(n - hofstadterQ(n - 2));

		return q;
	}
}
