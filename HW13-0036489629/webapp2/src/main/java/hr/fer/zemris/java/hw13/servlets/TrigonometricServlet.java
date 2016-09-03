package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet that calculates the table of trigonometric functions sin(x) and
 * cos(x). The table is determined for all integer angles in degrees in a range
 * determined by URL parameters a and b.<br>
 * If a or b ar missing, assumed default values are a=0, b = 360. If b > a+720,
 * b is set to a+720.
 *
 * @author Juraj Juričić
 */
public class TrigonometricServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a = 0;
		int b = 360;
		try {
			a = Integer.parseInt((String) req.getParameter("a"));
		} catch (Exception ignorable) {}
		try {
			b = Integer.parseInt((String) req.getParameter("b"));
		} catch (Exception ignorable) {}

		if (a > b) {
			// swap a and b
			a = a + b;
			b = a - b;
			a = a - b;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		List<TrigonometricValue> results = new LinkedList<>();
		for (int i = a; i <= b; i++) {
			results.add(new TrigonometricValue(i));
		}

		req.setAttribute("results", results);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp")
				.forward(req, resp);
	}

	/**
	 * The class that stores trigonometric values. Calculates the sine and
	 * cosine values of the given angle in degrees.
	 *
	 * @author Juraj Juričić
	 */
	public static class TrigonometricValue {

		/** The angle in degrees. */
		private int degrees;

		/** The sine value. */
		private double sin;

		/** The cosine value. */
		private double cos;

		/**
		 * Instantiates a new trigonometric value for the given angle in
		 * degrees. Calculates the sine and cosine values and stores them.
		 *
		 * @param degrees
		 *            angle in degrees
		 */
		public TrigonometricValue(int degrees) {
			this.degrees = degrees;

			double rad = Math.toRadians(degrees);
			this.sin = Math.sin(rad);
			this.cos = Math.cos(rad);
		}

		/**
		 * Gets the degrees.
		 *
		 * @return the degrees
		 */
		public int getDegrees() {
			return degrees;
		}

		/**
		 * Gets the sine value.
		 *
		 * @return the sine
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Gets the cosine value.
		 *
		 * @return the cosine
		 */
		public double getCos() {
			return cos;
		}

	}
}
