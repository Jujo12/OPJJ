package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The Class SmartScriptParserException. Exception is thrown for each syntax, semantic or any other error occured while parsing the input data.
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6078437273509537532L;

	/**
	 * Instantiates a new smart script parser exception.
	 *
	 * @param message the message
	 */
	public SmartScriptParserException(String message){
		super(message);
	}
}
