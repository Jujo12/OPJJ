package hr.fer.zemris.java.cstr;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * CString unit tests. Feel free to test your own, or else's code with it.
 * 
 * @author Juraj Juričić
 *
 */
@SuppressWarnings("javadoc")
public class CStringTests {
	/*
	 * Test sentence, used repeatedly
	 */
	private final String SENTENCE = "Quick brown fox jumped over a lazy dog.";

	/*
	 * Constructor tests:
	 */

	@Test(expected = Exception.class)
	public void nullConstructorTest() {
		new CString(null, 0, 5);
	}

	@Test(expected = Exception.class)
	public void illegalOffsetTest() {
		char[] array = { 'a', 'a', 'a', 'a', 'a' }; // 5 elements
		new CString(array, 5, 1);
	}

	@Test(expected = Exception.class)
	public void negativeLengthTest() {
		char[] array = { 'a', 'a', 'a', 'a', 'a' }; // 5 elements
		new CString(array, 3, -1);
	}

	@Test(expected = Exception.class)
	public void illegalLengthTest() {
		char[] array = { 'a', 'a', 'a', 'a', 'a' }; // 5 elements
		new CString(array, 3, 3);
	}

	@Test
	public void staticFactoryTest() {
		CString.fromString(SENTENCE);
	}

	@Test
	public void toStringTest() {
		CString test = CString.fromString(SENTENCE);
		assertEquals(SENTENCE, test.toString());
	}

	private final char[] array = { 'F', 'E', 'R', ' ', 'Z', 'a', 'g', 'r', 'e', 'b' };

	@Test
	public void fromArrayTest() {
		CString test = new CString(array, 0, array.length);
		assertEquals(String.valueOf(array), test.toString());
	}

	@Test
	public void fromSubArrayTest() {
		CString test = new CString(array, 4, 6);
		assertEquals("Zagreb", test.toString());
	}

	@Test
	public void fromOtherCStringTest() {
		CString first = CString.fromString(SENTENCE);
		CString second = new CString(first);

		assertEquals(SENTENCE, second.toString());
	}

	@Test
	public void fromEmptyStringTest() {
		assertEquals("", CString.fromString("").toString());
	}

	/*
	 * misc tests
	 */
	@Test
	public void lengthTest() {
		assertEquals(SENTENCE.length(), CString.fromString(SENTENCE).length());

		assertEquals(0, CString.fromString("").length());
	}

	@Test
	public void charAtTest() {
		CString testString = CString.fromString(SENTENCE);

		for (int i = 0, length = SENTENCE.length(); i < length; i++) {
			assertEquals(SENTENCE.charAt(i), testString.charAt(i));
		}
	}

	@Test
	public void toCharArrayTest() {
		CString testString = CString.fromString(SENTENCE);
		testCharArrayContentEquality(SENTENCE.toCharArray(), testString.toCharArray());

		// empty char array
		testCharArrayContentEquality(CString.fromString("").toCharArray(), new char[0]);
	}

	@Test
	public void addTest() {
		CString testString = CString.fromString(SENTENCE);

		assertEquals(SENTENCE + SENTENCE, testString.add(testString).toString());
		assertEquals(SENTENCE + " ", testString.add(CString.fromString(" ")).toString());
	}

	/*
	 * Search tests (indexOf, contains, startWith, endsWith):
	 */
	@Test
	public void indexOfTest() {
		CString testString = CString.fromString(SENTENCE);

		assertEquals(0, testString.indexOf('Q'));
		assertEquals(5, testString.indexOf(' '));
		assertEquals(8, testString.indexOf('o'));
		assertEquals(38, testString.indexOf('.'));

		// not found:
		assertEquals(-1, testString.indexOf('h'));
		assertEquals(-1, testString.indexOf('q'));
	}

	@Test
	public void startsWithTest() {
		CString testString = CString.fromString(SENTENCE);

		assertTrue(testString.startsWith(CString.fromString("Quick")));
		assertTrue(testString.startsWith(CString.fromString("")));
		assertTrue(testString.startsWith(CString.fromString(SENTENCE)));

		assertFalse(testString.startsWith(CString.fromString("Hello world")));
		assertFalse(testString.startsWith(CString.fromString(SENTENCE.toLowerCase())));
		assertFalse(testString.startsWith(CString.fromString("Quick brown dog")));
	}

