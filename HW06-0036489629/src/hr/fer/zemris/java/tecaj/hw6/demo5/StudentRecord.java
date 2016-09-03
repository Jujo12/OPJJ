package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * The StudentRecord class that repressents a single student record.
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

	/** The midterm score. */
	private double scoreMI;

	/** The finals score. */
	private double scoreZI;

	/** The lab exercise score. */
	private double scoreLAB;

	/** The final grade. */
	private int grade;

	/**
	 * Instantiates a new student record from the array containing data. The
	 * array should have exactly 7 elements in this particular order: jmbag,
	 * lastName, firstName, scoreMI, scoreZI, scoreLAB, grade.
	 *
	 * @param data
	 *            the data array containing the student's data
	 */
	StudentRecord(String[] data) {
		super();
		if (data.length != 7) {
			throw new IllegalArgumentException("Invalid input structure.");
		}

		try {
			this.jmbag = data[0];
			this.lastName = data[1];
			this.firstName = data[2];
			this.scoreMI = Double.parseDouble(data[3]);
			this.scoreZI = Double.parseDouble(data[4]);
			this.scoreLAB = Double.parseDouble(data[5]);
			this.grade = Integer.parseInt(data[6]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid input number format.");
		}

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
	 * Gets the score mi.
	 *
	 * @return the scoreMI
	 */
	public double getScoreMI() {
		return scoreMI;
	}

	/**
	 * Gets the score zi.
	 *
	 * @return the scoreZI
	 */
	public double getScoreZI() {
		return scoreZI;
	}

	/**
	 * Gets the score lab.
	 *
	 * @return the scoreLAB
	 */
	public double getScoreLAB() {
		return scoreLAB;
	}

	/**
	 * Gets the grade.
	 *
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Calculates and returns the total score. Total score is defined as the sum of scoreMI, scoreZI, and scoreLAB.
	 *
	 * @return the total score
	 */
	public double getTotalScore() {
		return scoreMI + scoreZI + scoreLAB;
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

}
