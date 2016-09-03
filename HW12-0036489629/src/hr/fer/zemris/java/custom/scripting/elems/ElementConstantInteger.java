package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementConstantInteger. Represents an integer constant.
 */
public class ElementConstantInteger extends Element {

	/** The value of the element */
	private int value;

	/**
	 * Instantiates a new ElementConstantDouble with the read-only value.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Gets the int value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantInteger(this);
	}
}
