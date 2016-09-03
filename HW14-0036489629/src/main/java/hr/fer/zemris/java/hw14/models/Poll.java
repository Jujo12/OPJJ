package hr.fer.zemris.java.hw14.models;

/**
 * The model Poll. Represents a sigle entry in Polls table.
 *
 * @author Juraj Juričić
 */
public class Poll {
	
	/** The poll id. */
	private long id;
	
	/** The poll title. */
	private String title;
	
	/** The poll message. */
	private String message;

	/**
	 * Instantiates a new poll.
	 *
	 * @param id the id
	 * @param title the title
	 * @param message the message
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Instantiates a new poll.
	 *
	 * @param id the id
	 * @param title the title
	 */
	public Poll(long id, String title) {
		this(id, title, null);
	}

	/**
	 * Instantiates a new poll.
	 */
	public Poll(){

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Poll option id="+id;
	}
}