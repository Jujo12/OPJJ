package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Represents a lexic unit token.
 * 
 * @author Juraj Juričić
 *
 */
public class Token {

	/**
	 * The token type.
	 */
	private TokenType type;

	/**
	 * The token value.
	 */
	private Object value;

	/**
	 * Instantiates a new Token with the given type and value.
	 * 
	 * @param type
	 *            The type of the new token.
	 * @param value
	 *            The value of then new token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the token value.
	 * 
	 * @return The token value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the token type.
	 * 
	 * @return The token type.
	 */
	public TokenType getType() {
		return type;
	}
}