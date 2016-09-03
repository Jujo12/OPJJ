package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * The Wrapper for values of {@link ObjectMultistack}. Additionally, provides
 * basic arithmetic operations that can be done on stored {@link Number}
 * objects.
 *
 * @author Juraj Juričić
 */
public class ValueWrapper {

	/** The stored value. */
	private Object value;

	/**
	 * Instantiates a new value wrapper with the given initial value.
	 *
	 * @param value
	 *            the initial value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds the give value to the currently stored value.<br>
	 * The allowed operands are null (in which case they are evaluated as int
	 * 0), String representing a number (rules are the same as
	 * Double.parseDouble(String) or Int.parseInt(String)), Integer and Double.
	 * <br>
	 * If the types are not valid, throws {@link IllegalArgumentException}.
	 *
	 * @param incValue
	 *            the value to add to the currently stored value
	 */
	public void increment(Object incValue) {
		value = Operations.INCREMENT.operate(value, incValue);
	}

	/**
	 * Subtracts the given value from the currently stored value.<br>
	 * The allowed operands are null (in which case they are evaluated as int
	 * 0), String representing a number (rules are the same as
	 * Double.parseDouble(String) or Int.parseInt(String)), Integer and Double.
	 * <br>
	 * If the types are not valid, throws {@link IllegalArgumentException}.
	 *
	 * @param decValue
	 *            the value to subtract from the currently stored value
	 */
	public void decrement(Object decValue) {
		value = Operations.DECREMENT.operate(value, decValue);
	}

	/**
	 * Multiplies the stored value by the given value.<br>
	 * The allowed operands are null (in which case they are evaluated as int
	 * 0), String representing a number (rules are the same as
	 * Double.parseDouble(String) or Int.parseInt(String)), Integer and Double.
	 * <br>
	 * If the types are not valid, throws {@link IllegalArgumentException}.
	 *
	 * @param mulValue
	 *            the value to multiply the currently stored value by
	 */
	public void multiply(Object mulValue) {
		value = Operations.MULTIPLY.operate(value, mulValue);
	}

	/**
	 * Divides the currently stored value with the given value.<br>
	 * The allowed operands are null (in which case they are evaluated as int
	 * 0), String representing a number (rules are the same as
	 * Double.parseDouble(String) or Int.parseInt(String)), Integer and Double.
	 * <br>
	 * The given value (divisor) cannot be null.<br>
	 * If the types are not valid, throws {@link IllegalArgumentException}. If
	 * the divisor evaluates to 0, throws {@link ArithmeticException}.
	 *
	 * @param divValue
	 *            the value to divide the currently stored value with
	 * @throws ArithmeticException
	 *             thrown if the divisor is evaluated to 0
	 */
	public void divide(Object divValue) throws ArithmeticException {
		value = Operations.DIVIDE.operate(value, divValue);
	}

	/**
	 * Gets the stored value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Compares the currently stored value with the given value.<br>
	 * The method returns an integer less than zero if currently stored value is
	 * smaller than argument, an integer greater than zero if currently stored
	 * value is larger than argument, or an integer 0 if they are equal.<br>
	 * The allowed operands are null (in which case they are evaluated as int
	 * 0), String representing a number (rules are the same as
	 * Double.parseDouble(String) or Int.parseInt(String)), Integer and Double.
	 * <br>
	 * If the types are not valid, throws {@link IllegalArgumentException}.
	 *
	 * @param withValue
	 *            the value to compare the currently stored value with
	 * @return the int: an integer greater than zero if currently stored value
	 *         is smaller than argument, an integer greater than zero if
	 *         currently stored value is larger than argument, or an integer 0
	 *         if they are equal.
	 */
	public int numCompare(Object withValue) {
		return ((Number) Operations.NUM_COMPARE.operate(value, withValue))
				.intValue();
	}

