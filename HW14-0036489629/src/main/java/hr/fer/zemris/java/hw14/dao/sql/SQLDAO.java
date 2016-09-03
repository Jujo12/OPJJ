package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * The database-driven DAO implementation.
 * Expects an existing connection to be provided by {@link SQLConnectionProvider}.
 *
 * @author Juraj Juričić
 */
public class SQLDAO implements DAO {
	@Override
	public List<Poll> getAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst;

		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						polls.add(new Poll(rs.getLong(1), rs.getString(2)));
					}
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ignorable) {
						}
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while adding the poll to list.", ex);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst;

		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
					}
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ignorable) {}
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll info.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOption> getAllPollOptions(long pollID) {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst;

		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount FROM pollOptions WHERE pollID=? ORDER BY votesCount DESC, id");
			pst.setLong(1, pollID);

			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						options.add(new PollOption(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getLong(5)));
					}
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ignorable) {}
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while adding the option to list.", ex);
		}
		return options;
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption option = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst;

		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from PollOptions where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						option = new PollOption(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getLong(5));
					}
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ignorable) {}
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll info.", ex);
		}
		return option;
	}

	@Override
	public void pollOptionVote(long optionID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst;

		try {
			pst = con.prepareStatement("UPDATE pollOptions SET votesCount = votesCount+1 WHERE id=?");
			pst.setLong(1, optionID);

			pst.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("Error while adding the option to list.", ex);
		}
	}
}