package hr.fer.zemris.java.tecaj.hw2;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * The Class ComplexNumber that represents an unmodifiable complex number
 * 
 * @author JJ
 */
public class ComplexNumber {

	/** The real part. */
	private double real;

	/** The imaginary part. */
	private double imaginary;

	/**
	 * Instantiates a new complex number.
	 *
	 * @param real
	 *            the real part
	 * @param imaginary
	 *            the imaginary party
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Static factory method that constructs a ComplexNumber from the real part.
	 *
	 * @param real
	 *            the real part of the complex number
	 * @return the complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Static factory method that constructs a ComplexNumber from the imaginary
	 * part.
	 *
	 * @param imaginary
	 *            the imaginary party of the complex number
	 * @return the complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Static factory method that constructs a ComplexNumber from the polar
	 * coordinates.
	 *
	 * @param magnitude
	 *            the magnitude
	 * @param angle
	 *            the angle
	 * @return the complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = Math.cos(angle) * magnitude;
		double imaginary = Math.sin(angle) * magnitude;

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Parses the String for a complex number.
	 *
	 * @param s
	 *            the string to parse
	 * @return the resulting complex number
	 */
	public static ComplexNumber parse(String s) {
		s = s.replace(" ", "");

		String[] parts = s.split("[+-]");
		double real = 0, imaginary = 0;
		int pos = -1;

		for (String part : parts) {
			if (part == "i"){
				imaginary = 1;
				continue;
			}
			if (pos != -1) {
				part = s.charAt(pos) + part;
			} else {
				pos = 0;
				if ("".equals(part)) {
					continue;
				}
			}
			pos += part.length();
			if (part.lastIndexOf('i') == -1) {
				if (!"+".equals(part) && !"-".equals(part)) {
					real += Double.parseDouble(part);
				}
			} else {
				part = part.replace("i", "");
				if ("+".equals(part)) {
					imaginary++;
				} else if ("-".equals(part)) {
					imaginary--;
				} else {
					imaginary += Double.parseDouble(part);
				}
			}
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Gets the real part.
	 *
	 * @return the real part
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary part.
	 *
	 * @return the imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates and returns the magnitude of the complex number in polar
	 * coordinates.
	 * 
	 * @return the magnitude of the complex number.
	 */
	public double getMagnitude() {
		return Math.hypot(real, imaginary);
	}

	/**
	 * Calculates and returns the angle of the complex number in polar
	 * coordinates.
	 * 
	 * @return the angle of the complex number.
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Calculates and returns the reciprocal value of the complex number
	 *
	 * @return the reciprocal complex number
	 */
	public ComplexNumber rec() {
		// I needed the reciprocal value of a ComplexNumber for division, so I
		// implemented it as a method

		double squaredNorm = real * real + imaginary * imaginary;

		return new ComplexNumber(real / squaredNorm, -imaginary / squaredNorm);
	}

	/**
	 * Constructs a new ComplexNumber by adding another ComplexNumber to this
	 * ComplexNumber.
	 *
	 * @param c
	 *            the ComplexNumber to add to this ComplexNumber
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Constructs a new ComplexNumber by subtracting another ComplexNumber from
	 * this ComplexNumber.
	 *
	 * @param c
	 *            the ComplexNumber to subtract from to this ComplexNumber
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Constructs a new ComplexNumber by multiplying another ComplexNumber with
	 * this ComplexNumber.
	 *
	 * @param c
	 *            the ComplexNumber to multiply with this ComplexNumber
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Constructs a new ComplexNumber by dividing this ComplexNumber with
	 * another ComplexNumber.
	 *
	 * @param c
	 *            the ComplexNumber to divide this ComplexNumber with
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		// division equals to multiplication with the reciprocal value
		return this.mul(c.rec());
	}

	/**
	 * Constructs a new ComplexNumber by raising this ComplexNumber to the nth power.
	 *
	 * @param n
	 *            the power to raise the ComplexNumber to
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("The power has to be grater than or equal to 0.");
		}

		ComplexNumber num = this;
		for (int i = 1; i < n; i++) {
			num = num.mul(this);
		}

		return num;
	}

	/**
	 * Calculates and returns the array of nth roots of this complex number.
	 *
	 * @param n
	 *            the required root to calculate
	 * @return the newly constructed ComplexNumber
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("The root order has to be grater than 0.");
		}

		ComplexNumber[] roots = new ComplexNumber[n];

		double thisAngle = this.getAngle();
		double thisMagnitude = this.getMagnitude();
		for (int i = 0; i < n; i++) {
			double angle = thisAngle / n + 2 * Math.PI * i / n;
			double magnitude = Math.pow(thisMagnitude, 1.0 / n);

			roots[i] = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
		}

		return roots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		DecimalFormat format = new DecimalFormat("0.######");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		char sign = (imaginary < 0) ? '-' : '+';

		String formatReal = format.format(real);
		String formatImaginary = format.format(Math.abs(imaginary)) + "i";

		if (real == 0) {
			formatReal = "";
		}
		if (imaginary == 0) {
			sign = ' ';
			formatImaginary = "";
		}
		if (imaginary == 1){
			formatImaginary = "i";
		}
		if (real == 0 && imaginary == 0) {
			formatReal = "0";
		}

		return String.format("%s %c %s", formatReal, sign, formatImaginary).trim();
	}
}
