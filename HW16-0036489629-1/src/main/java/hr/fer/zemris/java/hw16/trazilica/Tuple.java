package hr.fer.zemris.java.hw16.trazilica;

/**
 * The tuple class that stores an ordered pair of objects.
 *
 * @author Juraj Juričić
 * @param <X> the type of the first element in pair
 * @param <Y> the type of the second element in pair
 */
public class Tuple<X, Y> {
	
	/** The first element. */
	public final X x;
	
	/** The second element. */
	public final Y y;

	/**
	 * Instantiates a new tuple.
	 *
	 * @param x the first element
	 * @param y the second element
	 */
	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tuple<?, ?> tuple = (Tuple<?, ?>) o;

		if (x != null ? !x.equals(tuple.x) : tuple.x != null) return false;
		return y != null ? y.equals(tuple.y) : tuple.y == null;

	}

	@Override
	public int hashCode() {
		int result = x != null ? x.hashCode() : 0;
		result = 31 * result + (y != null ? y.hashCode() : 0);
		return result;
	}
}