package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;

/**
 * The Class Node. Abstractly represents a single node. Actual nodes inherit this class.
 */
public abstract class Node {
	
	/** The children. */
	private ArrayList<Node> children = null;
	
	/** True if the tag is empty (like ECHO-tag), false if it is non-empty (like FOR-tag). */
	protected final boolean isEmptyTag;
	
	/**
	 * Instantiates a new node.
	 *
	 * @param isEmptyTag the is empty tag
	 */
	public Node(boolean isEmptyTag){
		this.isEmptyTag = isEmptyTag;
	}
	
	/**
	 * Instantiates a new node for an empty tag.
	 */
	public Node(){
		this(true);
	}
	
	/**
	 * Adds the child node.
	 *
	 * @param child the child
	 */
	public void addChildNode(Node child){
		if (children == null){
			children = new ArrayList<>();
		}
		
		children.add(child);
	}
	
	/**
	 * Number of children.
	 *
	 * @return the int
	 */
	public int numberOfChildren(){
		if (children == null){
			return 0;
		}
		
		return children.size();
	}
	
	/**
	 * Gets the child.
	 *
	 * @param index the index
	 * @return the child
	 */
	public Node getChild(int index){
		if (children == null){
			throw new IndexOutOfBoundsException("There are no children."); 
		}
		
		//will throw IndexOutOfBoundsException if index is out of bounds.
		return (Node)children.get(index);
	} 
	
	/**
	 * Gets the checks if is empty tag.
	 *
	 * @return the checks if is empty tag
	 */
	public boolean getIsEmptyTag(){
		return isEmptyTag;
	}
	
	/**
	 * Returns the tag as a String. Used for reconstructing the original tag. To be overriden.
	 *
	 * @return the string
	 */
	public String asString(){
		return "";
	}
	
	/**
	 * The aceept method for the Visitor pattern. Implements the given visitor {@link INodeVisitor}.
	 *
	 * @param visitor the visitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
