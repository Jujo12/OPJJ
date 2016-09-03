package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The Interface IFilter. Filters the StudentRecords based on the given
 * parameter.
 * 
 * @author Juraj Juričić
 */
public interface IFilter {

	/**
	 * Returns true if the record satisfies the filter, false otherwise.
	 *
	 * @param record
	 *            the record
	 * @return true if the record satisfied the filter, false otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
