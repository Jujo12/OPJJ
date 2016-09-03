package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * The demonstration program for the HW06 problem 3. Prints the first pairs of
 * first two prime numbers. <br>
 * The program does not accept command line arguments.
 *
 * @author Juraj Juričić
 */
public class PrimesDemo2 {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
