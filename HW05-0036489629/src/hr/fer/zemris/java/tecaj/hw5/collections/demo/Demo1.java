package hr.fer.zemris.java.tecaj.hw5.collections.demo;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * The Demonstration Class.
 * 
 * @author Juraj Juričić
 */
public class Demo1 {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String args[]) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes:
																			// 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes:
																			// 4

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}

	}
}
