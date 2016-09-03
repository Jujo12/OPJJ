package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Servlet that parses the voting results and outputs them to a JSP page
 * glasanjeRez.jsp. <br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long id;

		try {
			id = Integer.parseInt(req.getParameter("pollID"));
		} catch (Exception e) {
			req.setAttribute("message", "Poll ID is invalid");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		try {
			List<PollOption> options = DAOProvider.getDao().getAllPollOptions(id);

			long maxScore = options.get(0).getVotesCount();
			List<PollOption> topResults = options.parallelStream().filter(x -> x.getVotesCount() == maxScore).collect(Collectors.toList());

			req.setAttribute("pollID", id);
			req.setAttribute("options", options);
			req.setAttribute("topResults", topResults);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRezultati.jsp")
					.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
