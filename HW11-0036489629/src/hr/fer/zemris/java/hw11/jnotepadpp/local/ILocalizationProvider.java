package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Object which are instances of classes that implement this interface are
 * able to give the translations for given keys.
 *
 * @author Juraj Juričić
 */
public interface ILocalizationProvider {

	/**
	 * Adds the localization listener.
	 *
	 * @param l the l
	 */
	public void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes the localization listener.
	 *
	 * @param l the l
	 */
	public void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Gets the translated string for the given key.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String getString(String key);

}