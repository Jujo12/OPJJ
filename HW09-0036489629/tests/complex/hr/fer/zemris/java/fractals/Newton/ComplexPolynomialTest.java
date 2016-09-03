package hr.fer.zemris.java.fractals.Newton;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexPolynomialTest {
	// p1(x) = x + 3
	private ComplexPolynomial realPolynomial1 = new ComplexPolynomial(Complex.ONE, new Complex(3, 0));
	
	// p2(x) = 2x^3 + 3x^2 + 1
	private ComplexPolynomial realPolynomial2 = new ComplexPolynomial(new Complex(2, 0), new Complex(3, 0), Complex.ZERO, Complex.ONE);

	// p3(x) = x^2
	private ComplexPolynomial realPolynomial3 = new ComplexPolynomial(Complex.ONE, Complex.ZERO, Complex.ZERO);

	// q1(x) = - i*x + i
	private ComplexPolynomial complexPolynomial1 = new ComplexPolynomial(Complex.IM_NEG, Complex.IM);

	// q2(x) = (3-2i)*x^2 - 4i*x + i
	private ComplexPolynomial complexPolynomial2 = new ComplexPolynomial(new Complex(3, -2), new Complex(0, -4), Complex.IM);
	
	////////////

	@Test
	public void orderTest1(){
		assertEquals(1, realPolynomial1.order());
	}

	@Test
	public void orderTest2(){
		assertEquals(3, realPolynomial2.order());
	}

	@Test
	public void orderTest3(){
		assertEquals(2, realPolynomial3.order());
	}

	@Test
	public void orderTest4(){
		assertEquals(1, complexPolynomial1.order());
	}

	@Test
	public void orderTest5(){
		assertEquals(2, complexPolynomial2.order());
	}
	
	////////////
	
	@Test
	public void applyRealTest1() {
		// p1(3) = 6
		assertEquals(new Complex(6, 0), realPolynomial1.apply(new Complex(3, 0)));
	}

	@Test
	public void applyRealTest2() {
		// p2(3) = 82
		assertEquals(new Complex(82, 0), realPolynomial2.apply(new Complex(3, 0)));
	}
	
	@Test
	public void applyRealTest3() {
		// p3(7) = 49
		assertEquals(new Complex(49, 0), realPolynomial3.apply(new Complex(7, 0)));
	}
	
	@Test
	public void applyRealTest4(){
		// p2(0) = -254-160i
		assertEquals(Complex.ONE, realPolynomial2.apply(Complex.ZERO));
	}
	
	@Test
	public void applyComplexTest1(){
		// p1(4i) = 3+4i
		assertEquals(new Complex(3, 4), realPolynomial1.apply(new Complex(0, 4)));
	}

	@Test
	public void applyComplexTest2(){
		// p1(3-4i) = 3+4i
		assertEquals(new Complex(6, -4), realPolynomial1.apply(new Complex(3, -4)));
	}

	@Test
	public void applyComplexTest3(){
		// p2(3-4i) = -254-160i
		assertEquals(new Complex(-254, -160), realPolynomial2.apply(new Complex(3, -4)));
	}

	@Test
	public void applyComplexTest4(){
		// p2(-2i) = -11+16
		assertEquals(new Complex(-11, 16), realPolynomial2.apply(new Complex(0, -2)));
	}
	
	@Test
	public void applyComplexTest5(){
		// p2(8+8i) = -254-160i
		assertEquals(new Complex(-2047, 2432), realPolynomial2.apply(new Complex(8, 8)));
	}
	
	@Test
	public void applyComplexTest6(){
		// q1(3+2i) = 2-2i
		assertEquals(new Complex(2, -2), complexPolynomial1.apply(new Complex(3, 2)));
	}

	@Test
	public void applyComplexTest7(){
		// q1(3) = 2-2i
		assertEquals(new Complex(0, -2), complexPolynomial1.apply(new Complex(3, 0)));
	}

	@Test
	public void applyComplexTest8(){
		// q2(2) = 12-15i
		assertEquals(new Complex(12, -15), complexPolynomial2.apply(new Complex(2, 0)));
	}

	@Test
	public void applyComplexTest9(){
		// q2(-2+4i) = -52-15i
		assertEquals(new Complex(-52, -15), complexPolynomial2.apply(new Complex(-2, 4)));
	}
	
	////////////

	@Test
	public void multiplyTest1(){
		assertEquals(realPolynomial1.multiply(complexPolynomial1), complexPolynomial1.multiply(realPolynomial1));
	}

	@Test
	public void multiplyTest2(){
		ComplexPolynomial mul = realPolynomial1.multiply(realPolynomial2);
		assertEquals(new ComplexPolynomial(new Complex(2, 0), new Complex(9, 0), new Complex(9, 0), Complex.ONE, new Complex(3, 0)), mul);
	}
	
	@Test
	public void multiplyTest3(){
		ComplexPolynomial mul = complexPolynomial1.multiply(complexPolynomial2);
		assertEquals(new ComplexPolynomial(new Complex(-2, -3), new Complex(-2, 3), new Complex(5, 0), Complex.ONE_NEG), mul);
	}
	
	///////////

	@Test
	public void deriveTest1(){
		// 1
		ComplexPolynomial der = realPolynomial1.derive();
		assertEquals(new ComplexPolynomial(Complex.ONE), der);
	}

	@Test
	public void deriveTest2(){
		// 6x^2 + 6x
		ComplexPolynomial der = realPolynomial2.derive();
		
		assertEquals(new ComplexPolynomial(new Complex(6, 0), new Complex(6, 0), Complex.ZERO), der);
	}

	@Test
	public void deriveTest3(){
		// (6-4i)*x - 4i
		ComplexPolynomial der = complexPolynomial2.derive();
		
		assertEquals(new ComplexPolynomial(new Complex(6, -4), new Complex(0, -4)), der);
	}
	
	///////////

	@Test
	public void testHashCode(){
		assertEquals(1480000543, complexPolynomial2.hashCode());
	}
	

	@Test
	public void testNotEqualType(){
		assertNotEquals(new Object(), complexPolynomial2);
	}

	@Test
	public void testNotEqualNull(){
		assertNotEquals(null, complexPolynomial2);
	}

	@Test
	public void testNotEqualFactors(){
		assertNotEquals(new ComplexPolynomial(new Complex(2, 0), new Complex(3, 0), new Complex(4, 0)), new ComplexPolynomial(new Complex(-2, 0), new Complex(3, 0), new Complex(4, 0)));
	}
	
}
