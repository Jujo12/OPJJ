package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet that generates a css file. The file contains only one style:
 * body's background color. The color is loaded from {@link HttpServletRequest}
 * session param pickedBgColor.
 *
 * @author Juraj Juričić
 */
public class CSSServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String color = (String) req.getSession().getAttribute("pickedBgCol");
		if (color == null) {
			color = "white";
		}

		resp.setContentType("text/css; charset=UTF-8");

		resp.getWriter().write("body{ background-color: " + color + "; } ");
	}
}
