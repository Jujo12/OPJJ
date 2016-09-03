package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * The demonstration program for the HW06 problem 2. Prints the median of set
 * (10,5,3).<br>
 * The program does not accept command line arguments.
 *
 * @author Juraj Juričić
 */
public class MedianDemo1 {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(18));
		likeMedian.add(new Integer(18));
		likeMedian.add(new Integer(18));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result);

		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}
