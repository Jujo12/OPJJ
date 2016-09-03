package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

/**
 * The Class Crypto. Used for encryption, decryption and hash verification of
 * files. Accepts arguments: command (shacheck, encrypt, decrypt) and file to
 * check, encrypt, or decrypt.
 *
 * @author Juraj Juričić
 */
public class Crypto {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		if (args.length == 0) {
			System.err.println(
					"Please enter a command: 'checksha', 'encrypt', 'decrypt'");
			return;
		}

		switch (args[0].toLowerCase().trim()) {
		case "checksha":
			if (args.length != 2) {
				System.err.println("File name expected as argument.");
				return;
			}
			Commands.checkSHA(args[1]);
			break;
		case "encrypt":
			if (args.length != 3) {
				System.err.println(
						"Input and output file names expected as arguments.");
				return;
			}

			try {
				Commands.encryptDecrypt(args[1], args[2], true);
			} catch (IOException e) {
				System.err.println("An IO error occured: " + e.getMessage());
			} catch (GeneralSecurityException e) {
				System.err
						.println("A security error occured: " + e.getMessage());
			}

			break;
		case "decrypt":
			if (args.length != 3) {
				System.err.println(
						"Input and output file names expected as arguments.");
				return;
			}

			try {
				Commands.encryptDecrypt(args[1], args[2], false);
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				System.err.println("An IO error occured: " + e.getMessage());
			} catch (GeneralSecurityException e) {
				System.err
						.println("A security error occured: " + e.getMessage());
			} catch (Exception e) {
				System.err
						.println("An unknown error occured! " + e.getMessage());
			}

			break;
		default:
			System.err.println("Unknown command: " + args[0] + ".");
			return;
		}
	}

	/**
	 * Converts hex string to byte array.
	 *
	 * @param s
	 *            the hex string
	 * @return the byte[]
	 */
	public static byte[] hexToByte(String s) {
		s = s.toLowerCase();
		if (!s.matches("[0-9a-f]+")) {
			throw new IllegalArgumentException("Invalid hex string provided.");
		}

		int len = s.length();
		if (len % 2 == 1) {
			s = "0" + s;
		}
		byte[] data = new byte[(len + 1) / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
