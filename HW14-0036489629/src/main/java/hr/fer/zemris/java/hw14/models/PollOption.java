package hr.fer.zemris.java.hw14.models;

/**
 * The model PollOption. Represents a sigle entry in Polloptions table.
 *
 * @author Juraj Juričić
 */
public class PollOption {
	
	/** The option id. */
	private long id;
	
	/** The option title. */
	private String optionTitle;
	
	/** The option link. */
	private String optionLink;
	
	/** The associated poll id. */
	private long pollID;
	
	/** The votes count (score). */
	private long votesCount;

	/**
	 * Instantiates a new poll option.
	 *
	 * @param id the id
	 * @param optionTitle the option title
	 * @param optionLink the option link
	 * @param pollID the poll id
	 * @param votesCount the votes count
	 */
	public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Instantiates a new poll option.
	 */
	public PollOption() {
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
	 * Gets the option title.
	 *
	 * @return the option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title.
	 *
	 * @param optionTitle the new option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link.
	 *
	 * @param optionLink the new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Gets the poll id.
	 *
	 * @return the poll id
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll id.
	 *
	 * @param pollID the new poll id
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votes count.
	 *
	 * @param votesCount the new votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public String toString() {
		return "Poll option id="+id;
	}
}