package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.UpdateableObject;

import java.util.List;

/**
 * The blog DAO interface, used for interacting with blog objects' storage
 * .
 * @author Juraj Juričić
 */
public interface DAO {

	/**
	 * Gets the blog user for given nick.
	 *
	 * @param nick the nick
	 * @return the blog user
	 * @throws DAOException the DAO exception
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Gets the blog entry for given ID.
	 *
	 * @param id the entry id
	 * @return the blog entry
	 * @throws DAOException the DAO exception
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Gets the list of all users.
	 *
	 * @return all users
	 * @throws DAOException the DAO exception
	 */
	public List<BlogUser> getAllUsers() throws DAOException;

	/**
	 * Gets the list of blog entries for given user.
	 *
	 * @param u the blog user
	 * @return blog entries
	 * @throws DAOException the DAO exception
	 */
	public List<BlogEntry> getBlogEntries(BlogUser u) throws DAOException;

	/**
	 * Updates or adds the given object to storage..
	 *
	 * @param e the object to store
	 */
	public void updateOrAdd(UpdateableObject e);

}
