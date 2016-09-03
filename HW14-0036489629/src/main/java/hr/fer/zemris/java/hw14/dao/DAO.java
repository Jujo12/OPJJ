package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

import java.util.List;

/**
 * Data interface that provides methods for working with data structure for polls application.
 *
 * @author Juraj Juričić
 *
 */
public interface DAO {

	/**
	 * Gets all polls, filling only two arguments: id and title.
	 *
	 * @return entry list
	 * @throws DAOException in case of an error
	 */
	public List<Poll> getAllPolls() throws DAOException;

	/**
	 * Gets the Poll with the given ID. If the poll does not exist, returns null.
	 * @param id
	 * @return the Poll
	 * @throws DAOException
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Gets all poll options for the given poll id. The list will be ordered by vote count DESC.
	 * 
	 * @param pollID the Poll ID
	 * @return sorted entry list
	 * @throws DAOException in case of an error
	 */
	public List<PollOption> getAllPollOptions(long pollID) throws DAOException;

	/**
	 * Gets the poll options with the given id. If the poll option does not exist, returns null.
	 *
	 * @param id
	 * @return the Poll option
	 * @throws DAOException in case of an error
	 */
	public PollOption getPollOption(long id) throws DAOException;


	/**
	 * Updates the data in storage environment so that the new data reflects the vote cast to the provided option ID
	 *
	 * @param optionID the id for which the vote should be cast
	 * @throws DAOException in case of an error
	 */
	public void pollOptionVote(long optionID) throws DAOException;

}