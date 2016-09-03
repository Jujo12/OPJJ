package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.UpdateableObject;

/**
 * The JPA implementation of DAO interface.
 *
 * @author Juraj Juričić
 */
public class JPADAOImpl implements DAO {

	@SuppressWarnings("unchecked")
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> result = JPAEMProvider.getEntityManager().createQuery("SELECT u from BlogUser u WHERE nick=:nick")
				.setParameter("nick", nick).getResultList();

		if (result.isEmpty()){
			return null;
		}

		return result.get(0);
	}

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogUser> getAllUsers() throws DAOException {
		return JPAEMProvider.getEntityManager().createQuery("SELECT u FROM BlogUser u").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogEntry> getBlogEntries(BlogUser u) throws DAOException {
		if (u == null){
			return new ArrayList<>();
		}

		return (List<BlogEntry>) JPAEMProvider.getEntityManager().createQuery("SELECT e FROM BlogEntry e WHERE creator=:user")
				.setParameter("user", u).getResultList();
	}

	@Override
	public void updateOrAdd(UpdateableObject e){
		EntityManager em = JPAEMProvider.getEntityManager();

		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}

		if(e.getId() != null) {
			em.merge(e);
		} else {
			em.persist(e);
		}

		em.getTransaction().commit();
	}
}
