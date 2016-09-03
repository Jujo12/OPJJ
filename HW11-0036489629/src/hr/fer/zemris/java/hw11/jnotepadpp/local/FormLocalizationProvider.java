package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * FormLocalizationProvider is a class derived from LocalizationProviderBridge;
 * in its constructor it registeres itself as a WindowListener to its JFrame;
 * when frame is opened, it calls connect and when frame is closed, it calls
 * disconnect.
 *
 * @author Juraj Juričić
 */
public class FormLocalizationProvider extends LocalizationProviderBridge
		implements WindowListener {

	/**
	 * Initializes a new FormLocalizationProvider
	 * 
	 * @param parent parent provider
	 * @param frame the linked form (frame)
	 */
	public FormLocalizationProvider(ILocalizationProvider parent,
			JFrame frame) {
		super(parent);
		frame.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		connect();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		disconnect();
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
