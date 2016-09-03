package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.hw13.servlets.GlasanjeUtilities.Band;

/**
 * The Servlet that parses the voting results and outputs them as a piechart in
 * PNG format. Definition file and results file paths are stored in
 * {@link GlasanjeUtilities} class. <br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String defFile = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-definicija.txt");
		String resFile = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-rezultati.txt");

		try {
			List<Band> bands = GlasanjeUtilities.loadBands(Paths.get(defFile),
					Paths.get(resFile));

			resp.setContentType("image/png");
			JFreeChart chart = createVotingChart(bands);
			BufferedImage img = chart.createBufferedImage(600, 400);

			ImageIO.write(img, "png", resp.getOutputStream());
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

	/**
	 * Creates the pie chart containing voting data.
	 *
	 * @param bands
	 *            the list of bands with set scores.
	 * @return the piechart with voting data
	 */
	private JFreeChart createVotingChart(List<Band> bands) {
		DefaultPieDataset result = new DefaultPieDataset();

		for (Band band : bands) {
			result.setValue(band.getName(), band.getScore());
		}

		JFreeChart chart = ChartFactory.createPieChart3D("Voting results",
				result, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

}