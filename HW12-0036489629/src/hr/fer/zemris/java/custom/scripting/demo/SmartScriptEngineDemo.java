package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The demonstration program for {@link SmartScriptEngine}.
 *
 * @author Juraj Juričić
 */
public class SmartScriptEngineDemo {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		// comment out lines not used; max. one program should be run.
		demo1();
		demo2();
		demo3();
		demo4();
	}

	/**
	 * First demo program: osnovni.smscr.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void demo1() throws IOException {
		String documentBody = readFromFile("osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}

	/**
	 * First demo program: zbrajanje.smscr.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void demo2() throws IOException {
		String documentBody = readFromFile("zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}

	/**
	 * First demo program: brojPoziva.smscr.
	 */
	private static void demo3() {
		String documentBody = readFromFile("brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters,
				persistentParameters, cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc)
						.execute();
		System.out.println("Vrijednost u mapi: "
				+ rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Fourth demo program: fibonacci.smscr.
	 */
	private static void demo4() {
		String documentBody = readFromFile("fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}

	/**
	 * Reads content from file and returns as string. Uses UTF-8 charset.
	 *
	 * @param filepath
	 *            the path to input file
	 * @return the output string
	 */
	private static String readFromFile(String filepath) {
		try {
			String docBody = new String(Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8);

			return docBody;
		} catch (IOException e) {
			System.err.println("Could not read input file.");
		}

		return null;
	}
}
