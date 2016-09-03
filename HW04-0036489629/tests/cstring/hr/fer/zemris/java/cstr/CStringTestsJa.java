package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class CStringTestsJa {
	@Test
	public void testConstructedCStringNotNull() {
		CString string = new CString(new char[] { '1', '2', '3' });

		assertNotEquals(null, string);
	}

	@Test
	public void testCopyConstructorNotNull() {
		CString string = new CString(new char[] { '1', '2', '3' });

		CString string2 = new CString(string);
		assertNotEquals(null, string2);
	}

	@Test
	public void testCharacterAt() {
		CString string = new CString(new char[] { '0', '1', '2', '3', '4', '5', '6' });

		assertEquals('0', string.charAt(0));
		assertEquals('1', string.charAt(1));
		assertEquals('2', string.charAt(2));
		assertEquals('3', string.charAt(3));
		assertEquals('4', string.charAt(4));
		assertEquals('5', string.charAt(5));
		assertEquals('6', string.charAt(6));
	}

	@Test
	public void testToCharArray() {
		CString string = new CString(new char[] { '0', '1', '2', '3', '4', '5', '6' });

		char[] data = string.toCharArray();
		assertEquals('0', data[0]);
		assertEquals('1', data[1]);
		assertEquals('2', data[2]);
		assertEquals('3', data[3]);
		assertEquals('4', data[4]);
		assertEquals('5', data[5]);
		assertEquals('6', data[6]);

		data[3] = 't';
		assertEquals('3', string.charAt(3));
	}

	@Test
	public void testFromString() {
		CString string = CString.fromString("0123456");

		assertEquals('0', string.charAt(0));
		assertEquals('1', string.charAt(1));
		assertEquals('2', string.charAt(2));
		assertEquals('3', string.charAt(3));
		assertEquals('4', string.charAt(4));
		assertEquals('5', string.charAt(5));
		assertEquals('6', string.charAt(6));
	}

	@Test
	public void testToString() {
		CString string = CString.fromString("0123456");
		assertEquals("0123456", string.toString());
	}

	@Test
	public void testIndexOf() {
		CString string = CString.fromString("abcdefg");

		assertEquals(-1, string.indexOf('1'));
		assertEquals(-1, string.indexOf('0'));
		assertEquals(-1, string.indexOf('h'));
		assertEquals(-1, string.indexOf('-'));
		assertEquals(0, string.indexOf('a'));
		assertEquals(1, string.indexOf('b'));
		assertEquals(2, string.indexOf('c'));
		assertEquals(3, string.indexOf('d'));
		assertEquals(4, string.indexOf('e'));
		assertEquals(5, string.indexOf('f'));
		assertEquals(6, string.indexOf('g'));
	}

	@Ignore
	@Test
	public void testHashCode() {
		CString string1 = CString.fromString("abcdef");
		CString string2 = CString.fromString("abcdef");

		assertEquals(string1.hashCode(), string2.hashCode());

		string2 = CString.fromString("abcdefg");
		assertNotEquals(string1.hashCode(), string2.hashCode());
	}

	@Ignore
	@Test
	public void testEquals() {
		CString string1 = CString.fromString("abcdef");
		CString string2 = CString.fromString("abcdef");

		assertEquals(string1.equals(string2), true);

		char[] data = "0123456abcdef1123".toCharArray();
		string2 = new CString(data, 7, 6);
		assertEquals(string1.equals(string2), true);

		string2 = CString.fromString("xyz");
		assertEquals(string1.equals(string2), false);
	}

	@Test
	public void testSubstring() {
		CString string1 = CString.fromString("a long sentence without any sense");
		assertEquals("a l", string1.substring(0, 3).toString());
		assertEquals("long", string1.substring(2, 6).toString());
		assertEquals("ong ", string1.substring(3, 7).toString());
		assertEquals("tence", string1.substring(10, 15).toString());
		assertEquals("sense", string1.substring(28, 33).toString());
	}

	@Test
	public void testSubstringOfSubstring() {
		CString string1 = CString.fromString("a long sentence without any sense");
		CString string2 = string1.substring(7, string1.length());

		assertEquals("sentence without any sense", string2.toString());

		assertEquals("sentence", string2.substring(0, 8).toString());
		assertEquals("without", string2.substring(9, 16).toString());
		assertEquals("sense", string2.substring(21, 26).toString());
	}

	@Test
	public void testStartsWith() {
		CString string = CString.fromString("someText");
		assertTrue(string.startsWith(CString.fromString("some")));
		assertTrue(string.startsWith(CString.fromString("so")));
		assertFalse(string.startsWith(CString.fromString("ome")));

		string = CString.fromString("sample text");
		assertTrue(string.startsWith(CString.fromString("sam")));
		assertTrue(string.startsWith(CString.fromString("sample text")));
		assertFalse(string.startsWith(CString.fromString("text")));
		assertFalse(string.startsWith(CString.fromString("someTextaaaa")));
		
	}

	@Test
	public void testEndsWith() {
		CString string = CString.fromString("someText");
		assertTrue(string.endsWith(CString.fromString("Text")));
		assertTrue(string.endsWith(CString.fromString("ext")));
		assertFalse(string.endsWith(CString.fromString("some")));

		string = CString.fromString("sample text");
		assertTrue(string.endsWith(CString.fromString("text")));
		assertTrue(string.endsWith(CString.fromString("sample text")));
		assertFalse(string.endsWith(CString.fromString("tex")));
	}

	@Test
	public void testLeft() {
		CString string = CString.fromString("sample text");

		assertEquals("", string.left(0).toString());
		assertEquals("s", string.left(1).toString());
		assertEquals("sa", string.left(2).toString());
		assertEquals("sam", string.left(3).toString());
		assertEquals("samp", string.left(4).toString());
		assertEquals("sample text", string.left(string.length()).toString());

		string = string.substring(2, 9);
		assertEquals("mple te", string.toString());

		assertEquals("m", string.left(1).toString());
		assertEquals("mp", string.left(2).toString());
		assertEquals("mpl", string.left(3).toString());
	}

	@Test
	public void testRight() {
		CString string = CString.fromString("sample text");

		assertEquals("", string.right(0).toString());
		assertEquals("t", string.right(1).toString());
		assertEquals("xt", string.right(2).toString());
		assertEquals("ext", string.right(3).toString());
		assertEquals("text", string.right(4).toString());
		assertEquals("sample text", string.right(string.length()).toString());

		string = string.substring(2, 9);
		assertEquals("mple te", string.toString());

		assertEquals("e", string.right(1).toString());
		assertEquals("te", string.right(2).toString());
		assertEquals(" te", string.right(3).toString());
	}

	@Test
	public void testContains() {
		CString string = CString.fromString("123456789");
		assertTrue(string.contains(CString.fromString("12345")));
		assertTrue(string.contains(CString.fromString("123456789")));
		assertTrue(string.contains(CString.fromString("567")));
		assertTrue(string.contains(CString.fromString("789")));
		assertTrue(string.contains(CString.fromString("9")));
		assertTrue(string.contains(CString.fromString("5")));
		assertTrue(string.contains(CString.fromString("")));

		assertFalse(string.contains(CString.fromString("42")));
		assertFalse(string.contains(CString.fromString("46")));
		assertFalse(string.contains(CString.fromString("482")));
		assertFalse(string.contains(CString.fromString("235")));
		assertFalse(string.contains(CString.fromString("421")));
		assertFalse(string.contains(CString.fromString("135")));
		assertFalse(string.contains(CString.fromString("12588")));
		assertFalse(string.contains(CString.fromString("98526")));
	}

	@Test
	public void testLargeStringContains() {
		CString string = CString
				.fromString("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
						+ "Donec sollicitudin in diam a condimentum. Sed mollis "
						+ "sapien eu libero lobortis gravida. Phasellus maximus lectus "
						+ "quis commodo efficitur. Praesent blandit et lorem eu suscipit. "
						+ "Etiam eget eros eget dolor facilisis blandit vel et mauris."
						+ " Aliquam dignissim est magna, in accumsan enim finibus interdum. "
						+ "Donec semper vehicula dictum. In ipsum tellus, cursus eget enim ut,"
						+ " ultricies vulputate enim. Aliquam vel pretium purus.");

		assertTrue(string.contains(CString.fromString("lobortis gravida")));
		assertTrue(string.contains(CString.fromString("facilisis")));
		assertTrue(string.contains(CString.fromString("Etiam")));
		assertTrue(string.contains(CString.fromString("in accumsan enim finibus")));
		assertTrue(string.contains(CString.fromString("semper vehicula dictum")));
		assertTrue(string.contains(CString.fromString("eu libero lobortis gravida")));
		assertTrue(string.contains(CString.fromString("eros")));
		assertTrue(string.contains(CString.fromString("eget eros eget dolor facilisis blandit vel")));
		assertTrue(string.contains(CString.fromString("lobortis gravida. Phasellus maximus lectus")));
		assertTrue(string.contains(CString.fromString("quis commodo efficitur. Praesent blandit et lorem")));
		assertTrue(string.contains(CString.fromString("dignissim est magna, in accumsan enim")));
		assertTrue(string.contains(CString.fromString("dignissim est magna, in accumsan enim finibus")));
		assertTrue(string.contains(CString.fromString("sapien eu libero lobortis gravida. Phasellus")));
		assertTrue(string.contains(CString.fromString("efficitur. Praesent blandit")));
		
		assertTrue(string.substring(20, string.length() - 25).
			contains(string.substring(80, string.length() - 50)));
	}

	@Test
	public void testContainsLarge() {
		CString string = CString.fromString(
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");

		assertTrue(string.contains(CString.fromString("aaaaaab")));
		assertTrue(string.contains(CString.fromString("aaaaaaaaab")));
		assertTrue(string.contains(CString.fromString("aaaaaaaaaaaaab")));
		assertTrue(string.contains(CString.fromString("aaaaaaaaaaaaaaaaaaab")));
		assertTrue(string.contains(CString.fromString("aaaaaaaaaaaaaaaaaaaaaaaaaab")));
		assertTrue(string.contains(string.right(7)));
		assertTrue(string.contains(string.right(string.length() / 2)));
	}

	@Test
	public void testAdd(){
		CString string1 = CString.fromString("some rand");
		
		assertEquals("some random", string1.add(CString.fromString("om")).toString());
		assertEquals("some random text", string1.add(CString.fromString("om"))
			.add(CString.fromString(" text")).toString());
		assertEquals("random text", string1.substring(5, 9).add(CString.fromString("om text")).toString());
		//assertEquals("some rand", string1.add(null).toString());
		assertEquals("some rand", string1.add(CString.fromString("")).toString());
	}
	
	@Test
	public void testReplaceAllEmpty(){
		CString string1 = CString.fromString("some random text");
		
		CString old = CString.fromString("");
		CString newS = CString.fromString(" ");
		assertEquals(" s o m e   r a n d o m   t e x t ", string1.replaceAll(old, newS).toString());
	}
	
	@Test
	public void testReplaceAll(){
		CString string1 = CString.fromString("this code is slow");
		
		CString old = CString.fromString("slow");
		CString newS = CString.fromString("the fastest one!");
		assertEquals("this code is the fastest one!", string1.replaceAll(old, newS).toString());
	}
	
	@Test
	public void testReplaceAllWithRepetitions(){
		CString string1 = CString.fromString("this sentance has this many this words this.");
		
		CString old = CString.fromString("this");
		CString newS = CString.fromString("****");
		assertEquals("**** sentance has **** many **** words ****.", string1.replaceAll(old, newS).toString());
	}
	
	@Test
	public void testReplaceAll2(){
		CString string1 = CString.fromString("samlpe");
		
		CString old = CString.fromString("lp");
		CString newS = CString.fromString("pl");
		assertEquals("sample", string1.replaceAll(old, newS).toString());
	}
	
	@Test
	public void testReplaceAllRepetitions(){
		CString string1 = CString.fromString("abababc");
		
		CString old = CString.fromString("ab");
		CString newS = CString.fromString("ababa");
		assertEquals("ababaababaababac", string1.replaceAll(old, newS).toString());
	}
}
