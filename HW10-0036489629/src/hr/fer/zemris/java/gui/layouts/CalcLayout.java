package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * The Class CalcLayout. A layout manager for the calculator. Can hold 31
 * element. Each element occupies one grid slot except for the first one (at
 * position(1,1)), which occupies 1 row and 5 columns.
 *
 * @author Juraj Juričić
 */
public class CalcLayout implements LayoutManager2 {

	/** The grid spacing. */
	private int spacing;

	/** The components. */
	private Map<Component, RCPosition> components = new HashMap<>();

	/** The row count. */
	private static final int MAX_ROWS = 5;

	/** The column count. */
	private static final int MAX_COLS = 7;

	/** The Constant ALIGN_CENTER. */
	private static final float ALIGN_CENTER = 0.5f;

	/** The first position, that occupies 5 columns. */
	private static final RCPosition FIRST_POSITION = new RCPosition(1, 1);

	/**
	 * Instantiates a new calc layout with grid spacing set to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Instantiates a new calc layout with the given grid spacing.
	 *
	 * @param spacing
	 *            the spacing
	 */
	public CalcLayout(int spacing) {
		if (spacing < 0) {
			throw new IllegalArgumentException(
					"Spacing cannot be less than 0.");
		}
		this.spacing = spacing;
	}

	/**
	 * Not supported.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getMaximumSize);
	}

	/**
	 * Calculates and returns the layout size. Generic function used to
	 * calculate minimum, preferred or maximum size via strategy design pattern.
	 *
	 * @param parent
	 *            the parent
	 * @param sizeFunction
	 *            the strategy used for determining the size (minimum,
	 *            preferred, maximum or other)
	 * @return the layout size
	 */
	private Dimension getLayoutSize(Container parent,
			Function<Component, Dimension> sizeFunction) {
		int maxW = 0;
		int maxH = 0;

		for (Map.Entry<Component, RCPosition> e : components.entrySet()) {
			Dimension size = sizeFunction.apply(e.getKey());
			RCPosition position = e.getValue();

			if (size == null) {
				continue;
			}

			maxH = Math.max(maxH, size.height);
			if (position.equals(FIRST_POSITION)) {
				continue;
			}
			maxW = Math.max(maxW, size.width);
		}

		Insets insets = parent.getInsets();
		int x = insets.left + insets.right + MAX_COLS * maxW
				+ (MAX_COLS - 1) * spacing;
		int y = insets.top + insets.bottom + MAX_ROWS * maxH
				+ (MAX_ROWS - 1) * spacing;

		return new Dimension(x, y);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position;

		if (constraints instanceof String) {
			position = RCPosition.fromString((String) constraints);
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException(
					"Constraint must be of type String of RCPosition.");
		}

		if (!checkPosition(position)) {
			throw new IllegalArgumentException("Illegal position given: "
					+ position.getRow() + "," + position.getColumn());
		}
		if (components.containsValue(position)) {
			throw new IllegalArgumentException("Position already filled.");
		}

		components.put(comp, position);
	}

	/**
	 * Checks if the position is within legal bounds.
	 *
	 * @param pos
	 *            the pos
	 * @return true if the position is legal, false otherwise
	 */
	private boolean checkPosition(RCPosition pos) {
		if (pos.getRow() < 1 || pos.getRow() > MAX_ROWS) {
			return false;
		}
		if (pos.getColumn() < 1 || pos.getColumn() > MAX_COLS) {
			return false;
		}
		if (pos.getRow() == 1 && pos.getColumn() > 1 && pos.getColumn() < 6) {
			return false;
		}

		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return ALIGN_CENTER;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return ALIGN_CENTER;
	}

	@Override
	public void invalidateLayout(Container target) {
		//void
		return;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int elementWidth = (parent.getWidth() - insets.left - insets.right
				- (MAX_COLS + 1) * spacing) / MAX_COLS;
		int elementHeight = (parent.getHeight() - insets.top - insets.bottom
				- (MAX_ROWS + 1) * spacing) / MAX_ROWS;

		int width;
		for (Entry<Component, RCPosition> e : components.entrySet()) {
			Component c = e.getKey();
			RCPosition pos = e.getValue();

			int x = insets.left + elementWidth * (pos.getColumn() - 1)
					+ spacing * pos.getColumn();
			int y = insets.top + elementHeight * (pos.getRow() - 1)
					+ spacing * pos.getRow();

			width = elementWidth;
			if (pos.equals(FIRST_POSITION)) {
				width = elementWidth * 5 + spacing * 4;
			}

			c.setBounds(x, y, width, elementHeight);
		}
	}

}
