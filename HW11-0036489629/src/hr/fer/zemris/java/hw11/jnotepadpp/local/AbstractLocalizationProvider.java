package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements ILocalizationProvider interface and adds it the ability to
 * register, de-register and inform (fire() method) listeners. It is an abstract
 * class – it does not implement getString(...) method.
 *
 * @author Juraj Juričić
 */
public abstract class AbstractLocalizationProvider
		implements ILocalizationProvider {

	/** The list of listeners. */
	private List<ILocalizationListener> listeners = new LinkedList<>();

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all registered listeners about the change.
	 */
	protected void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}