package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.text.Collator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * LocalizationProvider is a class that is singleton; it also extends
 * AbstractLocalizationProvider. Constructor sets the language to “en” by
 * default.
 *
 * @author Juraj Juričić
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** The instance. */
	private static LocalizationProvider instance;
	
	/** The current language code. */
	private String language;
	
	/** The currently used resource bundle. */
	private ResourceBundle bundle;
	
	/** The collator. */
	private Collator collator = null;
	
	/** The locale. */
	private Locale locale = null;

	/** The default language. */
	private static final String LANG_DEFAULT = "en";

	/**
	 * Gets the single instance of LocalizationProvider.
	 *
	 * @return single instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}

		return instance;
	}

	/**
	 * Instantiates a new localization provider.
	 */
	public LocalizationProvider() {
		setLanguage(LANG_DEFAULT);
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;

		locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw11.jnotepadpp.local.Translations",
				locale);

		collator = null;

		fire();
	}

	@Override
	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "[" + key + "]";
		}
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Gets the collator for the current language.
	 *
	 * @return the collator
	 */
	public Collator getCollator() {
		if (collator == null) {
			collator = Collator.getInstance(locale);
		}

		return collator;
	}

}
