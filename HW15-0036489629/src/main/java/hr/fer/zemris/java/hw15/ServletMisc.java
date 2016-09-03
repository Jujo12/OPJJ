package hr.fer.zemris.java.hw15;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The helper class providing miscellaneous servlet functions.
 *
 * @author Juraj Juričić
 */
public class ServletMisc {
	
	/**
	 * Displays the error to the user via HTTP.
	 *
	 * @param req the HttpServletRequest
	 * @param resp the HttpServletResponse
	 * @param message the message
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void error(HttpServletRequest req, HttpServletResponse resp,
			                         String message)
		throws ServletException, IOException{
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
}
