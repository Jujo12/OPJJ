package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The node visitor interface, used for establishing a visitor design pattern on
 * the smartscript document. Classes that implement this interface should
 * provide mechanism for visiting all the nodes in a smartscript document.
 *
 * @author Juraj Juričić
 */
public interface INodeVisitor {

	/**
	 * The method called upon encountering a text node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * The method called upon encountering a for loop node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * The method called upon encountering an echo node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * The method called upon encountering a document (top level) node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitDocumentNode(DocumentNode node);
}
