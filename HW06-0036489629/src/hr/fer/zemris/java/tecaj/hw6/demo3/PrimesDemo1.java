package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * The demonstration program for the HW06 problem 3. Prints the first 5 prime
 * numbers. <br>
 * The program does not accept command line arguments.
 *
 * @author Juraj Juričić
 */
public class PrimesDemo1 {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(50); // 5: how
																		// many
																		// of
																		// them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
