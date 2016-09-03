package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The localized version of JMenu.
 *
 * @author Juraj Juričić
 */
public class LJMenu extends JMenu {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1512655142019312824L;

	/** The localization provider. */
	private ILocalizationProvider provider;
	
	/** The text key. */
	private String key;

	/**
	 * Instantiates a new localized menu.
	 *
	 * @param provider the provider
	 * @param key the key
	 */
	public LJMenu(ILocalizationProvider provider, String key) {
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
	 * Updates the menu text.
	 */
	private void updateText(){
		setText(provider.getString(key));
	}
}
