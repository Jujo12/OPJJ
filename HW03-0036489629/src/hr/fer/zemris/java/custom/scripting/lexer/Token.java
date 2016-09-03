package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Class Token. Represents a lexed Token. Can be of any {@link TokenType}.
 * STores the token value and type.
 */
public class Token {

	/** The token type. */
	private TokenType type;

	/** The token value. */
	private Object value;

	/**
	 * Instantiates a new token.
	 *
	 * @param type
	 *            the token type
	 * @param value
	 *            the token value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Gets the token value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
}
