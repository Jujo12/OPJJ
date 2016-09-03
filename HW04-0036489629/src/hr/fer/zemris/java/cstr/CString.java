package hr.fer.zemris.java.cstr;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * The custom implementation of the String class.
 */
public class CString {
	/**
	 * The character array.
	 */
	private char[] data;

	/**
	 * The array offset.
	 */
	private int offset;

	/**
	 * The length of String
	 */
	private int length;

	/**
	 * The main constructor. Instantiates a new CString with the given char
	 * array, offset and length. If given offset is negative, the constructed
	 * CString will be constructed from the back of the array.
	 *
	 * @param data
	 *            the char array
	 * @param offset
	 *            the target CString offset
	 * @param length
	 *            the target CString length
	 */
	public CString(char[] data, int offset, int length) {
		if (data == null) {
			throw new IllegalArgumentException("Char array cannot be null.");
		}
		if (length < 0) {
			throw new IllegalArgumentException("Length cannot be negative");
		}
		/*
		 * if ((length - offset) > data.length) { throw new
		 * CStringIndexOutOfBoundsException(
		 * "Value of given (length-offset) cannot be greater than given char array size."
		 * ); }
		 */
		if ((length + offset) > data.length) {
			throw new CStringIndexOutOfBoundsException(
					"Value of given (length+offset) cannot be greater than given char array size.");
		}
		// negative offset support
		if (offset < 0) {
			offset = data.length + offset;
		}

		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Instantiates a new CString from the given char array.
	 *
	 * @param data
	 *            the data
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}

	/**
	 * Instantiates a new CString from another CString.
	 *
	 * @param original
	 *            the original
	 */
	public CString(CString original) {
		nullTest(original);
		char[] newData;
		/*
		 * if originals internal character array is larger than needed, your new
		 * instance must allocate its own character array of minimal required
		 * size and copy data; otherwise it must reuse original's character
		 * array
		 */
		if (original.data.length > original.length || original.offset != 0) {
			// allocate new character array
			newData = new char[original.length];
			System.arraycopy(original.data, original.offset, newData, 0, original.length);
		} else {
			newData = original.data;
		}

		// cannote delegate constructor because of previous calls; needed tests
		this.data = newData;
		this.offset = 0;
		this.length = newData.length;
	}

	/**
	 * Instantiates a new CString from the given String.
	 * 
	 * @param string
	 *            the string to import into CString.
	 */
	public CString(String string) {
		this(string.toCharArray());
	}

	/**
	 * Factory method that constructs a CString from the given String.
	 *
	 * @param s
	 *            the give string
	 * @return the constructed CString.
	 */
	public static CString fromString(String s) {
		return new CString(s);
	}

	/**
	 * Returns the CString length.
	 *
	 * @return the length
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns the character at the given index.
	 *
	 * @param index
	 *            the index
	 * @return the character
	 */
	public char charAt(int index) {
		if (index >= length) {
			throw new CStringIndexOutOfBoundsException("" + index);
		}
		return data[index + offset];
	}

	/**
	 * Returns the char[] array containing only the characters in the actual
	 * CString. Does not return a link to the internat array; constructs a new
	 * array instead.
	 *
	 * @return the char[] array.
	 */
	public char[] toCharArray() {
		char[] array = new char[length];
		for (int i = 0; i < length; i++) {
			array[i] = charAt(i);
		}

		return array;
	}

	@Override
	public String toString() {
		return new String(data, offset, length);
	}

