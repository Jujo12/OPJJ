package hr.fer.zemris.java.hw15;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * The class that provides sha1 hashing functionality.
 *
 * @author Juraj Juričić
 */
public class SHACrypto {

	/**
	 * Hashes the input String using SHA-1 algorithm.
	 *
	 * @param password the password
	 * @return the string
	 */
	public static String sha1(String password) {
		MessageDigest messageDigest;

		try {
			String sha1 = null;
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.reset();
			messageDigest.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(messageDigest.digest());
			return sha1;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ignorable) { return null; }
	}

	/**
	 * Converts an array of bytes to hex String.
	 *
	 * @param bytes the byte array
	 * @return the string
	 */
	private static String byteToHex(final byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();

		return result;
	}
}