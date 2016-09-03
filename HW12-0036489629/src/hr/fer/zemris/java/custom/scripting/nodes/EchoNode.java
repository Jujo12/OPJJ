package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * The Class EchoNode. This {@link Node} represents an Echo tag with its parameters.
 */
public class EchoNode extends Node {
	
	/** The elements of the echo tag. Actually all arguments. */
	private Element[] elements;
	
	/**
	 * Instantiates a new echo node.
	 *
	 * @param elements the elements
	 */
	public EchoNode(Element[] elements){
		super();
		this.elements = elements;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#asString()
	 */
	@Override
	public String asString(){
		StringBuilder builder = new StringBuilder();
		builder.append("{$=");
		
		for (Element i : elements){
			builder.append(" "+i.asText());
		}
		
		builder.append("$}");
		
		return builder.toString();
	}

	@Override
	public void accept(INodeVisitor visitor){
		visitor.visitEchoNode(this);
	}

	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
}
