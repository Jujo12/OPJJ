package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The listener interface for receiving ILocalization events.
 * The class that is interested in processing a ILocalization
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addILocalizationListener<code> method. When
 * the ILocalization event occurs, that object's appropriate
 * method is invoked.
 */
public interface ILocalizationListener {

	/**
	 * The method that is called when the localization changed.
	 */
	public void localizationChanged();
	
}