package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The demonstration program for bar chart. Accepts one command line argument:
 * path to the file containing chart data.<br>
 * <br>
 * Example data:<br>
 * Number of people in the car<br>
 * Frequency<br>
 * 1,8 2,20 3,22 4,10 5,4<br>
 * 0<br>
 * 22<br>
 * 2
 *
 * @author Juraj Juričić
 */
public class BarChartDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bar chart demo.
	 *
	 * @param model
	 *            the model
	 */
	public BarChartDemo(BarChart model) {
		super();

		this.add(new BarChartComponent(model));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.pack();
	}

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("One argument expected.");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String xLabel = br.readLine();
			String yLabel = br.readLine();

			String pointsStr = br.readLine();
			String[] points = pointsStr.split(" ");
			if (points.length == 0) {
				throw new IllegalArgumentException("No points given.");
			}
			List<XYValue> pointsList = new ArrayList<>();
			for (String p : points) {
				pointsList.add(XYValue.fromString(p));
			}

			int minY = Integer.parseInt(br.readLine());
			int maxY = Integer.parseInt(br.readLine());

			int ySpacing = Integer.parseInt(br.readLine());

			BarChart model = new BarChart(pointsList, xLabel, yLabel, minY,
					maxY, ySpacing);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					BarChartDemo window = new BarChartDemo(model);
					window.setVisible(true);
				}
			});
		} catch (IOException e) {
			System.err.println("An error occured while reading from file.");
		} catch (NumberFormatException e) {
			System.err.println("Invalid format: error while parsing number.");
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid format: " + e.getMessage());
		}

	}

}
