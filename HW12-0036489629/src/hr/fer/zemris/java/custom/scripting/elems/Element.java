package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class Element. Elements are used to for the representation of
 * expressions. This is the abstract class that other Elements inherit.
 */
public abstract class Element {

	/**
	 * Textual representation of the element. Returns the original structure of
	 * the element.
	 *
	 * @return the string
	 */
	public String asText() {
		return "";
	}

	/**
	 * Method that is called upon visitor's encounter of the node. Visitor's
	 * appropriate method is then called by the elements.
	 *
	 * @param visitor
	 *            the visitor
	 */
	public abstract void accept(IElementVisitor visitor);
}
