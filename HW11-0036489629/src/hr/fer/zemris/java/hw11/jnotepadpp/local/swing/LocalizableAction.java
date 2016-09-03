package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The Class LocalizableAction, used as a localized interface for AbstractAction.
 *
 * @author Juraj Juričić
 */
public abstract class LocalizableAction extends AbstractAction{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1304284062484946303L;
	
	/** The localization provider. */
	private ILocalizationProvider provider;
	
	/** The text key. */
	private String key;

	/**
	 * Instantiates a new localizable action.
	 *
	 * @param provider the provider
	 * @param key the key
	 */
	public LocalizableAction(ILocalizationProvider provider, String key) {
		super();
		
		this.provider = provider;
		this.key = key;
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				changed();
			}
		});
	}
	
	/**
	 * Called when the provider changed the text. Modifies the value.
	 */
	private void changed(){
		putValue(Action.NAME, provider.getString(key));
	}
}
