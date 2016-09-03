package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * The demonstration program for the HW06 problem 2. Prints the median of set
 * (Joe, Jane, Adam, Zed).<br>
 * The program does not accept command line arguments.
 *
 * @author Juraj Juričić
 */
public class MedianDemo2 {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		Optional<String> result = likeMedian.get();
		System.out.println(result); // Writes: Jane
	}

}
