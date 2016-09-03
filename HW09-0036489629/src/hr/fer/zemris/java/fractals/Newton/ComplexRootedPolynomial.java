package hr.fer.zemris.java.fractals.Newton;

/**
 * The class represents a root-based complex polynomial.
 *
 * @author Juraj Juričić
 */
public class ComplexRootedPolynomial {

	/** The array of roots. */
	private Complex[] roots;

	/**
	 * Instantiates a new complex rooted polynomial with the given roots.
	 *
	 * @param roots
	 *            the roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z
	 *            the point z
	 * @return the value of the polynomial
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;

		for (Complex r : roots) {
			result = result.multiply(z.sub(r));
		}

		return result;
	}

	/**
	 * Converts this representation to {@link ComplexPolynomial} type. Returns a
	 * newly constructed {@link ComplexPolynomial}.
	 *
	 * @return the newly constructed {@link ComplexPolynomial} object.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);

		for (Complex r : roots) {
			ComplexPolynomial mulVal = new ComplexPolynomial(Complex.ONE,
					r.negate());
			result = result.multiply(mulVal);
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
		if (roots.length == 0) {
			return "No roots.";
		}

		StringBuilder sb = new StringBuilder();
		for (Complex r : roots) {
			sb.append("(z-(");
			sb.append(r.toString());
			sb.append("))");
		}

		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within the
	 * threshold. If there is no such root, returns -1.
	 *
	 * @param z
	 *            the complex number z
	 * @param threshold
	 *            the threshold
	 * @return the index, or -1 if no root satisfies the threshold
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		Double min = null;

		int i = 0;
		for (Complex r : roots) {
			double delta = z.sub(r).module();
			if (min == null || delta < min) {
				if (delta < threshold) {
					min = delta;
					index = i;
				}
			}

			i++;
		}

		return index;
	}
}