package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

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

/**
 * The Servlet that dynamically creates an image showin pie chart. The data is
 * OS usage, obtained from the following url: <a href=
 * "https://www.netmarketshare.com/operating-system-market-share.aspx?qprid=10&qpcustomd=0">
 * Net market share</a>
 *
 * @author Juraj Juričić
 */
public class ReportImageServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/png");
		JFreeChart chart = createOSChart();
		BufferedImage img = chart.createBufferedImage(500, 270);
		ImageIO.write(img, "png", resp.getOutputStream());
	}

	/**
	 * Creates the pie chart with hardcoded data.
	 *
	 * @return the chart
	 */
	private JFreeChart createOSChart() {
		DefaultPieDataset result = new DefaultPieDataset();
		// source:
		// https://www.netmarketshare.com/operating-system-market-share.aspx?qprid=10&qpcustomd=0
		result.setValue("Windows 7", 48.57);
		result.setValue("Windows 10", 17.43);
		result.setValue("Windows XP", 10.09);
		result.setValue("Windows 8.1", 8.77);
		result.setValue("Windows 8", 2.62);

		result.setValue("Mac OS X 10.11", 4.64);
		result.setValue("Mac OS X 10.10", 2.04);
		result.setValue("Linux", 1.79);

		JFreeChart chart = ChartFactory.createPieChart3D("OS usage", result,
				true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}
}
