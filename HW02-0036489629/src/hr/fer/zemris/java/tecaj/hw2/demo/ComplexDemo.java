package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * Demonstration program for the ComplexNumber class.
 * 
 * @author JJ
 *
 */
public class ComplexDemo {

	/**
	 * The main method that is executed when the program is run.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("i");
		System.out.println(c2);
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		
		System.out.println(c3);
	}

}
