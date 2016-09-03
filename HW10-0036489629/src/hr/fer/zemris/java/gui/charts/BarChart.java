package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * The Class BarChart. Represents a model for {@link BarChartComponent},
 * containing values and data about axes size and spacing.
 *
 * @author Juraj Juričić
 */
public class BarChart {

	/** The values. */
	private List<XYValue> values;

	/** The x label. */
	private String xLabel;

	/** The y label. */
	private String yLabel;

	/** The min y. */
	private int minY;

	/** The max y. */
	private int maxY;

	/** The y spacing. */
	private int ySpacing;

	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values
	 *            the list of values
	 * @param xLabel
	 *            the x label
	 * @param yLabel
	 *            the y label
	 * @param minY
	 *            the min y
	 * @param maxY
	 *            the max y
	 * @param ySpacing
	 *            the y spacing
	 */
	public BarChart(List<XYValue> values, String xLabel, String yLabel,
			int minY, int maxY, int ySpacing) {
		super();

		JJHelper.checkNotNullArguments(values, xLabel, yLabel);

		this.values = values;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.minY = minY;
		this.maxY = maxY;
		this.ySpacing = ySpacing;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the x label.
	 *
	 * @return the xLabel
	 */
	public String getxLabel() {
		return xLabel;
	}

	/**
	 * Gets the y label.
	 *
	 * @return the yLabel
	 */
	public String getyLabel() {
		return yLabel;
	}

	/**
	 * Gets the min y.
	 *
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Gets the max y.
	 *
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Gets the y spacing.
	 *
	 * @return the ySpacing
	 */
	public int getySpacing() {
		return ySpacing;
	}

}
