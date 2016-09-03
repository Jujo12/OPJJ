package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Servlet used for casting the vote in the voting system. The servlet
 * accepts one request parameter, <i>id</i>, that represents the ID of the band
 * the user has voted for. The ID is not checked.<br>
 * The data is then saved to results file, which path is stored in
 * {@link GlasanjeUtilities} class.
 *
 * @author Juraj Juričić
 */
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// get ID
		int id;

		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (Exception e) {
			req.setAttribute("message", "Attribute ID is invalid");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		Path resultsPath = Paths.get(req.getServletContext()
				.getRealPath(GlasanjeUtilities.FILE_RES));

		try {
			Map<Integer, Integer> votes;
			if (Files.exists(resultsPath)) {
				votes = GlasanjeUtilities.getCurrentVotes(resultsPath);
			} else {
				votes = new TreeMap<>();
			}

			votes.merge(id, 1, Integer::sum);

			GlasanjeUtilities.saveVotes(votes, resultsPath);

			resp.sendRedirect("glasanje-rezultati");
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
