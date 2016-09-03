package hr.fer.zemris.java.hw15;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The Initialization listener. Initializes the entity manager factory.
 *
 * @author Juraj Juričić
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("baza.podataka.za.blog");

		sce.getServletContext().setAttribute("theEMF", emf);

		JPAEMFProvider.setEmf(emf);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);

		EntityManagerFactory emf = (EntityManagerFactory) sce
				.getServletContext().getAttribute("theEMF");

		if (emf != null) {
			emf.close();
		}
	}
}