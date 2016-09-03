package hr.fer.zemris.java.fractals.Newton;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexParserTest {

	@Test
	public void stringConstructorTest1() {
		Complex c = new Complex("3+i2");
		assertEquals(new Complex(3, 2), c);
	}

	@Test
	public void stringConstructorTest2() {
		Complex c = new Complex("3+i0");
		assertEquals(new Complex(3, 0), c);
	}

	@Test
	public void stringConstructorTest3() {
		Complex c = new Complex("3+i");
		assertEquals(new Complex(3, 1), c);
	}

	@Test
	public void stringConstructorTest4() {
		Complex c = new Complex("0+i");
		assertEquals(new Complex(0, 1), c);
	}

	@Test
	public void stringConstructorTest5() {
		Complex c = new Complex("0+i0");
		assertEquals(new Complex(0, 0), c);
	}

	@Test
	public void stringConstructorTest6() {
		Complex c = new Complex("7");
		assertEquals(new Complex(7, 0), c);
	}

	@Test
	public void stringConstructorTest7() {
		Complex c = new Complex("-i");
		assertEquals(new Complex(0, -1), c);
	}

	@Test
	public void stringConstructorTest8() {
		Complex c = new Complex("i4");
		assertEquals(new Complex(0, 4), c);
	}

}
