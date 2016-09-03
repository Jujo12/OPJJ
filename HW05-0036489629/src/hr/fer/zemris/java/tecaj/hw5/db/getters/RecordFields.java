package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.DbQueryException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The Enum RecordFields. Contains the fields of {@link StudentRecord} that can
 * be filtered against.
 * 
 * @author Juraj Juričić
 */
public enum RecordFields {

	/** The first name. */
	firstName(FirstNameValueGetter.class),

	/** The last name. */
	lastName(LastNameValueGetter.class),

	/** The jmbag. */
	jmbag(JmbagValueGetter.class);

	/** The getter class. */
	private Class<? extends IFieldValueGetter> getterClass;

	/**
	 * Instantiates a new record fields.
	 *
	 * @param getterClass
	 *            the getter class
	 */
	RecordFields(Class<? extends IFieldValueGetter> getterClass) {
		this.getterClass = getterClass;
	}

	/**
	 * Constructs a new value getter instance.
	 *
	 * @return the value getter
	 */
	public IFieldValueGetter getValueGetter() {
		try {
			return getterClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DbQueryException("A fatal error occured.");
		}
	}

}
