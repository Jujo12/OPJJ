package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The Class FileEncryption. Used for encryption and decryption of files using
 * AES algorithm.
 *
 * @author Juraj Juričić
 */
public class FileEncryption {

	/** The key size, in bytes. */
	public static final int KEY_SIZE = 16; // bytes

	/** The vestor size, in bytes. */
	public static final int VECTOR_SIZE = 16; // bytes

	/** The cipher. */
	private Cipher cipher;

	/** The input path. */
	private Path inputPath;

	/** The output path. */
	private Path outputPath;

	/**
	 * Instantiates a new file encryption.
	 *
	 * @param keyText
	 *            the key text (password)
	 * @param ivText
	 *            the initialization vector string
	 * @param inputFileName
	 *            the input file name
	 * @param outputFileName
	 *            the output file name
	 * @param encrypt
	 *            true if encrypting, false if decrypting
	 * @throws GeneralSecurityException
	 *             the general security exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public FileEncryption(String keyText, String ivText, String inputFileName,
			String outputFileName, boolean encrypt)
			throws GeneralSecurityException, IOException {
		SecretKeySpec keySpec = new SecretKeySpec(Crypto.hexToByte(keyText),
				"AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				Crypto.hexToByte(ivText));

		inputPath = Paths.get(inputFileName);
		if (!inputPath.toFile().isFile()) {
			throw new IOException("Input path does not point to a file.");
		}

		outputPath = Paths.get(outputFileName);
		if (!outputPath.toFile().exists()) {
			Files.createFile(outputPath);
		}

		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
				keySpec, paramSpec);
	}

	/**
	 * Processes the data.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 */
	public void process() throws IOException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		try (InputStream inputStream = Files.newInputStream(inputPath,
				StandardOpenOption.READ);
				OutputStream outputStream = Files.newOutputStream(outputPath,
						StandardOpenOption.WRITE)) {

			byte[] inputBuffer = new byte[4096];
			while (true) {
				int byteCount = inputStream.read(inputBuffer);
				if (byteCount < 1) {
					break;
				}

				byte[] outputBuffer = new byte[4096];
				int outputByteCount = cipher.update(inputBuffer, 0, byteCount,
						outputBuffer);
				outputStream.write(outputBuffer, 0, outputByteCount);
			}

			byte[] outputBuffer = new byte[4096];
			int outputByteCount = cipher.doFinal(outputBuffer, 0);
			outputStream.write(outputBuffer, 0, outputByteCount);
		}
	}
}
