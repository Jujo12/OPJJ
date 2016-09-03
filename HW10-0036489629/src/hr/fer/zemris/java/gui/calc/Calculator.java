package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.CalcProcessor.StackOperation;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * The Class Calculator. Similar to the Windows calculator. Provides basic
 * arithmetic and trigonometric operations.
 *
 * @author Juraj Juričić
 */
public class Calculator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The light blue color, used as background for buttons. */
	private static final Color COLOR_LIGHTBLUE = new Color(114, 159, 207);

	/** The deep blue color, used as border for buttons. */
	private static final Color COLOR_DEEPBLUE = new Color(52, 101, 175);

	/** The font used for buttons. */
	private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);

	/** The processor. */
	private CalcProcessor processor;

	/** The invert flag. If true, inverse operations are used. */
	private boolean invert = false;

	/** The set of buttons that provide inversed operations. */
	private Set<InversibleButton> inversibleButtons = new HashSet<>();

	/**
	 * Instantiates a new calculator.
	 */
	public Calculator() {
		initGUI();
	}

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator window = new Calculator();

			window.setVisible(true);
		});
	}

	/**
	 * Initializes the graphical user interface.
	 */
	private void initGUI() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new CalcLayout(5));
		this.add(panel);

		/*
		 * Add display
		 */
		JLabel display = new JLabel();
		panel.add(display, new RCPosition(1, 1));
		display.setBackground(Color.ORANGE);
		display.setOpaque(true);
		display.setHorizontalAlignment(JLabel.RIGHT);
		display.setBorder(BorderFactory.createLineBorder(COLOR_DEEPBLUE));
		display.setFont(new Font("Arial", Font.BOLD, 20));

		/*
		 * Attach processor
		 */
		processor = new CalcProcessor(display);

		/*
		 * Add buttons
		 */
		addButtons(panel);

		setStyle(panel, Arrays.asList(display));

		setSize(400, 315);
	}

	/**
	 * Adds the buttons to panel.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addButtons(JPanel panel) {

		/*
		 * Numbers
		 */
		addNumbers(panel);

		/*
		 * Unary operators
		 */
		addUnaryOperators(panel);

		/*
		 * Binary operators
		 */
		addBinaryOperators(panel);

		/*
		 * Memory operators
		 */
		addMemoryOperators(panel);

		/*
		 * Inverse operation
		 */
		JCheckBox inv = new JCheckBox("Inv");
		panel.add(inv, new RCPosition(5, 7));
		inv.addActionListener(e -> {
			invert = !invert;
			inversibleButtons.forEach(b -> {
				if (!(b instanceof JButton)) {
					return;
				}
				JButton jb = (JButton) b;

				if (invert) {
					jb.setText(b.getInverseLabel());
				} else {
					jb.setText(b.getRegularLabel());
				}
			});
		});
		inv.setHorizontalAlignment(JCheckBox.CENTER);

		/*
		 * Clear, reset
		 */
		addClearOperators(panel);

	}

	/**
	 * Adds the numbers.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addNumbers(JPanel panel) {
		int x;
		int y;
		for (int i = 1; i <= 9; i++) {
			x = 3 + ((i - 1) % 3);
			y = 4 - (i - 1) / 3;

			panel.add(new NumberButton(Integer.toString(i)),
					new RCPosition(y, x));
		}

		panel.add(new NumberButton(Integer.toString(0)), new RCPosition(5, 3));
		panel.add(new NumberButton("."), new RCPosition(5, 5));
	}

	/**
	 * Adds the unary operators.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addUnaryOperators(JPanel panel) {
		/*
		 * sin cos tan ctg 1/x log ln +/-
		 */
		panel.add(new UnaryOperatorButton("sin", Math::sin, "asin", Math::asin),
				new RCPosition(2, 2));
		panel.add(new UnaryOperatorButton("cos", Math::cos, "acos", Math::acos),
				new RCPosition(3, 2));
		panel.add(new UnaryOperatorButton("tan", Math::tan, "atan", Math::atan),
				new RCPosition(4, 2));
		panel.add(new UnaryOperatorButton("ctg", x -> 1.0 / Math.tan(x), "actg",
				x -> Math.atan(1.0 / x)), new RCPosition(5, 2));
		panel.add(new UnaryOperatorButton("1/x", x -> 1.0 / x),
				new RCPosition(2, 1));
		panel.add(new UnaryOperatorButton("log", Math::log10, "10^x",
				x -> Math.pow(10, x)), new RCPosition(3, 1));
		panel.add(new UnaryOperatorButton("ln", Math::log, "e^x", Math::exp),
				new RCPosition(4, 1));

		panel.add(new UnaryOperatorButton("+/-", x -> -x),
				new RCPosition(5, 4));
		panel.add(new UnaryOperatorButton("=", x -> x), new RCPosition(1, 6));

	}

	/**
	 * Adds the binary operators.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addBinaryOperators(JPanel panel) {
		/*
		 * operators: / * - + x^n
		 */
		panel.add(new BinaryOperatorButton("/", (x, y) -> x / y),
				new RCPosition(2, 6));
		panel.add(new BinaryOperatorButton("*", (x, y) -> x * y),
				new RCPosition(3, 6));
		panel.add(new BinaryOperatorButton("-", (x, y) -> x - y),
				new RCPosition(4, 6));
		panel.add(new BinaryOperatorButton("+", (x, y) -> x + y),
				new RCPosition(5, 6));

		panel.add(new BinaryOperatorButton("x^n", Math::pow, "n√x",
				(x, y) -> Math.pow(x, 1 / y)), new RCPosition(5, 1));
	}

	/**
	 * Adds the memory operators.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addMemoryOperators(JPanel panel) {
		JButton pushButton = new JButton("push");
		panel.add(pushButton, new RCPosition(3, 7));
		pushButton.addActionListener(e -> {
			processor.new MemoryOperation().action(StackOperation.PUSH);
		});

		JButton popButton = new JButton("pop");
		panel.add(popButton, new RCPosition(4, 7));
		popButton.addActionListener(e -> {
			processor.new MemoryOperation().action(StackOperation.POP);
		});
	}

	/**
	 * Adds the clear and reset operators.
	 *
	 * @param panel
	 *            the panel
	 */
	private void addClearOperators(JPanel panel) {
		JButton clearButton = new JButton("clr");
		panel.add(clearButton, new RCPosition(1, 7));
		clearButton.addActionListener(e -> {
			processor.new ClearOperation().action(false);
		});

		JButton resetButton = new JButton("res");
		panel.add(resetButton, new RCPosition(2, 7));
		resetButton.addActionListener(e -> {
			processor.new ClearOperation().action(true);
		});
	}

	/**
	 * Sets the style for all elements that are children to the given parent
	 * component.
	 *
	 * @param parent
	 *            the parent
	 * @param skip
	 *            the components to skip
	 */
	private static void setStyle(JComponent parent, List<JComponent> skip) {
		for (Component c : parent.getComponents()) {
			if (!(c instanceof JComponent)) {
				continue;
			}

			JComponent jc = (JComponent) c;

			if (skip.contains(jc)) {
				continue;
			}

			jc.setBorder(BorderFactory.createLineBorder(COLOR_DEEPBLUE));
			jc.setBackground(COLOR_LIGHTBLUE);
			jc.setOpaque(true);

			jc.setFont(BUTTON_FONT);
		}
	}

	/**
	 * The button used for numbers.
	 *
	 * @author Juraj Juričić
	 */
	private class NumberButton extends JButton {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new number button.
		 *
		 * @param num
		 *            the num
		 */
		public NumberButton(String num) {
			super(num);

			this.addActionListener(e -> {
				processor.new NumberPress().action(num);
			});
		}
	}

	/**
	 * The button used for unary operators.
	 *
	 * @author Juraj Juričić
	 */
	private class UnaryOperatorButton extends JButton
			implements InversibleButton {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The operator. */
		private DoubleUnaryOperator operator;

		/** The inverse operator. */
		private DoubleUnaryOperator inverseOperator;

		/** The inverse label. */
		private String inverseLabel;

		/** The regular label. */
		private String label;

		/**
		 * Instantiates a new unary operator button.
		 *
		 * @param label
		 *            the label
		 * @param operator
		 *            the operator
		 * @param inverseLabel
		 *            the inverse label
		 * @param inverseOperator
		 *            the inverse operator. Will be set to operator if null.
		 */
		public UnaryOperatorButton(String label, DoubleUnaryOperator operator,
				String inverseLabel, DoubleUnaryOperator inverseOperator) {
			super(label);
			this.operator = operator;

			if (inverseLabel == null) {
				inverseLabel = label;
			} else {
				inversibleButtons.add(this);
			}
			if (inverseOperator == null) {
				inverseOperator = operator;
			}

			this.label = label;
			this.inverseLabel = inverseLabel;
			this.inverseOperator = inverseOperator;

			this.addActionListener(e -> {
				processor.new UnaryOperation().action(getOperator());
			});
		}

		/**
		 * Instantiates a new unary operator button without the inverse
		 * operator.
		 *
		 * @param label
		 *            the label
		 * @param operator
		 *            the operator
		 */
		public UnaryOperatorButton(String label, DoubleUnaryOperator operator) {
			this(label, operator, null, null);
		}

		/**
		 * Gets the operator.
		 *
		 * @return the operator
		 */
		private DoubleUnaryOperator getOperator() {
			return invert ? inverseOperator : operator;
		}

		@Override
		public String getInverseLabel() {
			return inverseLabel;
		}

		@Override
		public String getRegularLabel() {
			return label;
		}
	}

	/**
	 * The Class BinaryOperatorButton.
	 *
	 * @author Juraj Juričić
	 */
	private class BinaryOperatorButton extends JButton
			implements InversibleButton {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The operator. */
		private DoubleBinaryOperator operator;

		/** The inverse operator. */
		private DoubleBinaryOperator inverseOperator;

		/** The inverse label. */
		private String inverseLabel;

		/** The regular label. */
		private String label;

		/**
		 * Instantiates a new binary operator button.
		 *
		 * @param label
		 *            the label
		 * @param operator
		 *            the operator
		 * @param inverseLabel
		 *            the inverse label
		 * @param inverseOperator
		 *            the inverse operator. Will be set to operator if null.
		 */
		public BinaryOperatorButton(String label, DoubleBinaryOperator operator,
				String inverseLabel, DoubleBinaryOperator inverseOperator) {
			super(label);
			this.operator = operator;

			if (inverseOperator == null) {
				inverseOperator = operator;
			}
			if (inverseLabel == null) {
				inverseLabel = label;
			} else {
				inversibleButtons.add(this);
			}

			this.label = label;
			this.inverseLabel = inverseLabel;
			this.inverseOperator = inverseOperator;

			this.addActionListener(e -> {
				processor.new BinaryOperation().action(getOperator());
			});
		}

		/**
		 * Instantiates a new binary operator button.
		 *
		 * @param label
		 *            the label
		 * @param operator
		 *            the operator
		 */
		public BinaryOperatorButton(String label,
				DoubleBinaryOperator operator) {
			this(label, operator, null, null);
		}

		/**
		 * Gets the operator.
		 *
		 * @return the operator
		 */
		private DoubleBinaryOperator getOperator() {
			return invert ? inverseOperator : operator;
		}

		@Override
		public String getInverseLabel() {
			return inverseLabel;
		}

		@Override
		public String getRegularLabel() {
			return label;
		}
	}
}
