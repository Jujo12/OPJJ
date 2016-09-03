package hr.fer.zemris.java.fractals.Newton;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexRootedPolynomialTest {
	// p1(x) = 2x^2
	private ComplexRootedPolynomial realPolynomial = new ComplexRootedPolynomial(new Complex(2, 0), new Complex(-2, 0));
	private ComplexRootedPolynomial complexPolynomial = new ComplexRootedPolynomial(new Complex(2, 3), new Complex(-2, 1), new Complex(4, -3));
	
	private final Complex[] COMPLEX_NUMBERS = {new Complex(2, 0), new Complex(-2, 0), new Complex(2, 3), new Complex(-2, 1), new Complex(4, -3), new Complex(3, 3)};
	
	@Test
	public void applyTest1(){
		ComplexPolynomial cp = realPolynomial.toComplexPolynom();

		assertEquals(Complex.ZERO, realPolynomial.apply(new Complex(2, 0)));
		assertEquals(Complex.ZERO, realPolynomial.apply(new Complex(-2, 0)));
		
		for(Complex c : COMPLEX_NUMBERS){
			assertEquals(cp.apply(c), realPolynomial.apply(c));
		}
	}

	@Test
	public void applyTest3(){
		ComplexPolynomial cp = complexPolynomial.toComplexPolynom();

		assertEquals(Complex.ZERO, complexPolynomial.apply(new Complex(2, 3)));
		assertEquals(Complex.ZERO, complexPolynomial.apply(new Complex(-2, 1)));
		assertEquals(Complex.ZERO, complexPolynomial.apply(new Complex(4, -3)));
		
		for(Complex c : COMPLEX_NUMBERS){
			assertEquals(cp.apply(c), complexPolynomial.apply(c));
		}
	}

	@Test
	public void closestRootForTest1(){
		assertEquals(0, realPolynomial.indexOfClosestRootFor(new Complex(2, 0.5), 0.5 + 1E-6));
	}

	@Test
	public void closestRootForTest2(){
		assertEquals(1, realPolynomial.indexOfClosestRootFor(new Complex(-1, 0.5), 1.12));
	}

	@Test
	public void closestRootForTest3(){
		assertEquals(-1, realPolynomial.indexOfClosestRootFor(new Complex(2, 0.5), 0.5 - 1E-6));
	}

	@Test
	public void closestRootForTest4(){
		assertEquals(0, complexPolynomial.indexOfClosestRootFor(new Complex(2, 3), 1E-6));
	}

	@Test
	public void closestRootForTest5(){
		assertEquals(2, complexPolynomial.indexOfClosestRootFor(new Complex(3, -1E-6), 5));
	}

	@Test
	public void closestRootForTest6(){
		assertEquals(-1, complexPolynomial.indexOfClosestRootFor(new Complex(0, -3), 4 - 1E-6));
	}
	
}
