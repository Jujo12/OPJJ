package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The Interface IFieldValueGetter. Gets the particular field of a record.
 * 
 * @author Juraj Juričić
 */
public interface IFieldValueGetter {

	/**
	 * Gets the particular field of a StudentRecord.
	 *
	 * @param record
	 *            the record
	 * @return the string
	 */
	public String get(StudentRecord record);
}
