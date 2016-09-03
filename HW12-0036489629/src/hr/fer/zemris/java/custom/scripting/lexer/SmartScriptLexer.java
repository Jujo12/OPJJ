package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.function.Predicate;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The Class SmartScriptLexer. The lexer for the SmartScript. Reads input
 * characters character-by-character and outputs tokens. Tokens are represented
 * as @see {@link Token}, and can be of different @see {@link TokenType}. <br>
 * Lexer is not aware of semantic nor all syntax validity.
 */
public class SmartScriptLexer {
	/**
	 * input text
	 */
	private char[] data;

	/**
	 * current token
	 */
	private Token token;

	/**
	 * index of first unparsed character
	 */
	private int currentIndex;

	/**
	 * current state of the Lexer
	 */
	private LexerState state = LexerState.BASIC;

	/**
	 * true if the next character will be escaped
	 */
	private boolean escaping = false;

	/**
	 * true if the latest parsed number was a double, false if integer
	 */
	private boolean numberFlag = false;

	/**
	 * Instantiates a new SmartScript lexer with the given input text to load.
	 * Does not start the lexing, just stores the input.
	 *
	 * @param text
	 *            the input code to process. Should not be null.
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input cannot be null.");
		}

		this.data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates the next token. Main lexing method. The {@link Token} may be of
	 * any {@link TokenType}. When the lexer reaches the end of the input data,
	 * a single {@link TokenType#EOF} Token is generated.
	 *
	 * @return the generated token.
	 */
	public Token nextToken() {
		token = null;
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
		} else {
			checkIndex();
			char next = data[currentIndex];

			if (state == LexerState.BASIC) {
				if (data[currentIndex] != '{') {
					token = new Token(TokenType.TEXT, textLexer(""));
					currentIndex--;
				} else {
					// begin of tag
					token = new Token(TokenType.SYMBOL, "{");
				}
			} else if (state == LexerState.IN_TAG) {
				// ignore whitespace until we reach a tag
				if (next == '$') {
					token = new Token(TokenType.SYMBOL, "$");
				} else if (Character.isWhitespace(next)) {
					currentIndex++;
					return nextToken();
				} else {
					throw new SmartScriptParserException("Unexpected input.");
				}
			} else if (state == LexerState.LEXING_TAG) {

				if (next == '@') {
					token = new Token(TokenType.SYMBOL, "@");
				} else if (next == '"') {
					// parse string
					currentIndex++;
					String string = stringLexer("");
					token = new Token(TokenType.STRING, string);
				} else if (Character.isLetter(next) || next == '=') {
					String variable = Character.toString(next);
					if (next != '=') {
						currentIndex++;

						variable = parseUntilWhitespace(Character.toString(next), new Predicate<Character>() {
							public boolean test(Character x) {
								return Character.isLetterOrDigit(x) || x == '_';
							}
						});
					}

					TagType tag = null;
					if ((tag = TagType.getTag(variable)) != null) {
						token = new Token(TokenType.TAG, tag);
					} else {
						token = new Token(TokenType.VARIABLE, variable);
					}

				} else if (next == '-') {
					// check for number
					if (currentIndex < data.length) {
						if (Character.isDigit(data[currentIndex + 1])) {
							currentIndex++;
							String numberWord = numberLexer("-");
							token = parseNumber(numberWord, numberFlag);
							numberFlag = false;
						} else {
							token = new Token(TokenType.SYMBOL, '-');
						}
					}
				} else if (Character.isDigit(next)) {
					String numberWord = numberLexer("");
					token = parseNumber(numberWord, numberFlag);
					numberFlag = false;
				} else if (Character.isWhitespace(next)) {
					currentIndex++;
					return nextToken();
				} else {
					token = new Token(TokenType.SYMBOL, next);
				}
			}
		}
		if (token == null) {
			throw new SmartScriptParserException("Unknown error occurred.");
		}

