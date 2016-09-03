package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The {@link IFieldValueGetter} that gets the firstName.
 * 
 * @author Juraj Juričić
 */
public class FirstNameValueGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
