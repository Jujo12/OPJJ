package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class DocumentNode. This {@link Node} represents the whole document. A single document may contain only one {@link DocumentNode}.
 */
public class DocumentNode extends Node {
	@Override
	public void accept(INodeVisitor visitor){
		visitor.visitDocumentNode(this);
	}
}
