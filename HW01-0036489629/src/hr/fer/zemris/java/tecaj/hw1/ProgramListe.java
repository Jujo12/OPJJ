package hr.fer.zemris.java.tecaj.hw1;

/**
 * A demostration and implementation of a simple linked list
 */
public class ProgramListe {

	/**
	 * List data structure
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		CvorListe cvor = new CvorListe();

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");

		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);

		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);

		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}

	/**
	 * Counts the elements in the list and returns the count. Complexity: O(n)
	 * 
	 * @param cvor
	 *            the root of the list
	 * @return the number of elements in the list
	 */
	private static int velicinaListe(CvorListe cvor) {
		if (cvor.sljedeci != null) {
			return velicinaListe(cvor.sljedeci) + 1;
		} else if (cvor.podatak != null) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Adds the element to the end of the list. Complexity: O(n)
	 * 
	 * @param prvi
	 *            the root of the list
	 * @param podatak
	 *            the element value to add to the list
	 * @return the root of the list
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe cvor;
		if (prvi.podatak == null) {
			prvi.podatak = podatak;
		} else if (prvi.sljedeci == null) {
			cvor = new CvorListe();
			prvi.sljedeci = cvor;
			cvor.podatak = podatak;
		} else {
			cvor = ubaci(prvi.sljedeci, podatak);
		}

		return prvi;
	}

	/**
	 * Prints the list to the standard output
	 * 
	 * @param cvor
	 *            the root of the list
	 */
	private static void ispisiListu(CvorListe cvor) {
		if (cvor.podatak != null) {
			System.out.println(cvor.podatak);
		}

		if (cvor.sljedeci != null) {
			ispisiListu(cvor.sljedeci);
		}
	}

	/**
	 * Sorts the list using a simple bubble sort. Complexity: O(n^2)
	 * 
	 * @param cvor
	 *            the root of the list to be sorted
	 * @return the root of the sorted list
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if (cvor.sljedeci == null) {
			// element count is < 2
			// could have used velicinaListe; this is much faster (O(1) vs O(n))
			return cvor; // sorted by default!
		}

		boolean sorted;
		// bubble sort
		do {
			CvorListe current = cvor;
			sorted = true;

			// foreach
			while (current != null && current.sljedeci != null) {
				if (current.podatak.compareTo(current.sljedeci.podatak) > 0) {
					// swap
					String temp = current.podatak;
					current.podatak = current.sljedeci.podatak;
					current.sljedeci.podatak = temp;

					sorted = false;
				}

				current = current.sljedeci;
			}
		} while (!sorted);

		return cvor;
	}
}
