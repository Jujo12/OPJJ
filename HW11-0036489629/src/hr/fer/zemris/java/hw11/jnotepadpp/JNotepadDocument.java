package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * The document class used in {@link JNotepadPP}. Stores all document
 * information, such as path and modified state, as well as the textArea used
 * for the document.
 *
 * @author Juraj Juričić
 */
class JNotepadDocument {

	/** The path of the document. */
	private Path path;

	/** The editor (textarea). */
	private JTextArea editor;

	/**
	 * True if the document is changed, false if current version has been saved.
	 */
	private boolean changed = false;

	/** The JNotepadPP parent program. */
	private JNotepadPP jNotepad;

	/** The listeners for document change. */
	private Set<ChangeListener> changeListeners = new HashSet<>();

	/**
	 * Instantiates a new document for the given program jNotepad. Loads data
	 * from the given path.
	 *
	 * @param jNotepad
	 *            the JNotepadPP parent program.
	 * @param path
	 *            the path to file. If null, a blank document is created.
	 */
	public JNotepadDocument(JNotepadPP jNotepad, Path path) {
		this.jNotepad = jNotepad;
		this.path = path;

		editor = new JTextArea();
	}

	/**
	 * Initializes the text editor.
	 */
	public void initEditor() {
		editor.setFont(new Font(JNotepadPP.FONT_FAMILY, Font.PLAIN,
				JNotepadPP.FONT_SIZE));
		editor.setMargin(new Insets(5, 5, 5, 5));
		editor.setLineWrap(true);
		editor.setCaretColor(JNotepadPP.COLOR_GRAY);
		editor.setSelectionColor(JNotepadPP.COLOR_DARKGRAY);
		editor.setSelectedTextColor(JNotepadPP.COLOR_FOREGROUND);

		editor.setBackground(JNotepadPP.COLOR_BACKGROUND);
		editor.setForeground(JNotepadPP.COLOR_FOREGROUND);

		// attach to JScrollPane
		editor.getParent().getParent().addMouseWheelListener(e -> {
			if (!e.isControlDown()) {
				return;
			}

			zoomTextArea(editor, -e.getWheelRotation());
		});

		editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				textChanged(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				textChanged(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textChanged(true);
			}
		});

		if (path != null) {
			openDocument();
		}
	}

	/**
	 * Should be called before attempting to close the document. PErforms
	 * modification check and safely removes all listeners.
	 *
	 * @return true, if the document can be closed. False if the user stopped
	 *         closing.
	 */
	public boolean beforeCloseDocument() {
		if (isTextChanged()) {
			int result = JOptionPane.showConfirmDialog(jNotepad,
					LocalizationProvider.getInstance()
							.getString("filemodified"),
					LocalizationProvider.getInstance().getString("Savefile?"),
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.YES_OPTION:
				if (!save(false)) {
					// user cancelled; stop closing
					return false;
				}
				break;
			default:
				// cancelled, do nothing, ...
				return false;
			}
		}
		return true;
	}

	/**
	 * Saves the current document. Asks the user for location if current path is
	 * null or saveAs flag is set to true.
	 *
	 * @param saveAs
	 *            if true, will always ask user for location.
	 * @return true, if successful
	 */
	public boolean save(boolean saveAs) {
		Path path = (saveAs) ? null : this.getPath();

		path = jNotepad.saveAsDialog(path);
		if (path == null) {
			return false;
		}

		boolean success = false;
		try {
			success = this.saveDocument(path);
		} catch (IllegalArgumentException ex) {
			success = false;
		}

		return success;
	}

	/**
	 * Opens the document.
	 */
	private void openDocument() {
		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(jNotepad,
					"Datoteka " + path.toAbsolutePath() + " ne postoji!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(jNotepad,
					"Datoteku " + path.toAbsolutePath()
							+ " nije moguce procitati!",
					"Pogreska", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String text = new String(bytes, StandardCharsets.UTF_8);
		editor.setText(text);
		textChanged(false);
	}

	/**
	 * Saves the document to the given path..
	 *
	 * @param path
	 *            the path. Should not be null.
	 * @return true, if successful
	 */
	private boolean saveDocument(Path path) {
		Objects.requireNonNull(path);

		this.path = path;

		byte[] bytes = editor.getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(path, bytes);
		} catch (IOException e1) {
			return false;
		}

		textChanged(false);
		return true;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Modifies the changed flag and fires the notification to all listeners for
	 * change.
	 *
	 * @param changed
	 *            the changed
	 */
	private void textChanged(boolean changed) {
		this.changed = changed;
		triggerChange();
	}

	/**
	 * Notifies all change listeners about the change.
	 */
	public void triggerChange() {
		changeListeners.forEach(l -> {
			l.stateChanged(new ChangeEvent(this));
		});
	}

	/**
	 * Zooms the text area font by the given amount. Amount can be negative.
	 *
	 * @param editor
	 *            the editor
	 * @param amount
	 *            the amount, can be negative.
	 */
	private static void zoomTextArea(JTextArea editor, int amount) {
		Font currentFont = editor.getFont();
		editor.setFont(
				currentFont.deriveFont(currentFont.getSize2D() + amount));
	}

	/**
	 * Gets the editor (text area).
	 *
	 * @return the editor
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Checks if is text changed.
	 *
	 * @return true, if is text changed
	 */
	public boolean isTextChanged() {
		return changed;
	}

	/**
	 * Gets the title.
	 *
	 * @param absolutePath if true, the title will contain absolute document path
	 * @return the title of the document
	 */
	public String getTitle(boolean absolutePath) {
		String title;
		if (path == null) {
			title = LocalizationProvider.getInstance().getString("Untitled");
		} else {
			if (absolutePath) {
				title = path.toString();
			} else {
				title = path.getFileName().toString();
			}
		}

		return title;
	}

	/**
	 * Adds the change listener. Will notify all change listeners when a document content change has occured.
	 *
	 * @param l the l
	 */
	public void addChangeListener(ChangeListener l) {
		changeListeners.add(l);
	}

	/**
	 * Sets the word (line) wrapping.
	 *
	 * @param wrap if true, word wrapping will be turned on. Off otherwise.
	 */
	public void setWordWrap(boolean wrap) {
		editor.setLineWrap(wrap);
	}

	/**
	 * Gets the word wrap state.
	 *
	 * @return the word wrap
	 */
	public boolean getWordWrap() {
		return editor.getLineWrap();
	}
}
