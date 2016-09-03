package hr.fer.zemris.java.tecaj.hw1;

/**
 * Prints the first N prime numbers. 2 is the first prime number. The parameter
 * (N) is submitted as the command line argument.
 */
public class PrimeNumbers {

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

		if (n >= 1)
			System.out.println("2"); // 2 is the first prime number

		int i = 1; // counter
		int j = 3; // the number we are checking (2 already printed)
		while (i < n) {
			if (isPrime(j)) {
				System.out.printf("%d%n", j);
				i++;
			}
			j += 2;
		}
	}

	/**
	 * Determines whether a number is prime or not. Uses a primitive algorithm
	 * [O(sqrt(N)].
	 * 
	 * @param number
	 *            Number to check the primality for.
	 * @return true if the number is prime, false otherwise.
	 */
	private static boolean isPrime(int number) {
		if (number == 2) {
			return true; // 2 is prime
		}
		if (number <= 1 || // 1 is not a prime, 0 and negative numbers are not
							// prime
				number % 2 == 0 // even numbers are not prime
		) {
			return false;
		}

		for (int i = 3, max = (int) Math.sqrt(number) + 1; i <= max; i += 2) {
			// we start with 3 and increase the number by 2 every turn (to skip
			// the even numbers)
			if (number % i == 0){
				return false;
			}
		}
		return true;
	}

}
