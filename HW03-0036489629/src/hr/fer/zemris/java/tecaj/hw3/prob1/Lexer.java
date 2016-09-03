package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * The Lexer. Parses the input text and calculates the tokens.
 * 
 * @author Juraj Juričić
 *
 */
public class Lexer {
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
	 * true if the next character will be escaped
	 */
	private boolean escaping = false;
	
	/**
	 * Current state of the Lexer
	 */
	private LexerState state = LexerState.BASIC;

	/**
	 * Instantiates a new Lexer object.
	 * 
	 * @param text
	 *            input text that iz tokenized.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input cannot be null.");
		}

		this.data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates and returns the next token. Throws {@link LexerException} in
	 * case of an error.
	 * 
	 * @return the next token.
	 */
	public Token nextToken() {
		if (currentIndex == data.length) {
			if (escaping) {
				throw new LexerException("Expected an escape sequence.");
			}
			token = new Token(TokenType.EOF, null);
			currentIndex++;
		} else {
			checkIndex();

			char next = data[currentIndex];
			if (Character.isWhitespace(next)) {
				currentIndex++;
				return nextToken();
			}else if (Character.isLetter(next) || escaping || (state == LexerState.EXTENDED && next != '#')) {
				token = new Token(TokenType.WORD, letterLexer(""));
			}else if (Character.isDigit(next)) {
				Long number = 0L;
				try{
					number = Long.parseLong(digitLexer(""));
				}catch(NumberFormatException e){
					throw new LexerException("Too big number");
				}
				token = new Token(TokenType.NUMBER, number);
			}else if (next == '\\') {
				// escape sequence
				escaping = true;
				currentIndex++;
				nextToken();
			}else{
				token = new Token(TokenType.SYMBOL, next);
				currentIndex++;
			}
		}

		return token;
	}

	/**
	 * Gets and returns the previously generated token. Can be called multiple
	 * times. Does not generate the next token.
	 * 
	 * @return the previously generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the Lexer state.
	 * @param state The new state.
	 */
	public void setState(LexerState state){
		if (state == null){
			throw new IllegalArgumentException("State cannot be null.");
		}
		
		this.state = state;
	}

	/**
	 * Parses the word. Recursive.
	 * 
	 * @param word
	 *            The current word
	 * @return The new word
	 */
	private String letterLexer(String word) {
		checkIndex(false);

		if (checkEOF()){
			return word;
		}
		char next = data[currentIndex];
		if (Character.isLetter(next) || escaping || state == LexerState.EXTENDED) {
			if (escaping && !Character.isDigit(next) && next != '\\') {
				throw new LexerException("Invalid escape sequence: " + next);
			}
			if (state == LexerState.EXTENDED){
				if (Character.isWhitespace(next)){
					return word;
				}
				if (next == '#'){
					//currentIndex--;
					return word;
				}
			}
			escaping = false;

			word += next;
			currentIndex++;
			word = letterLexer(word);
		} else if (next == '\\') {
			// escape sequence
			escaping = true;
			currentIndex++;
			word = letterLexer(word);
		}

		return word;
	}

	/**
	 * Parses the word consisting of numbers
	 * @param word The current number as a word, for comparison
	 * @return The new number
	 */
	private String digitLexer(String word){
		checkIndex(false);
		
		if (checkEOF()){
			return word;
		}
		
		char next = data[currentIndex];
		if (Character.isDigit(next)){
			//number = number * 10 + (next-48);
			word += next;
			currentIndex++;
			//number = digitLexer(number, word);
			word = digitLexer(word);
		}
		
		return word;
	}
	
	/**
	 * Checks if the end of input has been reached.
	 * @return true if EOF is reached, false otherwise.
	 */
	private boolean checkEOF(){
		return currentIndex == data.length;
	}
	
	/**
	 * Checks if the index is valid. If it is not, the input must be wrong. Inclusive version.
	 */
	private void checkIndex(){
		checkIndex(true);
	}
	
	/**
	 * Checks if the index is valid. If it is not, the input must be wrong.
	 * 
	 * @param inclusive Should the check include the length or not?
	 */
	private void checkIndex(boolean inclusive) {
		if (inclusive && currentIndex >= data.length || !inclusive && currentIndex > data.length) {
			throw new LexerException("Invalid input.");
		}
	}
}