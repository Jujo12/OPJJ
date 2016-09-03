package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * Computes and prints all requested roots of the given complex number. The
 * parameters are submitted as three command line arguments: real part of
 * complex number, imaginary part of complex number, and required root to
 * calculate (integer greated than 1).
 */
public class Roots {
	/**
	 * Complex number data structure
	 */
	static class ComplexNum {
		double re;
		double im;
		double r;
		double theta;
	}

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Expected three arguments.");
			return;
		}

		ComplexNum broj = new ComplexNum();
		broj.re = Double.parseDouble(args[0]);
		broj.im = Double.parseDouble(args[1]);

		int nth = Integer.parseInt(args[2]);
		if (nth <= 1) {
			System.err.println("The required root to calculate must be greater than 1.");
		}

		// we need polar coords to calculate the roots of a complex number
		calculatePolarCoords(broj);

		ComplexNum[] roots = new ComplexNum[nth];
		for (int i = 0; i < nth; i++) {
			double theta = broj.theta / nth + 2 * Math.PI * i / nth;
			double r = Math.pow(broj.r, 1.0 / nth);

			roots[i] = new ComplexNum();
			roots[i].r = r;
			roots[i].theta = theta;

			// convert polar coords to cartesian coords
			calculateCartesianCoords(roots[i]);
		}

		System.out.printf("You requested calculation of %d%s roots. Solutions are:%n", nth, orderSuffix(nth));
		for (int i = 0; i < nth; i++) {
			char sign = (roots[i].im < 0) ? '-' : '+';

			DecimalFormat format = new DecimalFormat("0.###");
			System.out.printf("%d) %s %c %si %n", i + 1, format.format(roots[i].re), sign,
					format.format(Math.abs(roots[i].im)));
		}
	}

	/**
	 * Calculates the polar coordinates (r, theta) of the given complex number.
	 * Stores the new values within the same object (structure). The existing
	 * polar coordinates are overwritten.
	 * 
	 * @param complexNum
	 *            the number to calculate the polar coordinates for
	 */
	private static void calculatePolarCoords(ComplexNum complexNum) {
		complexNum.r = Math.sqrt((Math.pow(complexNum.re, 2) + Math.pow(complexNum.im, 2)));
		complexNum.theta = Math.atan2(complexNum.im, complexNum.re);
	}

	/**
	 * Calculates the cartesian coordinates (Re, Im) of the given complex
	 * number. Stores the new values within the same object (structure). The
	 * existing cartesian coordinates are overwritten.
	 * 
	 * @param complexNum
	 *            the number to calculate the cartesian coordinates for
	 */
	private static void calculateCartesianCoords(ComplexNum complexNum) {
		complexNum.re = Math.cos(complexNum.theta) * complexNum.r;
		complexNum.im = Math.sin(complexNum.theta) * complexNum.r;
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

}