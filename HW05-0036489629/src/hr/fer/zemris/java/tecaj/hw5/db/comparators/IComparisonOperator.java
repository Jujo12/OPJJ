package hr.fer.zemris.java.tecaj.hw5.db.comparators;

/**
 * The Interface IComparisonOperator. Implementations should compare two strings
 * in a way defined by their contract.
 * 
 * @author Juraj Juričić
 */
public interface IComparisonOperator {

	/**
	 * Returns true if the strings are in the given relation.
	 *
	 * @param value1
	 *            the value1
	 * @param value2
	 *            the value2
	 * @return true if the strings satisfy the comparison, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
