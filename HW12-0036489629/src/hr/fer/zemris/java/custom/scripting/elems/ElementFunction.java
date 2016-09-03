package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementFunction. Represents a function. Valid function name starts
 * with @ followed by a letter and zero or more letters, digits, or underscores.
 * If function name is not valid, it is invalid.
 * 
 */
public class ElementFunction extends Element {

	/** The value of the element */
	private String name;

	/**
	 * Gets the name of the function.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Instantiates a new element function.
	 *
	 * @param name
	 *            the name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementFunction(this);
	}
}
