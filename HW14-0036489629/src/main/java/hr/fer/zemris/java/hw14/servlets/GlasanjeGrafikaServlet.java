package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * The Servlet that parses the voting results and outputs them as a piechart in
 * PNG format. <br>
 * The servlet accepts one request parameter: poll ID.
 *
 * @author Juraj Juričić
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int id;
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

			resp.setContentType("image/png");
			JFreeChart chart = createVotingChart(options);
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
	 * @param options
	 *            the list of bands with set scores.
	 * @return the piechart with voting data
	 */
	private JFreeChart createVotingChart(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();

		options.forEach(option -> {
			if (option.getVotesCount() == 0){
				return;
			}

			result.setValue(option.getOptionTitle(), option.getVotesCount());
		});

		JFreeChart chart = ChartFactory.createPieChart3D("Voting results",
				result, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

}