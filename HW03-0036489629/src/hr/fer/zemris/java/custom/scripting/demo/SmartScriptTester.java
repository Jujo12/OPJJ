package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The demo program for testing SmartScript parser classes.
 *
 */
public class SmartScriptTester {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		SmartScriptParser parser = null;
		String docBody = readFromFile("doc1.txt");
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.err.println("Unable to parse document: " + e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

	}

	/**
	 * Reads string from file.
	 *
	 * @param filepath the path to input file
	 * @return the output string
	 */
	private static String readFromFile(String filepath) {
		try {
			String docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

			return docBody;
		} catch (IOException e) {
			System.err.println("Could not read input file.");
		}

		return null;
	}

	/**
	 * Reconstructs the original document body. Recursive.
	 *
	 * @param parent the parent node
	 * @return the reconstructed document body
	 */
	private static String createOriginalDocumentBody(Node parent) {
		StringBuilder documentText = new StringBuilder();

		documentText.append(parent.asString());

		for (int i = 0, size = parent.numberOfChildren(); i < size; i++) {
			documentText.append(createOriginalDocumentBody(parent.getChild(i)));
		}

		//closing of non-empty tags
		if (!parent.getIsEmptyTag()) {
			documentText.append("{$END$}");
		}

		return documentText.toString();
	}
}
