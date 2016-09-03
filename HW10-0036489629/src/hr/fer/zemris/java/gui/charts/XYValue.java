package hr.fer.zemris.java.gui.charts;

/**
 * The Class XYValue stores (x,y) pairs of values to be displayed in
 * {@link BarChartComponent}.
 *
 * @author Juraj Juričić
 */
public class XYValue {

	/** The x value. */
	private final int x;

	/** The y value. */
	private final int y;

	/**
	 * Instantiates a new XY value.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Constructs a new {@link XYValue} from string. The string should be in format (x,y).
	 *
	 * @param str
	 *            the str
	 * @return the XY value
	 * @throws IllegalArgumentException Thrown if the inputstring is of invalid format.
	 */
	public static XYValue fromString(String str) throws IllegalArgumentException{
		String[] s = str.split(",");
		if (s.length != 2) {
			throw new IllegalArgumentException();
		}

		try {
			XYValue xyValue = new XYValue(Integer.parseInt(s[0].trim()),
					Integer.parseInt(s[1].trim()));

			return xyValue;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
}
