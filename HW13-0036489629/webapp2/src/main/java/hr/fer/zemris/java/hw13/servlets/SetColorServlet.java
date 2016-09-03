package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Servlet used for storing background color. Accepts a single parameter:
 * color, which should contain one of the following values: {white, red, green,
 * cyan}.
 *
 * @author Juraj Juričić
 */
public class SetColorServlet extends HttpServlet {

	/** The map containing color mapping. */
	private static final Map<String, ColorCombination> colors = new HashMap<>();

	static {
		colors.put("white", new ColorCombination("#EFEFEF", "white", "rgba(0, 0, 0, 0.87)"));
		colors.put("green", new ColorCombination("#4CAF50", "#66BB6A", "rgba(0, 0, 0, 0.87)"));
		colors.put("red", new ColorCombination("#B71C1C", "#C62828", "rgba(255, 255, 255, 0.87)"));
		colors.put("cyan", new ColorCombination("#00BCD4", "#26C6DA", "rgba(0, 0, 0, 0.87)"));
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String queryColor = req.getParameter("color");

		resp.setContentType("text/html; charset=UTF-8");

		if (!colors.containsKey(queryColor)) {
			req.setAttribute("message", "Unknown color selected: " + queryColor);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		req.getSession().setAttribute("pickedBgCol", colors.get(queryColor));
		resp.sendRedirect("/webapp2/colors.jsp");
	}

	/**
	 * The class that stores color combinations. Stored are two background colors and one foreground color.
	 *
	 * @author Juraj Juričić
	 */
	public static class ColorCombination {
		
		/** The background color. */
		private String bgColor;
		
		/** The second background color. */
		private String bgColor2;
		
		/** The foreground color. */
		private String fgColor;

		/**
		 * Instantiates a new color combination.
		 *
		 * @param bgColor the bg color
		 * @param bgColor2 the second bg color
		 * @param fgColor the fg color
		 */
		public ColorCombination(String bgColor, String bgColor2, String fgColor) {
			super();
			this.bgColor = bgColor;
			this.bgColor2 = bgColor2;
			this.fgColor = fgColor;
		}

		/**
		 * Gets the bg color.
		 *
		 * @return the bgColor
		 */
		public String getBgColor() {
			return bgColor;
		}

		/**
		 * Gets the second bg color.
		 *
		 * @return the bgColor
		 */
		public String getBgColor2() {
			return bgColor2;
		}

		/**
		 * Gets the fg color.
		 *
		 * @return the fgColor
		 */
		public String getFgColor() {
			return fgColor;
		}

	}
}
