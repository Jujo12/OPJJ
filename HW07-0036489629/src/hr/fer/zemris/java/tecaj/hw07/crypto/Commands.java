package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;

/**
 * Support for commands.
 *
 * @author Juraj Juričić
 */
public class Commands {

	/**
	 * The checksha command. Calculates the sha-256 hash of the given file and
	 * checks against a provided hash.
	 *
	 * @param fileName
	 *            the file name
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public static void checkSHA(String fileName)
			throws NoSuchAlgorithmException {
		SHAChecker checker = new SHAChecker(fileName);
		try(Scanner sc = new Scanner(System.in)){

			System.out.println(
					"Please provide expected sha-256 digest for " + fileName + ":");
			String expectedDigest = sc.nextLine().trim();
	
			checker.calcDigest();
			String calculatedDigest = checker.getDigestAsString();
	
			System.out.println(message(expectedDigest, calculatedDigest, fileName));
		}
	}

	/**
	 * Constructs a message for user.
	 *
	 * @param expectedDigest
	 *            the expected digest
	 * @param calculatedDigest
	 *            the calculated digest
	 * @param fileName
	 *            the file name
	 * @return the constructed message
	 */
	private static String message(String expectedDigest,
			String calculatedDigest, String fileName) {
		StringBuilder sb = new StringBuilder("Digesting completed. Digest of ");
		sb.append(fileName);

		if (expectedDigest.toLowerCase()
				.equals(calculatedDigest.toLowerCase())) {
			sb.append(" matches expected digest.");
		} else {
			sb.append(" does not match the expected digest. Digest was: ");
			sb.append(calculatedDigest);
		}

		return sb.toString();
	}

	/**
	 * Encrypt / decrypt method using AES encryption.
	 *
	 * @param inputFileName
	 *            the input file name
	 * @param outputFileName
	 *            the output file name
	 * @param encrypt
	 *            true if used for encryption, false for decrpytion.
	 * @throws GeneralSecurityException
	 *             the general security exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void encryptDecrypt(String inputFileName,
			String outputFileName, boolean encrypt)
			throws GeneralSecurityException, IOException {
		String keyText = "";
		String ivText = "";
		try(Scanner sc = new Scanner(System.in)){

			System.out.println(
					"Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			do {
				System.out.print("> ");
				keyText = sc.nextLine().trim();
				if (keyText.length() != FileEncryption.KEY_SIZE * 2) {
					System.out
							.println("Illegal password size, please enter again:");
					continue;
				}
				break;
			} while (true);
	
			System.out.println(
					"Please provide initialization vector as hex-encoded text (32 hex-digits):");
			do {
				System.out.print("> ");
				ivText = sc.nextLine().trim();
				if (ivText.length() != FileEncryption.KEY_SIZE * 2) {
					System.out.println(
							"Illegal initialization vector size, please enter again:");
					continue;
				}
				break;
			} while (true);
	
			FileEncryption fileEnc = new FileEncryption(keyText, ivText,
					inputFileName, outputFileName, encrypt);
	
			try {
				fileEnc.process();
			} catch (BadPaddingException e) {
				System.err.println(
						"A padding error occured. Probably wrong password or initialization vector?");
				return;
			}
			
			System.out.print(encrypt ? "Encryption" : "Decryption");
			System.out.println(" completed. Generated file " + outputFileName
					+ " based on file " + inputFileName + ".");
		}
	}
}
