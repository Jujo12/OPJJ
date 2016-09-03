package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementString. Represents a string.
 */
public class ElementString extends Element {

	/** The encapsulated String. */
	private String value;

	/**
	 * Gets the String value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Instantiates a new element string.
	 *
	 * @param value
	 *            the value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return "\"" + value + "\"";
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementString(this);
	}
}
