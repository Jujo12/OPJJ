package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The {@link IFieldValueGetter} that gets the jmbag.
 * 
 * @author Juraj Juričić
 */
public class JmbagValueGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
