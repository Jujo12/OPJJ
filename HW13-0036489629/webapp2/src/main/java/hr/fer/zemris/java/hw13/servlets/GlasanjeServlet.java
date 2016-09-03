package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.servlets.GlasanjeUtilities.Band;

/**
 * The Servlet that parses the voting definition file and outputs the bands to a
 * JSP page glasanjeIndex.jsp. File path is stored in {@link GlasanjeUtilities}
 * class. <br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext()
				.getRealPath(GlasanjeUtilities.FILE_DEF);

		try {
			List<Band> bands = GlasanjeUtilities.loadBands(Paths.get(fileName),
					null);

			req.setAttribute("bands", bands);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
					.forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

}
