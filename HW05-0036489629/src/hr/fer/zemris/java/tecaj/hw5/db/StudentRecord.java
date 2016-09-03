package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The Class StudentRecord. Represents a single student record.
 * 
 * @author Juraj Juričić
 */
public class StudentRecord {

	/** The jmbag. */
	private String jmbag;

	/** The last name. */
	private String lastName;

	/** The first name. */
	private String firstName;

	/** The final grade. */
	private int finalGrade;

	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @param lastName
	 *            the last name
	 * @param firstName
	 *            the first name
	 * @param finalGrade
	 *            the final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Gets the jmbag.
	 *
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade.
	 *
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
