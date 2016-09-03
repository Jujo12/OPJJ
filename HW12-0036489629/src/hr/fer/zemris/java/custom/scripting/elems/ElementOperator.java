package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementOperator. Represents an operator. Valid operators are +, -,
 * *, /, and ^ .
 */
public class ElementOperator extends Element {

	/** The symbol representing the operator. */
	private String symbol;

	/**
	 * Gets the symbol representing the operator.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Instantiates a new element operator.
	 *
	 * @param symbol
	 *            the symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementOperator(this);
	}
}
