package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.servlets.GlasanjeUtilities.Band;

/**
 * The Servlet that parses the voting results and outputs them to a JSP page
 * glasanjeRez.jsp. File paths are stored in {@link GlasanjeUtilities} class.
 * <br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String defFile = req.getServletContext()
				.getRealPath(GlasanjeUtilities.FILE_DEF);
		String resFile = req.getServletContext()
				.getRealPath(GlasanjeUtilities.FILE_RES);

		try {
			List<Band> bands = GlasanjeUtilities.loadBands(Paths.get(defFile),
					Paths.get(resFile));
			req.setAttribute("bands", bands);

			List<Band> topBands = new ArrayList<>();
			int maxScore = bands.get(0).getScore();
			for (Band band : bands) {
				if (band.getScore() == maxScore) {
					topBands.add(band);
				}
			}
			req.setAttribute("topBands", topBands);

			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp")
					.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
