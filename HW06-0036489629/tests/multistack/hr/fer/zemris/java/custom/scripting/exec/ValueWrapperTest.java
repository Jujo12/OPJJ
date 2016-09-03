package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

@SuppressWarnings("javadoc")
public class ValueWrapperTest {
	
	private final String TEST_STRING = "FER";

	/*
	 * Setters and getters
	 */
	@Test
	public void constructorTest() {
		assertEquals(4, new ValueWrapper(4).getValue());
		assertEquals(-7, new ValueWrapper(-7).getValue());
		assertEquals(-7.188, new ValueWrapper(-7.188).getValue());
		assertEquals(Integer.class, new ValueWrapper(Integer.class).getValue());
		assertEquals(null, new ValueWrapper(null).getValue());

		// doublewrapper
		ValueWrapper v = new ValueWrapper(new Object());
		assertEquals(v, new ValueWrapper(v).getValue());
	}

	@Test
	public void setValueTest() {
		ValueWrapper val = new ValueWrapper(null);

		val.setValue(42);
		assertEquals(42, val.getValue());

		Date date = new Date();
		val.setValue(date);
		assertEquals(date, val.getValue());

		val.setValue(null);
		assertEquals(null, val.getValue());
	}

	/*
	 * Addition (increment)
	 */
	@Test
	public void incrementIntIntTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.increment(5);

