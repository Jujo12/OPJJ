package hr.fer.zemris.java.gui.charts;

/**
 * The Class Margin, used for storing information about margin sizes. 
 *
 * @author Juraj Juričić
 */
class Margin{
	
	/** The top margin, in pixels. */
	private int top;
	
	/** The right margin, in pixels. */
	private int right;
	
	/** The bottom margin, in pixels. */
	private int bottom;
	
	/** The left margin, in pixels. */
	private int left;
	
	/**
	 * Instantiates a new margin data with the given values.
	 *
	 * @param top the top margin, in pixels
	 * @param right the right margin, in pixels
	 * @param bottom the bottom margin, in pixels
	 * @param left the left margin, in pixels
	 */
	public Margin(int top, int right, int bottom, int left) {
		super();
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	/**
	 * Gets the top margin, in pixels.
	 *
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * Sets the top.
	 *
	 * @param top the top margin, in pixels
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * Gets the right margin, in pixels.
	 *
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * Sets the right.
	 *
	 * @param right the right margin, in pixels
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * Gets the bottom margin, in pixels.
	 *
	 * @return the bottom
	 */
	public int getBottom() {
		return bottom;
	}

	/**
	 * Sets the bottom.
	 *
	 * @param bottom the bottom margin, in pixels
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	/**
	 * Gets the left margin, in pixels.
	 *
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Sets the left.
	 *
	 * @param left the left margin, in pixels
	 */
	public void setLeft(int left) {
		this.left = left;
	}
}