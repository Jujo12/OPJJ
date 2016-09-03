package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The localized version of JLabel.
 *
 * @author Juraj Juričić
 */
public class LJLabel extends JLabel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1254958456749081129L;

	/** The localization provider. */
	private ILocalizationProvider provider;
	
	/** The text key. */
	private String key;

	/**
	 * Instantiates a new localized JLabel.
	 *
	 * @param provider the provider
	 * @param key the key
	 */
	public LJLabel(ILocalizationProvider provider, String key) {
		super();
		
		this.provider = provider;
		this.key = key;
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				updateText();
			}
		});
	}
	
	/**
	 * Updates the text.
	 */
	private void updateText(){
		setText(provider.getString(key));
	}
}
