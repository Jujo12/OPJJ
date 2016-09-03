package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * The Servlet used for casting the vote in the voting system. The servlet
 * accepts one request parameter, <i>id</i>, that represents the ID of the band
 * the user has voted for.
 *
 * @author Juraj Juričić
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long id;

		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (Exception e) {
			req.setAttribute("message", "Attribute ID is invalid");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		try {
			PollOption option = DAOProvider.getDao().getPollOption(id);
			if (option == null){
				req.setAttribute("message", "Invalid option ID");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
						resp);

				return;
			}

			DAOProvider.getDao().pollOptionVote(id);

			long pollID = option.getPollID();
			resp.sendRedirect("glasanje-rezultati?pollID="+pollID);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