		currentIndex++;
		return token;
	}

	/**
	 * Parses the given number and generates the corresponsive {@link Token}.
	 *
	 * @param numberWord
	 *            the String that should be parsed.
	 * @param parseAsDouble
	 *            true if the number should be parsed as a double.
	 * @return the token
	 */
	private Token parseNumber(String numberWord, boolean parseAsDouble) {
		if (parseAsDouble) {
			// double
			try {
				double number = Double.parseDouble(numberWord);
				token = new Token(TokenType.CONSTANTDOUBLE, number);
			} catch (NumberFormatException e) {
				throw new SmartScriptParserException("Invalid number format.");
			}
		} else {
			try {
				int number = Integer.parseInt(numberWord);
				token = new Token(TokenType.CONSTANTINTEGER, number);
			} catch (NumberFormatException e) {
				throw new SmartScriptParserException("Invalid number format.");
			}
		}

		return token;
	}

	/**
	 * Parses the input until it reaches a whitespace. Accepts a
	 * {@link Predicate} for the {@link Character} input as the second argument
	 * and tests each character against it.
	 *
	 * @param current
	 *            the current string. Initial call should set it as an empty
	 *            String ("").
	 * @param test
	 *            the Predicate to test the characters against.
	 * @return the parsed String.
	 */
	private String parseUntilWhitespace(String current, Predicate<Character> test) {
		checkIndex(false);
		if (data.length == currentIndex) {
			return current;
		}

		char next = data[currentIndex];
		if (Character.isWhitespace(next)) {
			return current;
		} else {
			if (!test.test(next)) {
				currentIndex--;
				return current;
			}

			current += next;
		}
		currentIndex++;
		return parseUntilWhitespace(current, test);
	}

	/**
	 * The String lexer. Keeps on going until it reaches an unescaped quotation
	 * mark (
	 * "). Recursive. Initial call should be with word as an empty String ("").
	 *
	 * @param word
	 *            the current parsed word. Initial call should set it as an
	 *            empty String ("").
	 * @return the lexed String.
	 */
	private String stringLexer(String word) {
		if (data.length == currentIndex) {
			return word;
		}

		char next = data[currentIndex];

		if (!escaping && next == '\\') {
			escaping = true;

			currentIndex++;
			word = stringLexer(word);
		} else if (!escaping && next == '"') {
			return word;
		} else {
			if (escaping){
				//new line escape
				if (next == 'r'){
					next = '\r';
				}else if (next == 'n'){
						next = '\n';
				}
			}
			
			escaping = false;
			

			word += next;
			currentIndex++;
			word = stringLexer(word);
		}

		return word;
	}

	/**
	 * The number lexer. Keeps on going while getting a number or a decimal dot
	 * (.). Recursive. Initial call may be with word as an empty String ("").
	 *
	 * @param word
	 *            the current parsed word. Initial call should set it as an
	 *            empty String ("") or minus in case of a negative number ("-").
	 * @return the lexed String representing a number.
	 */
	private String numberLexer(String word) {
		if (data.length == currentIndex) {
			return word;
		}
		char next = data[currentIndex];

		if (Character.isDigit(next)) {
			word += next;
			currentIndex++;
			word = numberLexer(word);
		} else if (next == '.') {
			numberFlag = true;
		} else {
			currentIndex--;
		}

		return word;
	}

	/**
	 * The text lexer. Keeps on going until it reaches an unescaped tag (which
	 * starts with {). Recursive. Initial call should be with word as an empty
	 * String ("").
	 *
	 * @param word
	 *            the current parsed word. Initial call should set it as an
	 *            empty String ("").
	 * @return the lexed text.
	 */
	private String textLexer(String word) {
		if (data.length == currentIndex) {
			return word;
		}
		char next = data[currentIndex];

		if (next == '{' && !escaping) {
			return word;
		} else if (next == '\\' && !escaping) {
			// escape next character
			escaping = true;
		} else {
			escaping = false;

			word += next;
			currentIndex++;
			word = textLexer(word);
		}

		return word;
	}

	/**
	 * Inclusive alias for checkIndex.
	 */
	private void checkIndex() {
		checkIndex(true);
	}

	/**
	 * Checks if the index is valid.
	 *
	 * @param inclusive
	 *            true if the check should be inclusive.
	 */
	private void checkIndex(boolean inclusive) {
		if (inclusive && currentIndex >= data.length || !inclusive && currentIndex > data.length) {
			throw new SmartScriptParserException("Invalid input.");
		}
	}

	/**
	 * Gets the last generated token. Can be called multiple times. Does not
	 * generate the new token.
	 *
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the lexerstate.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State cannot be null.");
		}

		this.state = state;
	}

	/**
	 * Gets the lexer state.
	 *
	 * @return the state
	 */
	public LexerState getState() {
		return state;
	}
}
