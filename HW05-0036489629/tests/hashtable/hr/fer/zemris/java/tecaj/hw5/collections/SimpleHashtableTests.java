package hr.fer.zemris.java.tecaj.hw5.collections;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

@SuppressWarnings("javadoc")
public class SimpleHashtableTests {

	SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
	
	public SimpleHashtableTests() {
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
	}
	
	@Test
	public void overwriteDataTest(){
		assertEquals(Integer.valueOf(2), examMarks.get("Ivana"));
		examMarks.put("Ivana", 5);
		assertEquals(Integer.valueOf(5), examMarks.get("Ivana"));
	}
	
	@Test
	public void cartesianProductTest(){
		String[] keys = {"Ante", "Ivana", "Jasna", "Kristina"};
		int i = 0; int j = 0;
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks){
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks){
				assertEquals(pair1.getKey(), keys[i % 4]);
				assertEquals(pair2.getKey(), keys[j++ % 4]);
				System.out.println(pair1.getKey() + " " + pair2.getKey());
			}
			i++;
		}
	}
	
	@Test
	public void containsTest(){
		assertTrue(examMarks.containsKey("Ivana"));
		assertFalse(examMarks.containsKey(null));
		assertTrue(examMarks.containsKey("Ante"));
	}
	
	@Test
	public void multipleHasNextTest(){
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		for(int i = 0; i < 10; i++){
			assertTrue(it.hasNext());
		}
		assertEquals("Ante", it.next().getKey());
		assertTrue(it.hasNext());
		
		for(int i = 0; i < 3; i++){
			it.next();
		}
		assertFalse(it.hasNext());
	}

	@Test
	public void iteratorRemoveTest(){
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		it.next();
		assertTrue(examMarks.containsKey("Ante"));
		it.remove();
		
		assertFalse(examMarks.containsKey("Ante"));
	}

}
