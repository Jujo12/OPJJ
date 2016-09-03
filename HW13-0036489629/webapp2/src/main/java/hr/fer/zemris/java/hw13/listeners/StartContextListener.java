package hr.fer.zemris.java.hw13.listeners;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The listener for web application context. Stores start time as a {@link Date}
 * object under attribute startTime. That can later be used for determining
 * application uptime.
 */
public class StartContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", new Date());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
