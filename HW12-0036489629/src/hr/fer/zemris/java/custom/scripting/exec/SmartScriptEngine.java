package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Stack;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.elems.IElementVisitor;
import hr.fer.zemris.java.custom.scripting.exec.functions.SmartScriptFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The engine used for executing smart scripts.
 *
 * @author Juraj Juričić
 */
public class SmartScriptEngine {

	/** The main document node. */
	private DocumentNode documentNode;

	/** The request context, used for outputting data. */
	private RequestContext requestContext;

	/** The multistack. Used for variable values. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** The node visitor. */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("An error occured while writing to output: "
						+ e.getMessage());
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName();

			String initialValue = node.getStartExpression().asText();
			String endValue = node.getEndExpression().asText();
			String stepValue = (node.getStepExpression().asText() != null)
					? node.getStepExpression().asText() : "1";

			multistack.push(varName, new ValueWrapper(initialValue));

			while (multistack.peek(varName).numCompare(endValue) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
				multistack.peek(varName).increment(stepValue);
			}
			multistack.pop(varName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = node.getElements();

			IElementVisitor elemVisitor = new ElemVisitor();
			for (Element el : elems) {
				el.accept(elemVisitor);
			}

			for (Object o : elemVisitor.getStack()) {
				try {
					requestContext.write(o.toString());
				} catch (IOException e) {
					System.err.println(
							"An error occured while writing to output: "
									+ e.getMessage());
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};

	/**
	 * The element visitor.
	 *
	 * @author Juraj Juričić
	 */
	private class ElemVisitor implements IElementVisitor {

		/** The element's stack */
		private Stack<Object> stack = new Stack<>();

		/** The package containing functions */
		private final static String functionsPackage = "hr.fer.zemris.java.custom.scripting.exec.functions";

		@Override
		public void visitElementConstantDouble(ElementConstantDouble elem) {
			stack.push(elem.getValue());
		}

		@Override
		public void visitElementConstantInteger(ElementConstantInteger elem) {
			stack.push(elem.getValue());
		}

		@Override
		public void visitElementString(ElementString elem) {
			stack.push(elem.getValue());
		}

		@Override
		public void visitElementFunction(ElementFunction elem) {
			SmartScriptFunction function = getFunction(elem.getName());
			if (function == null) {
				return;
			}
			try {
				try {
					function.calculate(requestContext, stack);
				} catch (EmptyStackException e) {
					requestContext.write("No value provided.");
				}
			} catch (IOException e) {
				System.err.println("An error occured while writing to output: "
						+ e.getMessage());
			}
		}

		/**
		 * Constructs a new function using class loader. Assumes functions are
		 * implemented as Classes and located in package as specified in
		 * functionsPackage variable.
		 *
		 * @param name
		 *            the function name
		 * @return the function
		 */
		private SmartScriptFunction getFunction(String name) {
			try {
				try {
					String funcName = name.substring(0, 1).toUpperCase()
							+ name.substring(1);

					Class<?> referenceToClass;
					referenceToClass = this.getClass().getClassLoader()
							.loadClass(
									functionsPackage + ".Function" + funcName);

					Object newObject = referenceToClass.newInstance();
					SmartScriptFunction function = (SmartScriptFunction) newObject;

					return function;
				} catch (Exception e) {
					requestContext
							.write("Function " + name + " not supported.");
				}
			} catch (IOException e) {
				System.err.println("An error occured while writing to output: "
						+ e.getMessage());
			}
			return null;
		}

		@Override
		public void visitElementOperator(ElementOperator elem) {
			try {
				try {
					Object first = stack.pop();
					ValueWrapper val = new ValueWrapper(first);

					switch (elem.getSymbol()) {
					case "+":
						val.increment(stack.pop());
						break;
					case "-":
						val.decrement(stack.pop());
						break;
					case "*":
						val.multiply(stack.pop());
						break;
					case "/":
						val.divide(stack.pop());
						break;
					}

					stack.push(val.getValue());
				} catch (EmptyStackException e) {
					requestContext.write("Left or right value not supplied.");
				} catch (ClassCastException e) {
					requestContext
							.write("Invalid value type: " + e.getMessage());
				}
			} catch (IOException e1) {
				System.err.println("An error occured while writing to output: "
						+ e1.getMessage());
			}
		}

		@Override
		public void visitElementVariable(ElementVariable elem) {
			stack.push(multistack.peek(elem.getName()).getValue());
		}

		@Override
		public Stack<Object> getStack() {
			return stack;
		}

	}

	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode
	 *            the document node
	 * @param requestContext
	 *            the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes the script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}