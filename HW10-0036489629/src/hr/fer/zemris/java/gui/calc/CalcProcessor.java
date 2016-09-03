package hr.fer.zemris.java.gui.calc;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JLabel;

/**
 * The logic behind the calculator. Stores all values and performs all
 * operations. Works with double values.
 *
 * @author Juraj Juričić
 */
public class CalcProcessor {

	/** Cached previous value, used as fallback. */
	private String previousValue;

	/** The String-type version of currently stored value. */
	private String currentValue;

	/** The double-type version of the currently stored value. */
	private double doubleValue;

	/** The stack used for memory. */
	private Stack<Double> stack;

	/** The calculator display; updated upon any change. */
	private JLabel display;

	/** The clear flag. If true, next number input will reset the display. */
	private boolean clear = false;

	/**
	 * The error flag. If true, an error occured. Inherits behavious of the
	 * clear flag.
	 */
	private boolean error = false;

	/**
	 * The left value of the binary operator. Relevant only if binaryOperator is
	 * not null.
	 */
	private double binaryOperatorLeftValue;

	/** The used binary operator. Null if not currently used. */
	private DoubleBinaryOperator binaryOperator;

	/**
	 * Instantiates a new calc processor.
	 *
	 * @param display
	 *            the display to update upon any change
	 */
	public CalcProcessor(JLabel display) {
		this.display = display;

		currentValue = previousValue = "0";
		stack = new Stack<>();

		triggerChange();
	}

	/**
	 * Updates current value without ignoring the decimal point.
	 */
	private void updateCurrentValue() {
		updateCurrentValue(false);
	}

	/**
	 * Update current value based on the value stored in doubleValue variable.
	 *
	 * @param ignoreEndingDot
	 *            if false, will do nothing if the current value ends with
	 *            decimal point.
	 */
	private void updateCurrentValue(boolean ignoreEndingDot) {
		if (currentValue.endsWith(".") && !ignoreEndingDot) {
			return;
		}

		currentValue = Double.toString(doubleValue);
		if (doubleValue == (long) doubleValue) {
			currentValue = Long.toString((long) doubleValue);
		}
	}

	/**
	 * Updates the calculator display. To be called upon any value change.
	 */
	private void triggerChange() {
		try {
			doubleValue = Double.parseDouble(currentValue);

			updateCurrentValue();
		} catch (NumberFormatException e) {
			// Invalid format; probably double dot or some other error
			if (!error) {
				currentValue = previousValue;
				return;
			}
		}

		display.setText(currentValue + " ");
	}

	/**
	 * The abstract class CalcAction. Used for encapsulating operations.
	 *
	 * @author Juraj Juričić
	 * @param <T>
	 *            the type of argument that action accepts.
	 */
	private abstract class CalcAction<T> {

		/**
		 * The logic behind operation. Called after storing the previous value.
		 * Should not call triggerChange() - that is done by action() method.
		 *
		 * @param arg
		 *            the argument
		 */
		protected abstract void operate(T arg);

		/**
		 * Performs the action.
		 *
		 * @param arg
		 *            the argument
		 */
		public void action(T arg) {
			previousValue = currentValue;
			operate(arg);

			triggerChange();
		}
	}

	/**
	 * The Action called when a number is pressed.
	 *
	 * @author Juraj Juričić
	 */
	public class NumberPress extends CalcAction<String> {
		@Override
		protected void operate(String number) {
			if (clear || error) {
				currentValue = "";
				clear = error = false;
			}

			if (currentValue.equals("0") && !number.equals(".")) {
				currentValue = "";
			}

			currentValue = currentValue + number;
		}
	}

	/**
	 * The action called when a unary operator is pressed.
	 *
	 * @author Juraj Juričić
	 */
	public class UnaryOperation extends CalcAction<DoubleUnaryOperator> {
		@Override
		protected void operate(DoubleUnaryOperator operator) {
			performBinaryOperation();

			doubleValue = operator.applyAsDouble(doubleValue);
			updateCurrentValue(true);

			clear = true;
		}
	}

	/**
	 * The action called when a binary operator is pressed.
	 *
	 * @author Juraj Juričić
	 */
	public class BinaryOperation extends CalcAction<DoubleBinaryOperator> {
		@Override
		protected void operate(DoubleBinaryOperator operator) {
			performBinaryOperation();

			binaryOperatorLeftValue = doubleValue;
			clear = true;
			binaryOperator = operator;
		}
	}

	/**
	 * The Enum StackOperation.
	 *
	 * @author Juraj Juričić
	 */
	public static enum StackOperation {
		
		/** The push (store) operation. */
		PUSH, 
		/** The pop operation. */
		POP
	}

	/**
	 * The action called when a memory operator is pressed.
	 *
	 * @author Juraj Juričić
	 */
	public class MemoryOperation extends CalcAction<StackOperation> {
		@Override
		protected void operate(StackOperation op) {
			switch (op) {
			case POP:
				try {
					doubleValue = stack.pop();
					updateCurrentValue();
				} catch (EmptyStackException e) {
					error = true;
					currentValue = "Stack is empty";
				}
				break;
			case PUSH:
				stack.push(doubleValue);
				break;
			}
		}
	}

	/**
	 * The action called when a clear or reset operator is pressed.
	 *
	 * @author Juraj Juričić
	 */
	public class ClearOperation extends CalcAction<Boolean> {
		@Override
		protected void operate(Boolean reset) {
			doubleValue = 0;
			updateCurrentValue(true);

			if (reset) {
				binaryOperator = null;
			}
		}
	}

	/**
	 * Performs the currently stored binary operation on stored left value and current value (as right value).
	 */
	private void performBinaryOperation() {
		if (binaryOperator == null) {
			return;
		}

		doubleValue = binaryOperator.applyAsDouble(binaryOperatorLeftValue,
				doubleValue);
		updateCurrentValue();

		binaryOperator = null;
	}

}
