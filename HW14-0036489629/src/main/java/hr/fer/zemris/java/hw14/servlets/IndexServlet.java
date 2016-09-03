package hr.fer.zemris.java.hw14.servlets;

/**
 * Created by juraj on 14.6.2016..
 */

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * The Servlet that shows index of all polls in database.<br>
 * Accepts no request parameters.
 *
 * @author Juraj Juričić
 */
@WebServlet(urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			List<Poll> polls = DAOProvider.getDao().getAllPolls();

			req.setAttribute("polls", polls);
			req.getRequestDispatcher("/WEB-INF/pages/pollIndex.jsp")
					.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
