package hr.fer.zemris.java.tecaj.hw5.db.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.DbQueryException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparatorType;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.RecordFields;

/**
 * The Class QueryFilter. Filters the records based on the given query string.
 * 
 * @author Juraj Juričić
 */
public class QueryFilter implements IFilter {

	/** The split expressions to filter against. */
	private ConditionalExpression[] expressions;

	/**
	 * Instantiates a new query filter.
	 *
	 * @param queryString
	 *            the query string to split and parse for comparison queryies
	 */
	public QueryFilter(String queryString) {
		String[] comparisons = QueryParser.splitComparisons(queryString);

		expressions = new ConditionalExpression[comparisons.length];
		for (int i = 0; i < comparisons.length; i++) {
			expressions[i] = QueryParser.parseComparison(comparisons[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter#accepts(hr.fer.zemris.
	 * java.tecaj.hw5.db.StudentRecord)
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean recordSatisfies = false;

		for (ConditionalExpression expression : expressions) {
			recordSatisfies = expression.getComparator().satisfied(expression.getFieldGetter().get(record),
					expression.getQuery());

			if (!recordSatisfies) {
				return false;
			}
		}

		return recordSatisfies;
	}

	/**
	 * The query parser.
	 */
	private static class QueryParser {

		/**
		 * Splits the comparisons on the AND keyword outside of the quotation
		 * marks. Case insensitive.
		 *
		 * @param queryString
		 *            the query string to split
		 * @return the string[] array of split comparison strings
		 */
		public static String[] splitComparisons(String queryString) {
			return queryString.split("(?xi)   " + // case insensitive
					"\\s+and\\s+" + // split on "and" keyword
					"(?=        " + // followed by
					"  (?:      " + //
					"    [^\"]* " + // 0 or more non-quote characters
					"    \"     " + // 1 quote
					"    [^\"]* " + // 0 or more non-quote characters
					"    \"     " + // 1 quote
					"  )*       " + //
					"  [^\"]*   " + // 0 or more non-quote characters
					"  $        " + // till the end (this is necessary, else
									// every "and" will satisfy the condition)
					")          " // end look-ahead
			);
		}

		/**
		 * Parses the given comparison string. Constructs a new
		 * {@link ConditionalExpression} based on the given string.
		 *
		 * @param query
		 *            the query
		 * @return the constructed conditional expression
		 */
		public static ConditionalExpression parseComparison(String query) {
			// get field name
			query = query.trim();

			String pattern = "(?xi)			" // Case insensitive matching
					+ " ^					" // beginning of line
					+ " ([a-z]+)				" // field name (first group)
					+ "   \\s* 				" // whitespace (or not)
					+ "     ([!<>=]*|(?:LIKE)*)" // matching symbol
					+ "   \\s*				" // whitespace (or not)
					+ " \"([a-z0-9čćđšž*\\s]*)\"		"; // search content
			
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(query);

			if (!m.matches()) {
				throw new DbQueryException("Invalid command (query) syntax.");
			}

			/*
			 * Matcher groups: 1 - field name 2 - operator 3 - content
			 */
			if (m.groupCount() != 3) {
				throw new DbQueryException("Invalid command (query) syntax.");
			}

			ConditionalExpression exp = null;
			try {
				IFieldValueGetter fieldGetter = RecordFields.valueOf(m.group(1)).getValueGetter();
				IComparisonOperator comparator = ComparatorType.getComparatorType(m.group(2)).getComparator();

				exp = new ConditionalExpression(fieldGetter, m.group(3), comparator);
			} catch (IllegalArgumentException e) {
				throw new DbQueryException("Invalid syntax.");
			}

			return exp;
		}
	}

}
