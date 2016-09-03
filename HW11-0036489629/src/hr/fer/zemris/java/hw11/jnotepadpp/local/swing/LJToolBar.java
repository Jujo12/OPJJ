package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The localized version of {@link JToolBar}.
 *
 * @author Juraj Juričić
 */
public class LJToolBar extends JToolBar {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8350763114729205960L;

	/** The localization provider. */
	private ILocalizationProvider provider;
	
	/** The text key. */
	private String key;

	/**
	 * Instantiates a new LJ tool bar.
	 *
	 * @param provider the provider
	 * @param key the key
	 */
	public LJToolBar(ILocalizationProvider provider, String key) {
		super();
		
		this.provider = provider;
		this.key = key;
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				updateName();
			}
		});
	}
	
	/**
	 * Updates the toolbar name.
	 */
	private void updateName(){
		setName(provider.getString(key));
	}
}
