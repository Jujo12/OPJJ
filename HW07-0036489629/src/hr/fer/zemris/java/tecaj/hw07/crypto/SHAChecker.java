package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PErforms SHA-256 digesting and checking.
 *
 * @author Juraj Juričić
 */
public class SHAChecker {

	/** The file name. */
	private String fileName;

	/** The sha digest. */
	private MessageDigest sha;

	/** The digest. */
	private byte[] digest;

	/** Is the digest available?. */
	private boolean digestAvailable = false;

	/**
	 * Instantiates a new SHA checker.
	 *
	 * @param fileName
	 *            the file name
	 * @throws NoSuchAlgorithmException
	 *             thrown if the used algorithm is not available.
	 */
	public SHAChecker(String fileName) throws NoSuchAlgorithmException {
		this.fileName = fileName;
		this.sha = MessageDigest.getInstance("SHA-256");
	}

	/**
	 * Calculates the digest.
	 */
	public void calcDigest() {
		Path p = Paths.get(fileName);
		try (InputStream inputStream = Files.newInputStream(p,
				StandardOpenOption.READ)) {
			byte[] buffer = new byte[1024];

			int byteCount;
			do {
				byteCount = inputStream.read(buffer);

				if (byteCount >= 1) {
					sha.update(buffer, 0, byteCount);
				}
			} while (byteCount >= 1);

			this.digest = sha.digest();
			this.digestAvailable = true;
		} catch (IOException e) {
			System.err.println("An IO error occured.");
		}
	}

	/**
	 * Gets the calculated digest.
	 *
	 * @return the digest
	 */
	public byte[] getDigest() {
		if (digestAvailable) {
			return this.digest;
		} else {
			throw new IllegalStateException("Digest not yet calculated.");
		}
	}

	/**
	 * Gets the digest as string.
	 *
	 * @return the digest as string
	 */
	public String getDigestAsString() {
		byte[] data = getDigest();

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			buffer.append(Integer.toString((data[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return buffer.toString();
	}

}
