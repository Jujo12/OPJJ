package hr.fer.zemris.java.tecaj.hw5.db.comparators;

/**
 * The implementation of {@link IComparisonOperator} that checks if the first
 * string matches the LIKE query of given by the second stirng. The LIKE query
 * can contain a SINGLE wildcard *.
 * 
 * @author Juraj Juričić
 */
public class LikeComparator implements IComparisonOperator {

	/**
	 * Returns true if the value1 matches the LIKE query given as value2.
	 * 
	 * @param value1
	 *            The value to match against the given query.
	 * @param value2
	 *            The query containing a wildcard (*).
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		// max. 1 wildcard
		if (value2.split("\\*").length - 1 > 1) {
			throw new IllegalArgumentException("Only one wildcard allowed.");
		}

		String regex = value2.replaceFirst("\\*", "(.)*");

		return value1.matches(regex);
	}

}
