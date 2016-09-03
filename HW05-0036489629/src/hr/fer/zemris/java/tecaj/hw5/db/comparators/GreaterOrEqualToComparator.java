package hr.fer.zemris.java.tecaj.hw5.db.comparators;

/**
 * The implementation of {@link IComparisonOperator} that checks if the first
 * string is lexicographically greater than or equal to the other.
 * 
 * @author Juraj Juričić
 */
public class GreaterOrEqualToComparator implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return value1.compareTo(value2) >= 0;
	}

}
