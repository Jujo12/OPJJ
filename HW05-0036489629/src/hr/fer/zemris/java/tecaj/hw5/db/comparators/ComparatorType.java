package hr.fer.zemris.java.tecaj.hw5.db.comparators;

import hr.fer.zemris.java.tecaj.hw5.db.DbQueryException;

/**
 * The Enum ComparatorType. Contains all comparator types and their comparison operator classes.
 * 
 * @author Juraj Juričić
 */
public enum ComparatorType {
	
	/** The greater than comparison operator. */
	GTHAN(">", GreaterThanComparator.class),
	
	/** The less than comparison operator. */
	LTHAN("<", LessThanComparator.class),
	
	/** The greater than or equal to comparison operator. */
	GETO(">=", GreaterOrEqualToComparator.class),
	
	/** The less than or equal to comparison operator. */
	LETO("<=", LessOrEqualToComparator.class),
	
	/** The equal to comparison operator. */
	ETO("=", EqualToComparator.class),
	
	/** The not equal to comparison operator. */
	NETO("!=", NotEqualToComparator.class),
	
	/** The like comparison operator. */
	LIKE("LIKE", LikeComparator.class);

	/** The operator. */
	private String operator;
	
	/** The comparison operator class. */
	private Class<? extends IComparisonOperator> comparatorClass;

	/**
	 * Instantiates a new comparator type.
	 *
	 * @param operator the operator
	 * @param comparatorClass the comparison operator class
	 */
	ComparatorType(String operator, Class<? extends IComparisonOperator> comparatorClass) {
		this.operator = operator;
		this.comparatorClass = comparatorClass;
	}

	/**
	 * Instantiates a new comparator object.
	 *
	 * @return the comparator
	 */
	public IComparisonOperator getComparator() {
		try {
			return (IComparisonOperator) comparatorClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DbQueryException("A fatal error occured.");
		}
	}

	/**
	 * Gets the comparator type based on the operator (<, >, <=, >=, !=, =, LIKE).
	 *
	 * @param operator the operator
	 * @return the comparator type
	 */
	public static ComparatorType getComparatorType(String operator) {
		for (ComparatorType comparator : values()) {
			if (comparator.operator.equals(operator)) {
				return comparator;
			}
		}
		throw new IllegalArgumentException("Invalid operator.");
	}
}
