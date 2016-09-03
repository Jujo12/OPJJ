package hr.fer.zemris.java.fractals.Newton;

import java.util.Arrays;

/**
 * The class represents a coefficient-based complex polynomial.
 *
 * @author Juraj Juričić
 */
public class ComplexPolynomial {

	/** The array of factors */
	private Complex[] factors;

	/**
	 * Instantiates a new complex polynomial with the given factors.
	 *
	 * @param factors
	 *            the factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 *
	 * @return the order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Computes a new polynomial this * p, where p is another polynomial.
	 *
	 * @param p
	 *            the p
	 * @return the complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[factors.length + p.factors.length
				- 1];

		// using "this" for clear distinction between this and p.
		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				Complex rez = this.factors[i].multiply(p.factors[j]);

				if (newFactors[i + j] == null) {
					newFactors[i + j] = rez;
				} else {
					newFactors[i + j] = newFactors[i + j].add(rez);
				}
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] derived = new Complex[factors.length - 1];

		for (int i = 0, order = this.order(); i < order; i++) {
			int power = order - i;

			derived[i] = factors[i].multiply(new Complex(power, 0));
		}

		return new ComplexPolynomial(derived);
	}

	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z
	 *            the point z
	 * @return the value of the polynomial
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		int order = order();

		for (int i = 0; i <= order; i++) {
			int n = order - i;
			Complex val = factors[i].multiply(z.power(n));
			result = result.add(val);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int power = order();

		for (Complex f : factors) {
			if (f.equals(Complex.ZERO))
				continue;

			if (sb.length() > 0) {
				sb.append(" + ");
			}

			sb.append('(');
			sb.append(f.toString());
			sb.append(')');

			sb.append('z');
			if (power > 1) {
				sb.append("^");
				sb.append(power);
			}

			power--;
		}

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (!Arrays.equals(factors, other.factors))
			return false;
		return true;
	}
	
	
}