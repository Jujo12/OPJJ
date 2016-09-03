package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * The JPA EntityManager provider. Stores Entity Managers in a ThreadLocal map.
 *
 * @author Juraj Juričić
 */
public class JPAEMProvider {

	/** The thread local map of EntityManagers. */
	private static ThreadLocal<EntityManager> ems = new ThreadLocal<>();

	/**
	 * Gets the entity manager for current thread.
	 *
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {

		EntityManager em = ems.get();

		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			ems.set(em);
		}

		return em;
	}

	/**
	 * Commits the transaction and closes the entity manager.
	 *
	 * @throws DAOException the DAO exception
	 */
	public static void close() throws DAOException {
		EntityManager em = ems.get();
		if (em == null) {
			return;
		}

		try {
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			try {
				em.close();
				ems.remove();
			} catch (Exception ignorable) {}
		}

	}

}