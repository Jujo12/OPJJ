package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The demo program that writes the document tree using visitor design pattern.
 *
 * @author Juraj Juričić
 */
public class TreeWriter {
	
	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args){
		if (args.length != 1){
			System.err.println("One argument expected: path to input file.");
			return;
		}
		
		String fileName = args[0];
		if (!fileName.endsWith(".smscr")){
			System.err.println("Only files ending with .smscr allowed.");
			return;
		}
		
		String doc = readFromFile(fileName);
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(doc);
		} catch (SmartScriptParserException e) {
			System.err.println("Unable to parse document: " + e.getMessage());
			System.exit(-1);
		}
		
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}
	
	/**
	 * Reads content from file and returns as string. Uses UTF-8 charset.
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
	 * The node visitor that reconstructs the original script document and writes it to standard output..
	 *
	 * @author Juraj Juričić
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			writeNode(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			writeNode(node);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			writeNode(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			writeNode(node);
		}
		
		/**
		 * Helper method that reconstructs the node using node's asString method.
		 *
		 * @param node the node
		 */
		private static void writeNode(Node node){
			System.out.print(node.asString());
			for(int i = 0; i < node.numberOfChildren(); i++) {
				writeNode(node.getChild(i));
			}
			
			if (!node.getIsEmptyTag()){
				System.out.print("{$END$}");
			}
		}
		
	}
}
