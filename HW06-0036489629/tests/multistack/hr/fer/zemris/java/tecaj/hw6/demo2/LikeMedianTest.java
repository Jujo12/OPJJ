package hr.fer.zemris.java.tecaj.hw6.demo2;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw6.demo2.LikeMedian;

@SuppressWarnings("javadoc")
public class LikeMedianTest {

	private final Integer[] ARRAY_EVEN = {-3, 0, 4, 17, 23};
	private final Integer[] ARRAY_ODD = {-3, 0, 4, 17, 23, 42};
	private final Integer ARRAY_MED = 4;
	
	private <T extends Comparable<T>> void populateLikeMedian(T[] array, LikeMedian<T> likeMedian){
		for(T i : array){
			likeMedian.add(i);
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void validConstructorTest() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();
		LikeMedian<String> likeMedianString = new LikeMedian<>();
	}

	/*
	 * //should not compile public void invalidConstructorTest(){
	 * LikeMedian<Object> lm2 = new LikeMedian<>(); }
	 */

	@Test
	public void sequentialMedianEvenInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		for (int i = 1; i <= 14; i++) {
			likeMedianInt.add(i);
		}

		assertEquals(Integer.valueOf(7), likeMedianInt.get().get());
	}

	@Test
	public void sequentialMedianOddInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		for (int i = 1; i <= 13; i++) {
			likeMedianInt.add(i);
		}

		assertEquals(Integer.valueOf(7), likeMedianInt.get().get());
	}

	@Test
	public void orderedMedianEvenInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		populateLikeMedian(ARRAY_EVEN, likeMedianInt);

		assertEquals(ARRAY_MED, likeMedianInt.get().get());
	}

	@Test
	public void orderedMedianOddInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		populateLikeMedian(ARRAY_ODD, likeMedianInt);

		assertEquals(ARRAY_MED, likeMedianInt.get().get());
	}

	@Test
	public void orderedMedianDuplicateEvenInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		likeMedianInt.add(0);
		likeMedianInt.add(0);
		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(4);

		assertEquals(Integer.valueOf(4), likeMedianInt.get().get());
	}

	@Test
	public void orderedMedianDuplicateOddInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		likeMedianInt.add(0);
		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(4);

		assertEquals(Integer.valueOf(4), likeMedianInt.get().get());
	}
	

	@Test
	public void scrambledMedianEvenInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		Integer[] arr = Arrays.copyOf(ARRAY_EVEN, ARRAY_EVEN.length);
		Collections.shuffle(Arrays.asList(arr));

		populateLikeMedian(arr, likeMedianInt);

		assertEquals(ARRAY_MED, likeMedianInt.get().get());
	}

	@Test
	public void scramblededianOddInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		Integer[] arr = Arrays.copyOf(ARRAY_ODD, ARRAY_ODD.length);
		Collections.shuffle(Arrays.asList(arr));

		populateLikeMedian(arr, likeMedianInt);

		assertEquals(ARRAY_MED, likeMedianInt.get().get());
	}

	@Test
	public void scrambledMedianDuplicateEvenInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		likeMedianInt.add(4);
		likeMedianInt.add(0);
		likeMedianInt.add(0);
		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(4);

		assertEquals(Integer.valueOf(4), likeMedianInt.get().get());
	}

	@Test
	public void scrambledMedianDuplicateOddInt() {
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();

		likeMedianInt.add(4);
		likeMedianInt.add(4);
		likeMedianInt.add(0);
		likeMedianInt.add(4);
		likeMedianInt.add(4);

		assertEquals(Integer.valueOf(4), likeMedianInt.get().get());
	}
	
	@Test
	public void emptyLikeMedianTest(){
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();
		
		assertFalse(likeMedianInt.get().isPresent());
	}
	
	@Test
	public void iteratorTest(){
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();
		populateLikeMedian(ARRAY_ODD, likeMedianInt);

		int i = 0;
		for(Integer elem : likeMedianInt){
			assertEquals(ARRAY_ODD[i], elem);
			i++;
		}
	}
	
	@Test
	public void scambledIteratorTest(){
		LikeMedian<Integer> likeMedianInt = new LikeMedian<>();
		
		Integer[] arr = Arrays.copyOf(ARRAY_EVEN, ARRAY_EVEN.length);
		Collections.shuffle(Arrays.asList(arr));

		populateLikeMedian(arr, likeMedianInt);
		
		int i = 0;
		for(Integer elem : likeMedianInt){
			assertEquals(arr[i], elem);
			i++;
		}
	}

}
