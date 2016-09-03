package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class TextNode. Represents a single block of text.
 */
public class TextNode extends Node {

	/** The encapsulated text this node represents. */
	private String text;

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Instantiates a new text node.
	 *
	 * @param text the text
	 */
	public TextNode(String text){
		super();
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#asString()
	 */
	@Override 
	public String asString(){
		return text;
	}

}
