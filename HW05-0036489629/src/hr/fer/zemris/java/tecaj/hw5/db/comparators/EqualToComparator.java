package hr.fer.zemris.java.tecaj.hw5.db.comparators;

/**
 * The implementation of {@link IComparisonOperator} that checks if the two
 * strings are equal.
 * 
 * @author Juraj Juričić
 */
public class EqualToComparator implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return value1.equals(value2);
	}

}
