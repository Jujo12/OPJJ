package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * The Servlet that parses the voting definition file and outputs the bands to a
 * JSP page glasanjeIndex.jsp.<br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int pollID;

		try{
			pollID = Integer.parseInt(req.getParameter("pollID").toString());
		}catch(Exception e){
			req.setAttribute("message", "Invalid poll ID: " + e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);

			return;
		}

		try {
			Poll poll = DAOProvider.getDao().getPoll(pollID);
			if (poll == null){
				req.setAttribute("message", "Unknown poll ID provided.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
						resp);

				return;
			}

			req.setAttribute("title", poll.getTitle());
			req.setAttribute("message", poll.getMessage());

			List<PollOption> options = DAOProvider.getDao().getAllPollOptions(pollID);

			req.setAttribute("options", options);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
					.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
