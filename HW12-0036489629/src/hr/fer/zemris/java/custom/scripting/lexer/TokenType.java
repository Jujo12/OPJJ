package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enum TokenType. Represents different token types.
 */
public enum TokenType {
	
	/** The end-of-file. */
	EOF,
	
	/** Represents the text. */
	TEXT,
	
	/** Represents the variable. */
	VARIABLE,
	
	/** Represents the double constant. */
	CONSTANTDOUBLE,
	
	/** Represents the integer constant. */
	CONSTANTINTEGER,
	
	/** Represents the string . */
	STRING,
	
	/** Represents the tag identifier. */
	TAG,
	
	/** Represents the symbol. Semantically, could be an operator or an element of the tag (like {, }, or $). */
	SYMBOL
}
