package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.TagType;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * The Class SmartScriptParser. Used for parsing SmartScript. Supports FOR and
 * ECHO tags, along with simple text.
 */
public class SmartScriptParser {

	/** The lexer. */
	private SmartScriptLexer lexer;

	/** The main document node. */
	private DocumentNode documentNode;

	/** The Node stack. */
	private ObjectStack stack;

	/** The parsing tag. */
	private TagType parsingTag;

	/** The current parsing tag's arguments. */
	private ArrayList<Element> arguments;

	/**
	 * Instantiates a new smart script parser. Starts the lexer and parser.
	 *
	 * @param data
	 *            the data to parse.
	 */
	public SmartScriptParser(String data) {
		lexer = new SmartScriptLexer(data);

		documentNode = new DocumentNode();
		stack = new ObjectStack();

		stack.push(documentNode);

		try {
			parse();
		} catch (SmartScriptParserException smartException) {
			throw smartException;
		} catch (Exception e) {
			throw new SmartScriptParserException("An unknown error occured: " + e.getMessage());
		}

	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Parses the input data. Recursive.
	 */
	private void parse() {
		try {
			Token token = lexer.nextToken();

			if (token.getType() == TokenType.TEXT) {
				addNode(new TextNode((String) token.getValue()), false);
			} else {
				switch (lexer.getState()) {
				case BASIC:
					if (token.getType() == TokenType.SYMBOL && token.getValue().equals("{")) {
						lexer.setState(LexerState.IN_TAG);
					}
					break;
				case IN_TAG:
					if (token.getType() == TokenType.SYMBOL && token.getValue().equals("$")) {
						lexer.setState(LexerState.LEXING_TAG);
					} else {
						throw new SmartScriptParserException("Expected $ after {");
					}
					break;
				case LEXING_TAG:
					if (token.getType() == TokenType.TAG) {
						if (parsingTag != null) {
							throw new SmartScriptParserException("Unexpected TAG");
						}
						if (token.getValue().toString().toUpperCase().equals("FOR")) {
							// for loop node
							// ForLoopNode forLoopNode = new ForLoopNode(,
							// startExpression, endExpression, stepExpression);
							// currentNode.addChildNode(forLoopNode);
							parsingTag = TagType.FOR;
						} else if (token.getValue().toString().toUpperCase().equals("ECHO")) {
							parsingTag = TagType.ECHO;
						} else if (token.getValue().toString().toUpperCase().equals("END")) {
							if (stack.size() <= 1) {
								throw new SmartScriptParserException("Unexpected END-tag.");
							} else {
								stack.pop();
							}
						}
					} else if (token.getType() == TokenType.SYMBOL && token.getValue().toString().equals("$")) {
						stopTagParse();
					} else {
						if (parsingTag == null) {
							throw new SmartScriptParserException("Syntax error: invalid tag format.");
						} else if (parsingTag == TagType.FOR) {
							// check variable order
							parseFor(0);
							Element[] arguments = Arrays.copyOf(this.arguments.toArray(), 4, Element[].class);
							ForLoopNode node = new ForLoopNode((ElementVariable) arguments[0], arguments[1],
									arguments[2], arguments[3]);
							this.arguments = null;

							addNode(node, true);
						} else if (parsingTag == TagType.ECHO) {
							parseEcho(0);
							Element[] arguments = Arrays.copyOf(this.arguments.toArray(), this.arguments.size(),
									Element[].class);
							EchoNode node = new EchoNode(arguments);
							this.arguments = null;

							addNode(node, false);
						}
					}
					break;
				}
			}

			if (token.getType() != TokenType.EOF) {
				parse();
			}
		} catch (SmartScriptParserException e) {
			// end of parsing
			e.printStackTrace();
		}
	}

	/**
	 * Adds the node to the parent. Also adds the node to the stack if the node
	 * is non-empty.
	 *
	 * @param node
	 *            the node
	 * @param pushToStack
	 *            if true, the node will be added to the stack.
	 */
	private void addNode(Node node, boolean pushToStack) {
		try {
			Node lastNode = (Node) stack.peek();
			lastNode.addChildNode(node);
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Unexpected END-tag");
		}

		if (pushToStack) {
			stack.push(node);
		}
	}

	/**
	 * Method called after $ occurence within the tag. Should stop parsing the
	 * tag, expects a single occurence of }.
	 */
	private void stopTagParse() {
		Token token = lexer.nextToken();
		if (token.getType() == TokenType.SYMBOL && token.getValue().toString().equals("}")) {
			lexer.setState(LexerState.BASIC);
			parsingTag = null;
		} else {
			throw new SmartScriptParserException("Expected } after $");
		}
	}

	/**
	 * Parses the echo tag.
	 *
	 * @param count
	 *            the current argument count. Initiall should be set to 0.
	 */
	private void parseEcho(int count) {
		Token token;

		if (count == 0) {
			token = lexer.getToken();
		} else {
			token = lexer.nextToken();
		}

		if (token.getType() == TokenType.SYMBOL && token.getValue().toString().equals("$")) {
			stopTagParse();
			return;
		}

		if (arguments == null) {
			arguments = new ArrayList<>();
		}
		if (token.getType() == TokenType.SYMBOL && token.getValue().equals("@")) {
			arguments.add(parseFunction());
		} else {
			arguments.add(tokenToElement(token));
		}
		parseEcho(count + 1);
	}

	/**
	 * Parses the function.
	 *
	 * @return the element function
	 */
	private ElementFunction parseFunction() {
		Token token = lexer.nextToken();
		ElementFunction function = null;
		if (token.getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException(
					"Invalid function name. Got type " + token.getType() + " with value " + token.getValue());
		} else {
			function = new ElementFunction((String) token.getValue());
		}
		return function;
	}

	/**
	 * Parses the FOR-tag.
	 *
	 * @param count
	 *            the current argument count. Initially should be set to 0.
	 */
	private void parseFor(int count) {
		Token token;

		if (count == 0) {
			token = lexer.getToken();
		} else {
			token = lexer.nextToken();
		}

		if (token.getType() == TokenType.SYMBOL && token.getValue().toString().equals("$")) {
			stopTagParse();
			return;
		}

		if (arguments == null) {
			arguments = new ArrayList<>(4);
		}

		if (count > 4) {
			throw new SmartScriptParserException("Invalid argument count");
		}

		if (count == 0) {
			if (token.getType() != TokenType.VARIABLE) {
				throw new SmartScriptParserException(
						"Invalid argument type, expected variable but got: " + token.getType());
			}
		} else {
			if (token.getType() != TokenType.VARIABLE && token.getType() != TokenType.CONSTANTDOUBLE
					&& token.getType() != TokenType.CONSTANTINTEGER && token.getType() != TokenType.STRING) {
				throw new SmartScriptParserException(
						"Invalid argument (" + token.getValue() + ") type: " + token.getType());
			}
		}

		arguments.add(tokenToElement(token));

		parseFor(count + 1);
	}

	/**
	 * Generates the {@link Element} corresponding to the {@link Token} type.
	 *
	 * @param token
	 *            the token to process
	 * @return the generated element.
	 */
	private Element tokenToElement(Token token) {
		Element element = null;
		switch (token.getType()) {
		case CONSTANTDOUBLE:
			element = new ElementConstantDouble((double) token.getValue());
			break;
		case CONSTANTINTEGER:
			element = new ElementConstantInteger((int) token.getValue());
			break;
		case VARIABLE:
			element = new ElementVariable((String) token.getValue());
			break;
		case STRING:
			element = new ElementString((String) token.getValue());
			break;
		case SYMBOL:
			if (isOperator(token.getValue().toString().charAt(0))) {
				element = new ElementOperator(token.getValue().toString());
			}
			break;
		default:
			throw new SmartScriptParserException("Cannot parse " + token.getType() + ".");
		}

		return element;
	}

	/**
	 * Checks if the symbol is operator. Valid operators are +, -, *, /, and ^.
	 *
	 * @param operator
	 *            the character to check.
	 * @return true, if is operator.
	 */
	private static boolean isOperator(char operator) {
		return operator == '+' || operator == '-' || operator == '*' || operator == '/' || operator == '^';
	}
}