	@Test
	public void endsWithTest() {
		CString testString = CString.fromString(SENTENCE);

		assertTrue(testString.endsWith(CString.fromString("dog.")));
		assertTrue(testString.endsWith(CString.fromString("a lazy dog.")));
		assertTrue(testString.endsWith(CString.fromString("")));
		assertTrue(testString.endsWith(CString.fromString(SENTENCE)));

		assertFalse(testString.startsWith(CString.fromString("Hello world")));
		assertFalse(testString.startsWith(CString.fromString(SENTENCE.toLowerCase())));
		assertFalse(testString.startsWith(CString.fromString("dog")));
	}

	@Test
	public void containsTest() {
		CString testString = CString.fromString(SENTENCE);

		assertTrue(testString.contains(CString.fromString("Quick")));
		assertTrue(testString.contains(CString.fromString("dog.")));
		assertTrue(testString.contains(CString.fromString(" dog.")));
		assertTrue(testString.contains(CString.fromString(" ")));

		assertFalse(testString.contains(CString.fromString("Hello")));
		assertFalse(testString.contains(CString.fromString("god")));
	}

	@Test
	public void loopedContainsTest() {
		String s = "q";
		for (int i = 0; i < 10; i++) {
			s += s;
		}
		s += "ww";

		CString testString = CString.fromString(s);
		assertTrue(testString.contains(CString.fromString("qw")));
		assertTrue(testString.contains(CString.fromString("qww")));
		assertTrue(testString.contains(CString.fromString("qqww")));
		assertTrue(testString.contains(CString.fromString("qqqww")));
		assertTrue(testString.contains(CString.fromString("qqqqww")));

		assertFalse(testString.contains(CString.fromString("www")));
		assertFalse(testString.contains(CString.fromString("wq")));
		assertFalse(testString.contains(CString.fromString("qwqw")));
		assertFalse(testString.contains(CString.fromString("q" + s)));
	}

	/*
	 * Warning: this code might take a long to execute if the CString.contains()
	 * implementation is poorly written. Use with caution.
	 */
	@Test
	public void largeContainsTest() {
		String s = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"
				+ "when an unknown printer took a galley of type and scrambled it to make a type"
				+ "specimen book. It has survived not only five centuries, but also the leap into"
				+ "electronic typesetting, remaining essentially unchanged. It was popularised"
				+ "in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,"
				+ "and more recently with desktop publishing software like Aldus PageMaker including"
				+ "versions of the text. It is a long established fact that a reader will be"
				+ "distracted by the readable content of a page when looking at its layout. The point"
				+ "of using Lorem Ipsum is that it has a more-or-less normal distribution of letters,"
				+ "as opposed to using 'Content here, content here', making it look like readable"
				+ "English. Many desktop publishing packages and web page editors now use Lorem Ipsum"
				+ "as their default model text, and a search for 'lorem ipsum' will uncover many web"
				+ "sites still in their infancy. Various versions have evolved over the years, sometimes"
				+ "by accident, sometimes on purpose (injected humour and the like). Lorem Ipsum.";

		CString testString = CString.fromString(s);

		assertTrue(testString.contains(CString.fromString("has survived")));
		assertTrue(testString.contains(CString.fromString("sheets containing")));
		assertTrue(testString.contains(CString.fromString("galley")));
		assertTrue(testString.contains(CString.fromString("recently with")));
		assertTrue(testString.contains(CString.fromString("versions of the text.")));
		assertTrue(testString.contains(CString.fromString("unchanged. It")));
		assertTrue(testString.contains(CString.fromString("Lorem Ipsum.")));
		assertTrue(testString.contains(CString.fromString("1960")));
		assertTrue(testString.contains(CString.fromString("Lorem Ipsum is")));
		assertTrue(testString.contains(CString.fromString("only five centuries,")));
		assertTrue(testString.contains(CString.fromString("lorem ipsum")));
		assertTrue(testString.contains(CString.fromString("(injected humour and the like).")));
		assertTrue(testString.contains(CString.fromString("Lorem Ipsum.")));
		assertTrue(testString.contains(CString.fromString("Content here, content here")));
		assertTrue(testString.contains(CString.fromString("and a search for 'lorem ipsum'")));
		assertTrue(testString.contains(CString.fromString("infancy")));
		assertTrue(testString.contains(CString.fromString(s)));
		assertTrue(testString.contains(CString.fromString(s.substring(1))));
		assertTrue(testString.contains(CString.fromString("Various versions")));
		assertTrue(testString.contains(CString.fromString(". ")));

		assertTrue(testString.substring(2, testString.length() - 10)
				.contains(testString.substring(15, testString.length() - 50)));

		// some speed exibitions (total 701520 runs, should be done in < 2s on
		// i7 PC)
		for (int i = 0; i < s.length(); i++) {
			for (int j = i; j < s.length(); j++) {
				assertTrue(testString.contains(CString.fromString(s.substring(i, j))));

				// uncomment this to also test the speed of substring method:
				// assertTrue(testString.contains(CString.fromString(s).substring(i,
				// j)));
			}
		}
	}

