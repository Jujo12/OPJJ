package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The JPA entity manager factory provider.
 *
 * @author Juraj Juričić
 */
public class JPAEMFProvider {

	/** The EntityManagerFactory. */
	public static EntityManagerFactory emf;

	/**
	 * Gets the Entity Manager Factory.
	 *
	 * @return the EntityManagerFactory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the emf.
	 *
	 * @param emf the new emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}