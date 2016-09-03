package hr.fer.zemris.java.fractals.Newton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The class represents an immutable complex number. Complex number is defined
 * by its real (re) and imaginary (im) parts.
 *
 * @author Juraj Juričić
 */
public class Complex {

	/** The complex number 0+0i. */
	public static final Complex ZERO = new Complex();

	/** The complex number 1+0i. */
	public static final Complex ONE = new Complex(1, 0);

	/** The complex number -1+0i. */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** The complex number 0+1i. */
	public static final Complex IM = new Complex(0, 1);

	/** The complex number 0-1i. */
	public static final Complex IM_NEG = new Complex(0, -1);

	/** The real part. */
	private final double re;

	/** The imaginary part. */
	private final double im;

	/**
	 * Instantiates a new complex number without any arguments, equals to ZERO.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Instantiates a new complex number with the given real and imaginary
	 * parts.
	 *
	 * @param re
	 *            the re
	 * @param im
	 *            the im
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Instantiates a new complex number from the given string that is parsed
	 * into a complex number. General syntax for complex numbers is of form a+ib
	 * or a-ib where parts that are zero can be dropped, but not both (empty
	 * string is not legal complex number); for example, zero can be given as 0,
	 * i0, 0+i0, 0-i0. If there is 'i' present but no b is given, b is assumed
	 * b=1.
	 * 
	 * @param str
	 *            string to parse
	 */
	public Complex(String str) {
		String[] parts = str.split("(\\+i|-i)");

		if (parts.length > 2) {
			throw new NumberFormatException("Could not parse " + str);
		}

		if (str.startsWith("+i") || str.startsWith("-i")
				|| str.startsWith("i")) {
			re = 0.0;

			String newStr = str.substring(1).trim();
			if (!str.startsWith("i")) {
				newStr = newStr.substring(1).trim();
			}
			if (newStr.isEmpty()) {
				if (str.startsWith("-i"))
					im = -1.0;
				else
					im = 1.0;
			} else {
				im = Double.parseDouble(newStr);
			}
			return;
		}

		re = Double.parseDouble(parts[0].trim());
		if (parts.length == 1) {
			if (str.contains("i")) {
				im = 1.0;
			} else {
				im = 0.0;
			}
		} else {
			im = Double.parseDouble(parts[1].trim());
		}
	}

	/**
	 * Returns the module (magnitude) of the complex number.
	 *
	 * @return the module
	 */
	public double module() {
		return Math.hypot(re, im);
	}

	/**
	 * Multiplies this complex number with the given complex number c and
	 * returns the result as a new Complex instance.
	 *
	 * @param c
	 *            the Complex number to multiply this object with
	 * @return the newly constructed complex number (this * c)
	 */
	public Complex multiply(Complex c) {
		double re = this.re * c.re - this.im * c.im;
		double im = this.re * c.im + this.im * c.re;

		return new Complex(re, im);
	}

	/**
	 * Divides this complex number by the given complex number c and returns the
	 * result as a new Complex instance. The other complex number cannot be
	 * equal to zero (0+0i).
	 *
	 * @param c
	 *            the Complex number to divide this object by
	 * @return the newly constructed complex number (this / c)
	 * @throws ArithmeticException
	 *             Thrown if the c is equal to 0+0i - division by zero.
	 */
	public Complex divide(Complex c) throws ArithmeticException {
		if (c.equals(ZERO)) {
			throw new ArithmeticException("Division by zero.");
		}

		return this.multiply(c.rec());
	}

	/**
	 * Constructs a new Complex number that is the reciprocal value of this
	 * complex number.
	 *
	 * @return the newly constructed complex number (1 / this)
	 */
	private Complex rec() {
		// I needed the reciprocal value of a Complex number for division, so I
		// implemented it as a method

		double squaredNorm = re * re + im * im;

		return new Complex(re / squaredNorm, -im / squaredNorm);
	}

	/**
	 * Adds the given complex number c to this complex number and returns the
	 * result as a new Complex instance.
	 *
	 * @param c
	 *            the Complex number to add to this object.
	 * @return the newly constructed complex number (this + c)
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Subtracts the given complex number c from this complex number and returns
	 * the result as a new Complex instance.
	 *
	 * @param c
	 *            the Complex number to subtract from this object.
	 * @return the newly constructed complex number (this - c)
	 */
	public Complex sub(Complex c) {
		return add(c.negate());
	}

	/**
	 * Negates this complex number and returns the result as a new Complex
	 * instance.
	 *
	 * @return the newly constructed complex number (-this)
	 */
	public Complex negate() {
		return this.multiply(ONE_NEG);
	}

	/**
	 * Returns the new Complex number that represents this ^n, where n is
	 * non-negative integer.
	 *
	 * @param n
	 *            the exponent
	 * @return the newly constructed complex number (this ^ n)
	 * @throws IllegalArgumentException
	 *             Thrown if the provided exponent (n) is a negative number.
	 */
	public Complex power(int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Exponent must be a non-negative integer.");
		}
		double magnitude = this.module();
		double angle = Math.atan2(im, re);

		double newMagnitude = Math.pow(magnitude, n);
		double newAngle = angle * n;

		return new Complex(newMagnitude * Math.cos(newAngle),
				newMagnitude * Math.sin(newAngle));
	}

	/**
	 * Calculates n-th roots of this complex number. Returns the results as a
	 * list of complex numbers that are roots of this complex number.
	 *
	 * @param n
	 *            the n
	 * @return the list of n-th roots
	 * @throws IllegalArgumentException Thrown if the provided argument (n) is a negative number.
	 */
	public List<Complex> root(int n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException("n must be >= 1.");
		}

		List<Complex> roots = new ArrayList<>(n);

		double magnitude = Math.pow(module(), (1.0 / n));
		double offset = 2 * Math.PI / n;
		double angle = Math.atan2(im, re) / n;

		for (int i = 0; i < n; i++) {
			double re = magnitude * Math.cos(angle + offset * i);
			double im = magnitude * Math.sin(angle + offset * i);
			roots.add(new Complex(re, im));
		}

		return roots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("0.######");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		char sign = (im < 0) ? '-' : '+';

		String formatReal = format.format(re);
		String formatImaginary = format.format(Math.abs(im)) + "i";

		if (re == 0) {
			formatReal = "";
		}
		if (re == 0) {
			sign = ' ';
			formatImaginary = "";
		}
		if (im == 1) {
			formatImaginary = "i";
		}
		if (re == 0 && im == 0) {
			formatReal = "0";
		}

		return String.format("%s %c %s", formatReal, sign, formatImaginary)
				.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		double EPS = 1E-15;
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(im - other.im) > EPS)
			return false;
		if (Math.abs(re - other.re) > EPS)
			return false;
		return true;
	}

}
