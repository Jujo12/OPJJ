package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enum LexerState. Represents a Lexer State.
 */
public enum LexerState {

	/**
	 * The basic state. Used when the lexer is processing regular text, outside
	 * of a tag.
	 */
	BASIC,

	/**
	 * The in_tag state. Used when the parser is expecting $ (after a previous
	 * {).
	 */
	IN_TAG,

	/**
	 * The lexing_tag state. Used when the lexer is processing a single tag.
	 * Triggered after "{$".
	 */
	LEXING_TAG
}
