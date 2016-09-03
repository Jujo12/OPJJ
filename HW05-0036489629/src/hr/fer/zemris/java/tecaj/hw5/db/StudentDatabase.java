package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;

/**
 * The Class StudentDatabase. Represents a database of StudentRecords, indexed
 * by JMBAG.
 * 
 * @author Juraj Juričić
 */
public class StudentDatabase {

	/** The records. */
	List<StudentRecord> records = new ArrayList<>();

	/**
	 * The map of indexes. Key is jmbag, value is index of the value stored in
	 * records list.
	 */
	SimpleHashtable<String, Integer> indexMap = new SimpleHashtable<>();

	/**
	 * Instantiates a new student database. Parses the input list of rows for
	 * the data.
	 *
	 * @param rows
	 *            the rows
	 */
	public StudentDatabase(List<String> rows) {
		for (String row : rows) {
			/*
			 * Structure: JMBAG lastName firstName finalGrade
			 */

			String[] rowData = row.split("\\t");
			try {
				StudentRecord record = new StudentRecord(rowData[0], rowData[1], rowData[2],
						Integer.parseInt(rowData[3]));

				int index = records.size();
				records.add(record);
				indexMap.put(rowData[0], index);
			} catch (NumberFormatException e) {
				System.err.println("Invalid final grade format.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Invalid db file structure");
			} catch (Exception e) {
				System.err.println("Unexpected error occured.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Searches the database for the student with given jmbag and returns the
	 * record. Returns null if the record is not found.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @return the student record
	 */
	public StudentRecord forJmbag(String jmbag) {
		Integer index = indexMap.get(jmbag);
		if (index == null) {
			return null;
		}

		return records.get(index);
	}

	/**
	 * Returns the list of records that satisfy the IFilter provided.
	 *
	 * @param filter
	 *            the filter to check the values against
	 * @return the list of records that passed the filter.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				temp.add(record);
			}
		}

		return temp;
	}

}
