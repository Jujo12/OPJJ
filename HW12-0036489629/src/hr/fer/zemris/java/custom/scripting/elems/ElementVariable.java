package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementVariable. Represents a variable. A variable is defined by a
 * name. Valid variable name starts with a letter followed by zero or more
 * letters, digits, or underscores. If a name is not valid, it is invalid.
 */
public class ElementVariable extends Element {

	/** The name of the variable */
	private String name;

	/**
	 * Instantiates a new element variable.
	 *
	 * @param name
	 *            the name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return name;
	}

	/**
	 * Gets the name of the variable.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementVariable(this);
	}	
}
