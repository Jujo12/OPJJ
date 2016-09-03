package hr.fer.zemris.java.hw15.form;

import java.util.HashMap;
import java.util.Map;

/**
 * The form object wrapper.
 *
 * @author Juraj Juričić
 */
abstract class FormWrapper {
	
	/** The map of errors. */
	protected final Map<String, String> errors = new HashMap<>();

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public Map<String,String> getErrors(){
		return errors;
	}
}
