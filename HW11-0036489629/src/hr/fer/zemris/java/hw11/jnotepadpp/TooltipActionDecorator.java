package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;

/**
 * Decorator that masks the action name as description. Used for tooltips in toolbars. Does not modify the original action.
 *
 * @author Juraj Juričić
 */
class TooltipActionDecorator implements Action {
	
	/** The decorated target. */
	private Action target;
	
	/** The map of masked values. */
	private Map<String, Object> values = new HashMap<>();
	
	/** The list of PropertyChangeListeners. */
	private List<PropertyChangeListener> listeners = new LinkedList<>();

	/**
	 * Instantiates a new tooltip action decorator for the given decorated target.
	 *
	 * @param target the target
	 */
	public TooltipActionDecorator(Action target) {
		this.target = target;
		values.put(SHORT_DESCRIPTION, target.getValue(NAME));

		target.addPropertyChangeListener(e -> {
			if (!e.getPropertyName().equals(NAME)) {
				listeners.forEach(l -> l.propertyChange(e));
			}

			String oldName = (String) target.getValue(NAME);
			String oldDesc = (String) values.get(SHORT_DESCRIPTION);
			values.put(SHORT_DESCRIPTION, oldName);
			values.put(NAME, "");

			listeners.forEach(l -> {
				l.propertyChange(
						new PropertyChangeEvent(this, NAME, oldName, ""));
				l.propertyChange(new PropertyChangeEvent(this,
						SHORT_DESCRIPTION, oldDesc, oldName));
			});
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.actionPerformed(e);
	}

	@Override
	public Object getValue(String key) {
		if (values.containsKey(key)) {
			return values.get(key);
		}

		return target.getValue(key);
	}

	@Override
	public void putValue(String key, Object value) {
		target.putValue(key, value);
	}

	@Override
	public void setEnabled(boolean b) {
		target.setEnabled(b);
	}

	@Override
	public boolean isEnabled() {
		return target.isEnabled();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}

}