	/**
	 * Checks if all the given elements are instances of the given class.
	 *
	 * @param type
	 *            the class to check elements against
	 * @param elems
	 *            the elements to check
	 * @return true if all given elements are of the given type. False
	 *         otherwise.
	 */
	private static boolean checkAllTypes(Class<?> type, Object... elems) {
		for (Object i : elems) {
			if (!type.isInstance(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if any of the given elements is an instance of the given class.
	 *
	 * @param type
	 *            the class to check elements against
	 * @param elems
	 *            the elements to check
	 * @return true if any of the given elements is and instance of the given
	 *         type. False otherwise.
	 */
	private static boolean checkAnyTypes(Class<?> type, Object... elems) {
		for (Object i : elems) {
			if (type.isInstance(i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Parses the given object into a {@link Number}.<br>
	 * Null-values are evaluated as Integer 0. Strings are evaluated as
	 * {@link Double} or {@link Integer}, or a {@link ClassCastException} is
	 * thrown if that is not possible. Integers and Doubles are cast to Number.
	 *
	 * @param value
	 *            the value to evaluate
	 * @return the parsed number
	 * @throws ClassCastException
	 *             thrown if the parsing is not possible (invalid type)
	 */
	private static Number parseNumber(Object value) throws ClassCastException {
		if (!validType(value)) {
			throw new ClassCastException("Invalid object type");
		}

		if (value == null) {
			return Integer.valueOf(0);
		} else if (value instanceof String) {
			// check if might be decimal
			String stringValue = (String) value;
			if (stringValue.indexOf('.') != -1
					|| stringValue.indexOf('E') != -1) {
				// double
				Double result = null;
				try {
					result = Double.parseDouble(stringValue);
				} catch (NumberFormatException e) {
					throw new ClassCastException("Invalid String format");
				}
				return result;
			} else {
				// integer
				Integer result = null;
				try {
					result = Integer.parseInt(stringValue);
				} catch (NumberFormatException e) {
					throw new ClassCastException("Invalid String format: " + stringValue);
				}
				return result;
			}
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}

		// should never reach this
		throw new ClassCastException("Invalid object type");
	}

	/**
	 * Checks if the given objet is of valid type for parsing (Integer, Double,
	 * String or null-pointer).
	 *
	 * @param value
	 *            the value to check
	 * @return true if the value is of valid type, false otherwise.
	 */
	private static boolean validType(Object value) {
		return (value instanceof Integer || value instanceof Double
				|| value instanceof String || value == null);
	}

	/**
	 * The Operations enum. Also provides logic for operations.
	 *
	 * @author Juraj Juričić
	 */
	private enum Operations {

		/** Adds the two numbers. */
		INCREMENT(Integer::sum, Double::sum),

		/** Subtracts the two numbers. */
		DECREMENT((a, b) -> a - b, (a, b) -> a - b),

		/** Multiplies the two numbers. */
		MULTIPLY((a, b) -> a * b, (a, b) -> a * b),

		/**
		 * Divides the two numbers; throws ArithmeticException if dividing by
		 * zero.
		 */
		DIVIDE((a, b) -> {
			if (b.intValue() == 0) {
				throw new ArithmeticException("Division by zero.");
			}
			return Integer.divideUnsigned(a, b);
		}, (a, b) -> {
			if (b.intValue() == 0) {
				throw new ArithmeticException("Division by zero.");
			}
			return a / b;
		}),

		/** Compares the given numbers. */
		NUM_COMPARE(Integer::compareTo, (a, b) -> Double.valueOf(Double.valueOf(a).compareTo(b)));

		/** The operation for calculating with the integers. */
		private BiFunction<Integer, Integer, Integer> intOperation;

		/** The operation for calculating with the doubles. */
		private BiFunction<Double, Double, Double> doubleOperation;

		/**
		 * Instantiates a new operation.
		 *
		 * @param intOperation
		 *            the operation for calculating with the integers
		 * @param doubleOperation
		 *            the operation for calculating with the integers
		 */
		private Operations(BiFunction<Integer, Integer, Integer> intOperation,
				BiFunction<Double, Double, Double> doubleOperation) {
			this.intOperation = intOperation;
			this.doubleOperation = doubleOperation;
		}

		/**
		 * Performs the operation on the given objects. The objects should
		 * conform with the type-specifications of the given operation.
		 *
		 * @param value
		 *            the first value
		 * @param other
		 *            the second (other) value
		 * @return the result of the operation
		 * @throws ClassCastException
		 *             thrown if the parsing is not possible (invalid type)
		 */
		public Object operate(Object value, Object other)
				throws ClassCastException {
			// check current value type
			if (!validType(value) || !validType(other)) {
				throw new ClassCastException("Invalid object type");
			}
			Number parsedValue = parseNumber(value);
			Number parsedOther = parseNumber(other);

			if (checkAnyTypes(Double.class, parsedValue, parsedOther)) {
				return this.doubleOperation.apply(parsedValue.doubleValue(),
						parsedOther.doubleValue());
			}
			if (checkAllTypes(Integer.class, parsedValue, parsedOther)) {
				return this.intOperation.apply(parsedValue.intValue(),
						parsedOther.intValue());
			}

			// should never reach this
			throw new ClassCastException("Invalid object type");
		}
	}
}
