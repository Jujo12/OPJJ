package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.comparators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;

/**
 * The Class ConditionalExpression. Instances represent a structured query
 * element.
 * 
 * @author Juraj Juričić
 */
public class ConditionalExpression {

	/** The field getter. */
	private IFieldValueGetter fieldGetter;

	/** The query. */
	private String query;

	/** The comparator. */
	private IComparisonOperator comparator;

	/**
	 * Instantiates a new conditional expression.
	 *
	 * @param fieldGetter
	 *            the field getter
	 * @param query
	 *            the query
	 * @param comparator
	 *            the comparator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String query, IComparisonOperator comparator) {
		if (fieldGetter == null || query == null || comparator == null) {
			throw new IllegalArgumentException();
		}

		this.fieldGetter = fieldGetter;
		this.query = query;
		this.comparator = comparator;
	}

	/**
	 * Gets the field getter.
	 *
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Gets the comparator.
	 *
	 * @return the comparator
	 */
	public IComparisonOperator getComparator() {
		return comparator;
	}

}