	/**
	 * returns index of first occurrence of char or -1 if the char is not found
	 * in the string.
	 *
	 * @param c
	 *            the character to search for.
	 * @return the index of first ocurrence or -1.
	 */
	public int indexOf(char c) {
		for (int i = 0; i < length; i++) {
			if (c == charAt(i)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Checks the left part of the string. Returns true if this string begins
	 * with given string, false otherwise
	 *
	 * @param s
	 *            the string to search for
	 * @return true if this string begins with given string, false otherwise
	 */
	public boolean startsWith(CString s) {
		nullTest(s, "Test CString");

		for (int i = 0; i < s.length; i++) {
			if (s.charAt(i) != this.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the right part of the string. Returns true if this string ends
	 * with given string, false otherwise
	 *
	 * @param s
	 *            the string to search for
	 * @return true if this string ends with given string, false otherwise
	 */
	public boolean endsWith(CString s) {
		nullTest(s, "Test CString");

		for (int i = s.length - 1, j = this.length - 1; i > 0; i--, j--) {
			if (s.charAt(i) != this.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Searches the string for the occurence of the given substring. Returns
	 * true if this string contains given string at any position, false
	 * otherwise.
	 *
	 * @param s
	 *            the string to search for
	 * @return true if substring is found, false otherwise
	 */
	public boolean contains(CString s) {
		nullTest(s, "Test CString");

		int testPos = 0;
		for (int i = 0; i < this.length; i++) {
			if (testPos == s.length) {
				return true;
			}
			if (this.charAt(i) == s.charAt(testPos)) {
				testPos++;
			} else {
				i -= testPos;
				testPos = 0;
			}
		}
		if (testPos == s.length) {
			return true;
		}

		return false;
	}

	/**
	 * Returns new CString which represents a part of original string. Position
	 * endIndex does not belong to the substring.<br>
	 * Arguments: startIndex>=0, endIndex>=startIndex.<br>
	 * <br>
	 * Complexity: O(1)
	 *
	 * @param startIndex
	 *            the start index
	 * @param endIndex
	 *            the end index (not included in the result substring)
	 * @return the substring
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new IllegalArgumentException("Start index must be a positive integer.");
		}
		if (endIndex < startIndex) {
			throw new IllegalArgumentException(
					"End index (" + endIndex + ") must be greater than or equal to start index (" + startIndex + ").");
		}
		if (endIndex > length) {
			throw new CStringIndexOutOfBoundsException("End index out of CString bounds.");
		}

		return new CString(data, offset + startIndex, endIndex - startIndex);
	}

	/**
	 * Returns new CString which represents the starting part of original string
	 * and is of length n.
	 *
	 * @param n
	 *            the length of the new string
	 * @return the left substring
	 */
	public CString left(int n) {
		if (n > length || n < 0) {
			throw new CStringIndexOutOfBoundsException("Cannot be constructed.");
		}
		return substring(0, n);
	}

	/**
	 * Returns new CString which represents the ending part of original string
	 * and is of length n.
	 *
	 * @param n
	 *            the length of the new string
	 * @return the right substring
	 */
	public CString right(int n) {
		if (n > length || n < 0) {
			throw new CStringIndexOutOfBoundsException("Cannot be constructed.");
		}
		return substring(length - n, length);
	}

	/**
	 * Creates a new CString which is concatenation of current and given string
	 *
	 * @param s
	 *            the string to concatenate.
	 * @return the new CString.
	 */
	public CString add(CString s) {
		char[] array = new char[this.length + s.length];
		for (int i = 0; i < this.length; i++) {
			array[i] = this.charAt(i);
		}
		for (int i = 0; i < s.length; i++) {
			array[i + this.length] = s.charAt(i);
		}

		return new CString(array);
	}

	/**
	 * Creates a new CString in which each occurrence of old character is
	 * replaced with new character
	 *
	 * @param oldChar
	 *            the old char
	 * @param newChar
	 *            the new char
	 * @return the new CString
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char newData[] = new char[length];
		char element;

		for (int i = 0; i < this.length; i++) {
			element = this.charAt(i);
			if (element == oldChar) {
				newData[i] = newChar;
			} else {
				newData[i] = element;
			}
		}

		return new CString(newData);
	}

	/**
	 * Helper method. Constructs a new CString by adding the given character to
	 * the start, end, and between every character of the string. Same as
	 * replaceAll("", insert);
	 *
	 * @param insert
	 *            the character to insert
	 * @return the new CString
	 */
	public CString addBetweenEveryChar(CString insert) {
		int newLength = length + insert.length * (this.length + 1);
		char[] newArray = new char[newLength];

		for (int sourceIndex = 0, destIndex = 0; sourceIndex <= length; sourceIndex++) {
			for (int j = 0; j < insert.length; j++) {
				newArray[destIndex + j] = insert.charAt(j);
			}
			destIndex += insert.length;
			if (sourceIndex < length) {
				newArray[destIndex++] = charAt(sourceIndex);
			}
		}

		return new CString(newArray);
	}

	/**
	 * Creates a new CString in which each occurrence of old substring is
	 * replaced with the new substring
	 * 
	 * @param oldStr
	 *            the old substring
	 * @param newStr
	 *            the new substring
	 * @return the new CString
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		// special case: empty oldStr
		if (oldStr.length == 0) {
			return addBetweenEveryChar(newStr);
		}

		int[] occurences = stringOccurences(oldStr);

		// resize
		int sizeDiff = (newStr.length - oldStr.length) * occurences.length;
		char[] newArray = new char[length + sizeDiff];

		int occurenceIndex = 0;
		for (int oldArrayIndex = 0, newArrayIndex = 0; oldArrayIndex < this.length; oldArrayIndex++, newArrayIndex++) {
			if (occurences[occurenceIndex] == oldArrayIndex) {
				// replace string
				for (int replacementIndex = 0; replacementIndex < newStr.length; replacementIndex++, newArrayIndex++) {
					newArray[newArrayIndex] = newStr.charAt(replacementIndex);
				}
				oldArrayIndex += oldStr.length;
				newArrayIndex--;
				oldArrayIndex--;

				if (occurenceIndex < occurences.length - 1) {
					occurenceIndex++;
				}
			} else {
				newArray[newArrayIndex] = this.charAt(oldArrayIndex);
			}
		}

		return new CString(newArray);
	}

	/**
	 * Looks for all occurences of the given CString (needle), and returns the
	 * array containing indexes of every starting position of the given
	 * substring
	 * 
	 * @param needle
	 *            the substring to search for
	 * @return the array containing starting indexes of the searched substring
	 */
	private int[] stringOccurences(CString needle) {
		// int array of indexes
		ArrayIndexedCollection found = new ArrayIndexedCollection();

		int needleIndex = 0;
		for (int thisIndex = 0; thisIndex < this.length; thisIndex++) {
			char element = this.charAt(thisIndex);
			if (element == needle.charAt(needleIndex)) {
				needleIndex++;
			} else {
				needleIndex = 0;
			}
			if (needleIndex == needle.length) {
				needleIndex = 0;
				found.add(thisIndex + 1 - needle.length);
			}
		}

		found.toArray();
		int[] intArray = new int[found.size()];

		// to int[] array
		for (int i = 0, size = found.size(); i < size; i++) {
			intArray[i] = (int) found.get(i);
		}

		return intArray;
	}

	/**
	 * Tests if the object is null. Default message part "Argument".
	 *
	 * @param o the object to test
	 */
	private void nullTest(Object o) {
		nullTest(o, "Argument");
	}

	/**
	 * Tests if the object is null. Throws an {@link IllegalArgumentException} if null, voids otherwise.
	 *
	 * @param o the object to test
	 * @param what the message part to add to the expection message.
	 */
	private void nullTest(Object o, String what) {
		if (o == null) {
			throw new IllegalArgumentException(what + " cannot be null.");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CString other = (CString) obj;

		// return (other.toCharArray().equals(this.toCharArray()));
		for (int i = 0; i < length; i++) {
			if (charAt(i) != other.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		long result = 0L;
		for (int i = 0; i < length; i++) {
			result += charAt(i) * Math.pow(prime, length - i - 1);
		}
		return (int) result % 1000000007;
	}
}
