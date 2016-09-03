package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementConstantDouble. Represents a double constant.
 * 
 * @see Element
 */
public class ElementConstantDouble extends Element {

	/** The value of the element */
	private double value;

	/**
	 * Instantiates a new ElementConstantDouble with the read-only value.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Gets the double value.
	 *
	 * @return the value
	 */
	public double getValue(){
		return value;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantDouble(this);
	}	
}
