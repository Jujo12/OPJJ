package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator for some other IlocalizationProvider. Serves as a bridge. Can
 * connect or disconnect from the target window.
 *
 * @author Juraj Juričić
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The parent. */
	ILocalizationProvider parent;

	/** The listener. */
	ILocalizationListener listener;

	/** The connection status. */
	private boolean connected;

	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param parent
	 *            the parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;

		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	@Override
	public String getString(String key) {
		if (!connected) {
			return null;
		}

		return parent.getString(key);
	}

	/**
	 * Connects.
	 */
	public void connect() {
		connected = true;
		parent.addLocalizationListener(listener);

		listener.localizationChanged();
	}

	/**
	 * Disconnects.
	 */
	public void disconnect() {
		connected = false;
		parent.removeLocalizationListener(listener);
	}
}