	/*
	 * Substring tests:
	 */
	@Test(expected = Exception.class)
	public void illegalSubStringStartIndexTest() {
		CString testString = CString.fromString(SENTENCE);

		testString.substring(-1, 5);
	}

	@Test(expected = Exception.class)
	public void illegalSubStringLengthTest() {
		CString testString = CString.fromString(SENTENCE);

		testString.substring(0, SENTENCE.length() + 1);
	}

	@Test(expected = Exception.class)
	public void illegalSubStringEndIndexTest() {
		CString testString = CString.fromString(SENTENCE);

		testString.substring(10, 9);
	}

	@Test
	public void subStringTest() {
		CString testString = CString.fromString(SENTENCE);

		for (int i = 0; i < SENTENCE.length(); i++) {
			for (int j = i; j < SENTENCE.length(); j++) {
				assertEquals(SENTENCE.substring(i, j), testString.substring(i, j).toString());
			}
		}

		assertEquals("", testString.substring(10, 10).toString());
	}

	/*
	 * To run this test, temporarily set visibility of "data" array to public,
	 * then uncomment it. Verifies that the substring arrays point to the same
	 * location
	 */
	/*
	 * @Test public void subStringPointerTest(){ CString testString =
	 * CString.fromString(SENTENCE);
	 * 
	 * assertEquals(testString.data, testString.substring(1, 5).data);
	 * assertEquals(testString.data, testString.substring(0,
	 * testString.length()).data); }
	 */

	/*
	 * replaceAll tests:
	 */

	@Test
	public void replaceAllCharTest() {
		assertEquals(SENTENCE.replace(' ', '.'), CString.fromString(SENTENCE).replaceAll(' ', '.').toString());
		assertEquals(SENTENCE.replace('Q', 'b'), CString.fromString(SENTENCE).replaceAll('Q', 'b').toString());
		assertEquals(SENTENCE, CString.fromString(SENTENCE).replaceAll('h', 'A').toString());
	}

	@Test
	public void replaceAllStringTest() {
		// simple test
		assertEquals(SENTENCE.replaceAll("fox", "cat"), CString.fromString(SENTENCE)
				.replaceAll(CString.fromString("fox"), CString.fromString("cat")).toString());

		// same content test
		assertEquals(SENTENCE.replaceAll("fox", "dog"), CString.fromString(SENTENCE)
				.replaceAll(CString.fromString("fox"), CString.fromString("dog")).toString());

		// multiple occurence test
		assertEquals("Quick brown fox jumped over a lazy brown dog.".replaceAll("brown", "white"),
				CString.fromString("Quick brown fox jumped over a lazy brown dog.")
						.replaceAll(CString.fromString("brown"), CString.fromString("white")).toString());
		
		// bigger size test
		assertEquals(SENTENCE.replaceAll("fox", "bear"), CString.fromString(SENTENCE)
				.replaceAll(CString.fromString("fox"), CString.fromString("bear")).toString());
		
		//smaller size test
		assertEquals(SENTENCE.replaceAll("jumped over", "cried to"), CString.fromString(SENTENCE)
				.replaceAll(CString.fromString("jumped over"), CString.fromString("cried to")).toString());
		
		// same content test
		assertEquals("ababab".replaceAll("ab", "abab"), CString.fromString("ababab")
				.replaceAll(CString.fromString("ab"), CString.fromString("abab")).toString());

		//empty needle, single-charred replacement
		assertEquals(SENTENCE.replace("", "$"), CString.fromString(SENTENCE).replaceAll(CString.fromString(""), CString.fromString("$")).toString());
		

		//empty needle, multi-charred replacement
		assertEquals(SENTENCE.replace("", "REPL"), CString.fromString(SENTENCE).replaceAll(CString.fromString(""), CString.fromString("REPL")).toString());
	}

	private void testCharArrayContentEquality(char[] first, char[] second) {
		assertEquals(first.length, second.length);

		for (int i = 0; i < first.length; i++) {
			assertEquals(first[i], second[i]);
		}
	}

}
