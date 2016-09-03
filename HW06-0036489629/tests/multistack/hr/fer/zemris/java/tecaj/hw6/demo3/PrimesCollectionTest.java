package hr.fer.zemris.java.tecaj.hw6.demo3;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw6.demo3.PrimesCollection;

@SuppressWarnings("javadoc")
public class PrimesCollectionTest {
	private final Integer[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
			37, 41, 43, 47, 53 };

	@SuppressWarnings("unused")
	@Test
	public void constructorTest() {
		PrimesCollection primator = new PrimesCollection(15);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructorTest() {
		PrimesCollection primator = new PrimesCollection(0);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructorTest2() {
		PrimesCollection primator = new PrimesCollection(-1);
	}

	@Test
	public void primeNumbersTest() {
		PrimesCollection primator = new PrimesCollection(primes.length);

		int i = 0;
		for (Integer x : primator) {
			assertEquals(primes[i++], x);
		}
	}

	@Test
	public void outOfRangeTest() {
		PrimesCollection primator = new PrimesCollection(2);

		Iterator<Integer> primterator = primator.iterator();

		assertEquals(primes[0], primterator.next());
		assertEquals(primes[1], primterator.next());

		assertFalse(primterator.hasNext());

		try {
			primterator.next();
		} catch (NoSuchElementException e) {
			return;
		}
		
		fail("Expected NoSuchElementException.");
		
	}

}
