package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * The Class ForLoopNode. This {@link Node} represents a FOR-tag with its parameters.
 */
public class ForLoopNode extends Node {
	
	/** The variable. First tag argument. */
	private ElementVariable variable;
	
	/** The expression start value. Second tag argument. */
	private Element startExpression;
	
	/** The expression end value. Third tag argument. */
	private Element endExpression;
	
	/** The step expression. Fourth tag argument. Optional. */
	private Element stepExpression;

	/**
	 * Instantiates a new for loop node.
	 *
	 * @param variable the variable
	 * @param startExpression the start expression
	 * @param endExpression the end expression
	 * @param stepExpression the step expression. Optional.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		//FOR-tag is not empty.
		super(false);
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#asString()
	 */
	@Override
	public String asString(){
		String maybeStepExpression = (stepExpression == null) ? "" : stepExpression.asText() + " ";
		return "{$FOR "+variable.asText()+" "+startExpression.asText()+" "+endExpression.asText()+" "+maybeStepExpression+"$}";
	}

	@Override
	public void accept(INodeVisitor visitor){
		visitor.visitForLoopNode(this);
	}

	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
}