		assertEquals(2 + 5, val.getValue());
	}

	@Test
	public void incrementNullIntTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.increment(5);

		assertEquals(0 + 5, val.getValue());
	}

	@Test
	public void incrementIntNullTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.increment(null);

		assertEquals(2 + 0, val.getValue());
	}

	@Test
	public void incrementNullNullTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.increment(null);

		assertEquals(0 + 0, val.getValue());
	}

	@Test
	public void incrementIntDoubleTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.increment(5.3);

		assertEquals(2 + 5.3, val.getValue());
	}

	@Test
	public void incrementDoubleIntTest() {
		ValueWrapper val = new ValueWrapper(2.3);
		val.increment(5);

		assertEquals(2.3 + 5, val.getValue());
	}

	@Test
	public void incrementDoubleDoubleTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.increment(5.3);

		assertEquals(2.5 + 5.3, val.getValue());
	}

	@Test
	public void incrementDoubleNullTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.increment(null);

		assertEquals(2.5 + 0, val.getValue());
	}

	@Test
	public void incrementNullDoubleTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.increment(5.3);

		assertEquals(0 + 5.3, val.getValue());
	}

	@Test
	public void incrementStringIntTest() {
		ValueWrapper val = new ValueWrapper("2");
		val.increment(5);

		assertEquals(2 + 5, val.getValue());
	}

	@Test
	public void incrementIntStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.increment("5");

		assertEquals(2 + 5, val.getValue());
	}

	@Test
	public void incrementStringStringTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.increment("5.3");

		assertEquals(2.5 + 5.3, val.getValue());
	}

	@Test
	public void incrementStringDoubleTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.increment(5.3);

		assertEquals(2.5 + 5.3, val.getValue());
	}

	@Test
	public void incrementStringStringTest2() {
		ValueWrapper val = new ValueWrapper("2E-3");
		val.increment("5.14E3");

		assertEquals(2E-3 + 5.14E3, val.getValue());
	}

	@Test
	public void incrementStringNullTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.increment(null);

		assertEquals(2.5, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void incrementObjectIntTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.increment(4);
	}

	@Test(expected = RuntimeException.class)
	public void incrementIntObjectTest() {
		ValueWrapper val = new ValueWrapper(4);
		val.increment(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void incrementObjectDoubleTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.increment(4.7);
	}

	@Test(expected = RuntimeException.class)
	public void incrementDoubleObjectTest() {
		ValueWrapper val = new ValueWrapper(4.7);
		val.increment(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void incrementObjectStringTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.increment("14");
	}

	@Test(expected = RuntimeException.class)
	public void incrementStringObjectTest() {
		ValueWrapper val = new ValueWrapper("14");
		val.increment(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void incrementNullObjectTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.increment(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void incrementObjectNullTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.increment(null);
	}

	@Test(expected = RuntimeException.class)
	public void incrementIntInvalidStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.increment("Marko Čupić je spasio život dvoje djece koristeći"
				+ "3 D-bistabila, multipleksor i Hammingov koder");
	}

	/*
	 * Subtraction (decrement)
	 */
	@Test
	public void decrementIntIntTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.decrement(5);

		assertEquals(2 - 5, val.getValue());
	}

	@Test
	public void decrementNullIntTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.decrement(5);

		assertEquals(0 - 5, val.getValue());
	}

	@Test
	public void decrementIntNullTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.decrement(null);

		assertEquals(2 - 0, val.getValue());
	}

	@Test
	public void decrementNullNullTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.decrement(null);

		assertEquals(0 - 0, val.getValue());
	}

	@Test
	public void decrementIntDoubleTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.decrement(5.3);

		assertEquals(2 - 5.3, val.getValue());
	}

	@Test
	public void decrementDoubleIntTest() {
		ValueWrapper val = new ValueWrapper(2.3);
		val.decrement(5);

		assertEquals(2.3 - 5, val.getValue());
	}

	@Test
	public void decrementDoubleDoubleTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.decrement(5.3);

		assertEquals(2.5 - 5.3, val.getValue());
	}

	@Test
	public void decrementDoubleNullTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.decrement(null);

		assertEquals(2.5 - 0, val.getValue());
	}

	@Test
	public void decrementNullDoubleTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.decrement(5.3);

		assertEquals(0 - 5.3, val.getValue());
	}

	@Test
	public void decrementStringIntTest() {
		ValueWrapper val = new ValueWrapper("2");
		val.decrement(5);

		assertEquals(2 - 5, val.getValue());
	}

	@Test
	public void decrementIntStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.decrement("5");

		assertEquals(2 - 5, val.getValue());
	}

	@Test
	public void decrementStringStringTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.decrement("5.3");

		assertEquals(2.5 - 5.3, val.getValue());
	}

	@Test
	public void decrementStringDoubleTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.decrement(5.3);

		assertEquals(2.5 - 5.3, val.getValue());
	}

	@Test
	public void decrementStringStringTest2() {
		ValueWrapper val = new ValueWrapper("2E-3");
		val.decrement("5.14E3");

		assertEquals(2E-3 - 5.14E3, val.getValue());
	}

	@Test
	public void decrementStringNullTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.decrement(null);

		assertEquals(2.5 - 0, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void decrementObjectIntTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.decrement(4);
	}

	@Test(expected = RuntimeException.class)
	public void decrementIntObjectTest() {
		ValueWrapper val = new ValueWrapper(4);
		val.decrement(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void decrementObjectDoubleTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.decrement(4.7);
	}

	@Test(expected = RuntimeException.class)
	public void decrementDoubleObjectTest() {
		ValueWrapper val = new ValueWrapper(4.7);
		val.decrement(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void decrementObjectStringTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.decrement("14");
	}

	@Test(expected = RuntimeException.class)
	public void decrementStringObjectTest() {
		ValueWrapper val = new ValueWrapper("14");
		val.decrement(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void decrementNullObjectTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.decrement(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void decrementObjectNullTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.decrement(null);
	}

	@Test(expected = RuntimeException.class)
	public void decrementIntInvalidStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.decrement(TEST_STRING);
	}

	/*
	 * Multiplication
	 */
	@Test
	public void multiplyIntIntTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.multiply(5);

		assertEquals(2 * 5, val.getValue());
	}

	@Test
	public void multiplyNullIntTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.multiply(5);

		assertEquals(0 * 5, val.getValue());
	}

	@Test
	public void multiplyIntNullTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.multiply(null);

		assertEquals(2 * 0, val.getValue());
	}

	@Test
	public void multiplyNullNullTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.multiply(null);

		assertEquals(0 * 0, val.getValue());
	}

	@Test
	public void multiplyIntDoubleTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.multiply(5.3);

		assertEquals(2 * 5.3, val.getValue());
	}

	@Test
	public void multiplyDoubleIntTest() {
		ValueWrapper val = new ValueWrapper(2.3);
		val.multiply(5);

		assertEquals(2.3 * 5, val.getValue());
	}

	@Test
	public void multiplyDoubleDoubleTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.multiply(5.3);

		assertEquals(2.5 * 5.3, val.getValue());
	}

	@Test
	public void multiplyDoubleNullTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.multiply(null);

		assertEquals(2.5 * 0, val.getValue());
	}

	@Test
	public void multiplyNullDoubleTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.multiply(5.3);

		assertEquals(0 * 5.3, val.getValue());
	}

	@Test
	public void multiplyStringIntTest() {
		ValueWrapper val = new ValueWrapper("2");
		val.multiply(5);

		assertEquals(2 * 5, val.getValue());
	}

	@Test
	public void multiplyIntStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.multiply("5");

		assertEquals(2 * 5, val.getValue());
	}

	@Test
	public void multiplyStringStringTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.multiply("5.3");

		assertEquals(2.5 * 5.3, val.getValue());
	}

	@Test
	public void multiplyStringDoubleTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.multiply(5.3);

		assertEquals(2.5 * 5.3, val.getValue());
	}

	@Test
	public void multiplyStringStringTest2() {
		ValueWrapper val = new ValueWrapper("2E-3");
		val.multiply("5.14E3");

		assertEquals(2E-3 * 5.14E3, val.getValue());
	}

	@Test
	public void multiplyStringNullTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.multiply(null);

		assertEquals(2.5 * 0, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void multiplyObjectIntTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.multiply(4);
	}

	@Test(expected = RuntimeException.class)
	public void multiplyIntObjectTest() {
		ValueWrapper val = new ValueWrapper(4);
		val.multiply(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void multiplyObjectDoubleTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.multiply(4.7);
	}

	@Test(expected = RuntimeException.class)
	public void multiplyDoubleObjectTest() {
		ValueWrapper val = new ValueWrapper(4.7);
		val.multiply(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void multiplyObjectStringTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.multiply("14");
	}

	@Test(expected = RuntimeException.class)
	public void multiplyStringObjectTest() {
		ValueWrapper val = new ValueWrapper("14");
		val.multiply(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void multiplyNullObjectTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.multiply(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void multiplyObjectNullTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.multiply(null);
	}

	@Test(expected = RuntimeException.class)
	public void multiplyIntInvalidStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.multiply(TEST_STRING);
	}

	/*
	 * Division
	 */
	@Test
	public void divideIntIntTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.divide(5);

		assertEquals(2 / 5, val.getValue());
	}

	@Test
	public void divideNullIntTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.divide(5);

		assertEquals(0 / 5, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void divideIntNullTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.divide(null);

		assertEquals(2 / 0, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void divideNullNullTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.divide(null);
	}

	@Test
	public void divideIntDoubleTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.divide(5.3);

		assertEquals(2 / 5.3, val.getValue());
	}

	@Test
	public void divideDoubleIntTest() {
		ValueWrapper val = new ValueWrapper(2.3);
		val.divide(5);

		assertEquals(2.3 / 5, val.getValue());
	}

	@Test
	public void divideDoubleDoubleTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.divide(5.3);

		assertEquals(2.5 / 5.3, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void divideDoubleNullTest() {
		ValueWrapper val = new ValueWrapper(2.5);
		val.divide(null);

		assertEquals(2.5 / 0, val.getValue());
	}

	@Test
	public void divideNullDoubleTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.divide(5.3);

		assertEquals(0 / 5.3, val.getValue());
	}

	@Test
	public void divideStringIntTest() {
		ValueWrapper val = new ValueWrapper("2");
		val.divide(5);

		assertEquals(2 / 5, val.getValue());
	}

	@Test
	public void divideIntStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.divide("5");

		assertEquals(2 / 5, val.getValue());
	}

	@Test
	public void divideStringStringTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.divide("5.3");

		assertEquals(2.5 / 5.3, val.getValue());
	}

	@Test
	public void divideStringDoubleTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.divide(5.3);

		assertEquals(2.5 / 5.3, val.getValue());
	}

	@Test
	public void divideStringStringTest2() {
		ValueWrapper val = new ValueWrapper("2E-3");
		val.divide("5.14E3");

		assertEquals(2E-3 / 5.14E3, val.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void divideStringNullTest() {
		ValueWrapper val = new ValueWrapper("2.5");
		val.divide(null);
	}

	@Test(expected = RuntimeException.class)
	public void divideObjectIntTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.divide(4);
	}

	@Test(expected = RuntimeException.class)
	public void divideIntObjectTest() {
		ValueWrapper val = new ValueWrapper(4);
		val.divide(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void divideObjectDoubleTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.divide(4.7);
	}

	@Test(expected = RuntimeException.class)
	public void divideDoubleObjectTest() {
		ValueWrapper val = new ValueWrapper(4.7);
		val.divide(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void divideObjectStringTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.divide("14");
	}

	@Test(expected = RuntimeException.class)
	public void divideStringObjectTest() {
		ValueWrapper val = new ValueWrapper("14");
		val.divide(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void divideNullObjectTest() {
		ValueWrapper val = new ValueWrapper(null);
		val.divide(new Object());
	}

	@Test(expected = RuntimeException.class)
	public void divideObjectNullTest() {
		ValueWrapper val = new ValueWrapper(new Object());
		val.divide(null);
	}

	@Test(expected = RuntimeException.class)
	public void divideIntInvalidStringTest() {
		ValueWrapper val = new ValueWrapper(2);
		val.divide(TEST_STRING);
	}

	/*
	 * Comparison
	 */
	@Test
	public void numCompareIntIntEqualTest() {
		ValueWrapper val = new ValueWrapper(5);
		assertEquals(0, val.numCompare(5));
	}

	@Test
	public void numCompareDoubleDoubleEqualTest() {
		ValueWrapper val = new ValueWrapper(5.744523);
		assertEquals(0, val.numCompare(5.744523));
	}

	@Test
	public void numCompareDoubleDoubleEqualPreciseTest() {
		ValueWrapper val = new ValueWrapper(Math.PI / 31);
		assertEquals(0, val.numCompare(Math.PI / 31));
	}

	@Test
	public void numCompareNullNullTest() {
		ValueWrapper val = new ValueWrapper(null);
		assertEquals(0, val.numCompare(null));
	}

	@Test
	public void numCompareNullIntEqualsTest() {
		ValueWrapper val = new ValueWrapper(null);
		assertEquals(0, val.numCompare(0));
	}

	@Test
	public void numCompareNullDoubleEqualsTest() {
		ValueWrapper val = new ValueWrapper(null);
		assertEquals(0, val.numCompare(0.0));
	}

	@Test
	public void numCompareIntDoubleEqualsTest() {
		ValueWrapper val = new ValueWrapper(42);
		assertEquals(0, val.numCompare(42.0));
	}

	@Test
	public void numCompareDoubleIntEqualsTest() {
		ValueWrapper val = new ValueWrapper(42.0);
		assertEquals(0, val.numCompare(42));
	}

	@Test
	public void numCompareDoubleIntLessThanTest() {
		ValueWrapper val = new ValueWrapper(Math.PI);
		assertTrue(val.numCompare(4) < 0);
	}

	@Test
	public void numCompareIntDoubleLessThanTest() {
		ValueWrapper val = new ValueWrapper(2);
		assertTrue(val.numCompare(Math.E) < 0);
	}

	@Test
	public void numCompareDoubleIntGreaterThanTest() {
		ValueWrapper val = new ValueWrapper(Math.PI);
		assertTrue(val.numCompare(3) > 0);
	}

	@Test
	public void numCompareIntDoubleGreaterThanTest() {
		ValueWrapper val = new ValueWrapper(3);
		assertTrue(val.numCompare(Math.E) > 0);
	}

	@Test(expected = RuntimeException.class)
	public void numCompareInvalidStringIntTest() {
		ValueWrapper val = new ValueWrapper(TEST_STRING);
		val.numCompare(3);
	}

}