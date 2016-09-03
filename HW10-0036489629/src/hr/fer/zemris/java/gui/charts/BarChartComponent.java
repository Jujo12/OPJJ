package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * The bar chart component. Displays the bars with data from the given
 * {@link BarChart} model.
 *
 * @author Juraj Juričić
 */
public class BarChartComponent extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model containing data. */
	private BarChart model;

	/** The y splits. */
	private List<Integer> ySplits;

	/** The x splits. */
	private List<Integer> xSplits;

	/** The dimensions of the chart. */
	private BarChartDimensions dimensions;

	/** The max y val. */
	double maxYVal;

	/** The big margin. */
	private int BIG_MARGIN = 20;

	/** The small margin. */
	private int SMALL_MARGIN = 5;

	/** The gray color, used for arrows and axes. */
	private static final Color COLOR_GRAY = new Color(120, 120, 120);

	/** The gray color used for shadows. Alpha channel is set to 50%. */
	private static final Color COLOR_SHADOW = new Color(90, 90, 90, 50);

	/** The color used for bars. */
	private static final Color COLOR_BAR = new Color(244, 119, 72);

	/** The light orange color, used for grid. */
	private static final Color COLOR_LIGHTORANGE = new Color(238, 220, 187);

	/** The regular font. */
	private static final Font FONT_REGULAR = new Font("default", Font.PLAIN,
			12);

	/** The bold font. */
	private static final Font FONT_BOLD = new Font("default", Font.BOLD, 12);

	/** The square root of 2. */
	private static final double SQRT2 = Math.sqrt(2);

	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param model
	 *            the model
	 */
	public BarChartComponent(BarChart model) {
		super();
		this.model = model;

		this.setPreferredSize(new Dimension(600, 400));

		ySplits = new ArrayList<>();
		xSplits = new ArrayList<>();

		this.dimensions = new BarChartDimensions();

		// y splits:
		for (int y = model.getMinY(); y < model.getMaxY(); y += model
				.getySpacing()) {
			ySplits.add(y);
		}
		maxYVal = ySplits.get(ySplits.size() - 1) + model.getySpacing();
		ySplits.add((int) maxYVal);

		// x splits:
		for (XYValue x : model.getValues()) {
			xSplits.add(x.getX());
		}
	}

	/**
	 * Draws the grid.
	 *
	 * @param g2d
	 *            the {@link Graphics2D} object
	 */
	private void drawGrid(Graphics2D g2d) {
		// horizontal
		int baseY = dimensions.getMargin().getTop();
		int y;
		int x1 = dimensions.getMargin().getLeft();
		int delta = model.getMaxY() - model.getMinY();
		for (int i = 0; i < delta; i += model.getySpacing()) {

			y = (int) (baseY
					+ (double) i / delta * dimensions.chartDimension.height);

			g2d.setColor(COLOR_LIGHTORANGE);
			g2d.drawLine(x1, y,
					x1 + dimensions.getChartDimension().width + SMALL_MARGIN,
					y);

			g2d.setColor(COLOR_GRAY);
			g2d.drawLine(x1 - SMALL_MARGIN, y, x1, y);
		}

		// vertical
		int baseX = dimensions.getMargin().getLeft();
		int x;
		int y1 = dimensions.getMargin().getTop() - SMALL_MARGIN;
		int y2 = y1 + dimensions.getChartDimension().height + SMALL_MARGIN;
		for (int i = 0; i < xSplits.size() + 1; i++) {
			x = (int) (baseX + (double) i / xSplits.size()
					* dimensions.chartDimension.width);

			g2d.setColor(COLOR_LIGHTORANGE);
			g2d.drawLine(x, y1, x, y2);

			g2d.setColor(COLOR_GRAY);
			g2d.drawLine(x, y2, x, y2 + SMALL_MARGIN);
		}
	}

	/**
	 * Draws the axes.
	 *
	 * @param g2d
	 *            the {@link Graphics2D} object
	 */
	private void drawAxes(Graphics2D g2d) {
		g2d.setColor(COLOR_GRAY);

		int arrowSize = 10;

		// x axis
		// arrow
		int x = dimensions.getMargin().getLeft();
		int y = dimensions.getMargin().getTop() / 2;
		Polygon triangle = new Polygon(
				new int[] { x - arrowSize / 2, x, x + arrowSize / 2 },
				new int[] { y, (int) (y - arrowSize / SQRT2), y }, 3);
		g2d.fillPolygon(triangle);

		// line
		y = this.getHeight() - dimensions.getMargin().getBottom();
		g2d.drawLine(dimensions.getMargin().getLeft() - SMALL_MARGIN / 2, y,
				this.getWidth() - dimensions.getMargin().getRight() / 2, y);

		// y axis
		// arrow
		x = this.getWidth() - dimensions.getMargin().getRight() / 2;
		y = this.getHeight() - dimensions.getMargin().getBottom();
		triangle = new Polygon(
				new int[] { x, (int) (x + arrowSize / SQRT2), x },
				new int[] { y - arrowSize / 2, y, y + arrowSize / 2 }, 3);
		g2d.fillPolygon(triangle);

		// line
		x = dimensions.getMargin().getLeft();
		g2d.drawLine(x, dimensions.getMargin().getTop() / 2, x, this.getHeight()
				- dimensions.getMargin().getBottom() + SMALL_MARGIN);
	}

	/**
	 * Draw the content on the margins.
	 *
	 * @param g2d
	 *            the {@link Graphics2D} object
	 */
	private void drawMargins(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();

		int x;
		int y;

		// bottom
		// x label
		g2d.setFont(FONT_REGULAR);
		int halfXAxis = dimensions.getMargin().getLeft()
				+ (dimensions.getChartDimension().width / 2);
		g2d.drawString(model.getxLabel(),
				halfXAxis - fm.stringWidth(model.getxLabel()) / 2,
				this.getHeight() - fm.getDescent());

		// x splits
		g2d.setFont(FONT_BOLD);
		y = this.getHeight() - fm.getHeight() - BIG_MARGIN;

		int baseX = dimensions.margin.getLeft()
				+ dimensions.getSpacing().width / 2;
		for (int i = 0; i < xSplits.size(); i++) {
			x = (int) (baseX + (double) i / xSplits.size()
					* dimensions.chartDimension.width);

			g2d.drawString(Integer.toString(xSplits.get(i)), x, y);
		}

		// left
		// y label
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		int halfYAxis = dimensions.getMargin().getTop()
				+ (dimensions.getChartDimension().height / 2);
		g2d.drawString(model.getyLabel(),
				-halfYAxis - fm.stringWidth(model.getyLabel()) / 2,
				fm.getAscent());

		at.rotate(Math.PI / 2);
		g2d.setTransform(at);

		if (dimensions.chartDimension.height < 0) {
			return;
		}

		// y splits
		int baseY = this.getHeight() - dimensions.getMargin().getBottom()
				+ fm.getAscent() / 2;// dimensions.getMargin().getTop() +
										// fm.getAscent() / 2;

		int rightAlign = dimensions.getMargin().getLeft() - BIG_MARGIN / 2;

		String str;

		g2d.setFont(FONT_BOLD);
		int delta = model.getMaxY() - model.getMinY();
		int num;
		for (int i = 0; i <= delta; i += model.getySpacing()) {
			num = model.getMinY() + i;
			str = Integer.toString(num);

			y = (int) (baseY
					- (double) i / delta * dimensions.chartDimension.height);

			x = rightAlign - fm.stringWidth(str);
			g2d.drawString(str, x, y);
		}

	}

	/**
	 * Draw the bars.
	 *
	 * @param g2d
	 *            the {@link Graphics2D} object
	 */
	private void drawBars(Graphics2D g2d) {
		int x = dimensions.getMargin().getLeft() + 1;
		int y;
		int width;
		int height;

		XYValue val;
		int delta = model.getMaxY() - model.getMinY();
		for (int i = 0; i < model.getValues().size(); i++) {
			val = model.getValues().get(i);

			width = dimensions.spacing.width;
			height = (int) ((val.getY() - model.getMinY()) / (double) delta
					* dimensions.getChartDimension().height);

			height = Math.max(0, height);

			y = this.getHeight() - dimensions.getMargin().getBottom() - height;

			if (i >= model.getValues().size()
					- (dimensions.getChartDimension().width)
							% model.getValues().size()) {
				width += 1;
			}

			// shadow
			g2d.setColor(COLOR_SHADOW);
			g2d.fillRect(x + 2, y + 2, width, height - 2);

			// bar
			g2d.setColor(COLOR_BAR);
			g2d.fillRect(x, y, width - 1, height);

			x += width;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(FONT_REGULAR);

		this.dimensions.recalculate(g2d);

		drawMargins(g2d);

		if (dimensions.chartDimension.height < 0) {
			return;
		}

		drawGrid(g2d);

		drawAxes(g2d);

		drawBars(g2d);
	}

	/**
	 * The Class BarChartDimensions. Provides storage and calculation mechanisms
	 * for chart size information. Information include margin sizes, chart size,
	 * padding, and spacing.
	 *
	 * @author Juraj Juričić
	 */
	private class BarChartDimensions {

		/** The margin. */
		private Margin margin;

		/** The chart dimension. */
		private Dimension chartDimension;

		/** The spacing. */
		private Dimension spacing;

		/**
		 * Recalculates and stores the newly calculated data.
		 *
		 * @param g2d
		 *            the {@link Graphics2D} object
		 */
		public void recalculate(Graphics2D g2d) {
			this.margin = calculateMargins(g2d);
			this.chartDimension = new Dimension(
					getWidth() - margin.getLeft() - margin.getRight(),
					getHeight() - margin.getTop() - margin.getBottom());

			this.spacing = new Dimension(chartDimension.width / xSplits.size(),
					chartDimension.height / (ySplits.size() - 1));
		}

		/**
		 * Calculates the margins.
		 *
		 * @param g2d
		 *            the {@link Graphics2D} object
		 * @return the margin
		 */
		private Margin calculateMargins(Graphics2D g2d) {
			FontMetrics fm = g2d.getFontMetrics();
			FontMetrics fmBold = g2d.getFontMetrics(FONT_BOLD);

			int top;
			int right = top = BIG_MARGIN;
			int bottom = fm.getHeight() + fmBold.getHeight() + BIG_MARGIN;
			int left = fm.getHeight() + BIG_MARGIN + SMALL_MARGIN
					+ fmBold.stringWidth(
							Integer.toString(ySplits.get(ySplits.size() - 1)));

			return new Margin(top, right, bottom, left);
		}

		/**
		 * Gets the margin.
		 *
		 * @return the margin
		 */
		public Margin getMargin() {
			return margin;
		}

		/**
		 * Gets the chart dimension.
		 *
		 * @return the chart dimension
		 */
		public Dimension getChartDimension() {
			return chartDimension;
		}

		/**
		 * Gets the spacing.
		 *
		 * @return the spacing
		 */
		public Dimension getSpacing() {
			return spacing;
		}

	}

}
