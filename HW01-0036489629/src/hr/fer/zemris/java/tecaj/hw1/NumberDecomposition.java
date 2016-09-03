package hr.fer.zemris.java.tecaj.hw1;

/**
 * Calculates and prints the decomposition of the given number N onto prime
 * factors. The parameter (N) is submitted as the command line argument.
 */
public class NumberDecomposition {

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Expected one argument.");
			return;
		}

		int n = Integer.parseInt(args[0]);

		if (n < 1) {
			System.err.printf("Expected an integer greater than 1, got %d%n.", n);
			return;
		}

		// everything is ok; prime decomposition is delegated:
		printPrimeDecomposition(n);
	}

	/**
	 * Calculates the number decomposition and prints it to standard output.
	 * 
	 * @param n
	 *            the integer to decompose
	 */
	private static void printPrimeDecomposition(int n) {
		for (int i = 2; i <= n / i; i++) { // to n/i because we eliminated i as
											// a factor.
			while (n % i == 0) {
				System.out.printf("%d%n", i);
				n /= i;
			}
		}
		if (n > 1)
			System.out.printf("%d%n", n); // n is left over
	}
}