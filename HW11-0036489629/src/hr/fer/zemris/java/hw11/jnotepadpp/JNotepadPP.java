/*
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * The Notepad "plus plus" program. A simple text editor. Accepts no command
 * line arguments.<br>
 * <br>
 * Actually not called JNotepad++. Notepad++ got its name after the programming
 * language it is written in - C++. Hereafter, there is absolutely no reason to
 * name this application "JNotepad++". Its name is JNotepad. Period.
 *
 * @author Juraj Juričić
 */
public class JNotepadPP extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5271852429891299951L;

	/** The default font family. */
	static final String FONT_FAMILY = "Consolas";

	/** The default font size. */
	static final int FONT_SIZE = 14;

	/** The background (text area) color. */
	static final Color COLOR_BACKGROUND = new Color(39, 40, 34);

	/** The foreground (text) color. */
	static final Color COLOR_FOREGROUND = new Color(248, 248, 242);

	/** The (light) gray color. */
	static final Color COLOR_GRAY = new Color(134, 134, 134);

	/** The (dark) gray color. */
	static final Color COLOR_DARKGRAY = new Color(73, 72, 62);

	/** The program name, displayed in title bar. */
	private static final String PROGRAM_NAME = "JNotepad";

	/** The red diskette icon. Used to display modified file. */
	private static final ImageIcon DISKETTE_RED = loadIcon(
			"icons/diskette-red.png");

	/** The green diskette icon. Used to display unmodified file. */
	private static final ImageIcon DISKETTE_GREEN = loadIcon(
			"icons/diskette-green.png");

	/** The icon for File-New action. */
	private static final ImageIcon ICON_NEW = loadIcon("icons/newFile.png");
	/** The icon for File-Open action. */
	private static final ImageIcon ICON_OPEN = loadIcon("icons/openFile.png");
	/** The icon for File-Save action. */
	private static final ImageIcon ICON_SAVE = loadIcon("icons/save.png");
	/** The icon for File-Save as action. */
	private static final ImageIcon ICON_SAVEAS = loadIcon("icons/saveAs.png");
	/** The icon for File-Close action. */
	private static final ImageIcon ICON_CLOSE = loadIcon("icons/closeFile.png");
	/** The icon for File-Exit action. */
	private static final ImageIcon ICON_EXIT = loadIcon("icons/exit.png");
	/** The icon for Edit-Copy action. */
	private static final ImageIcon ICON_COPY = loadIcon("icons/copy.png");
	/** The icon for Edit-Cut action. */
	private static final ImageIcon ICON_CUT = loadIcon("icons/cut.png");
	/** The icon for Edit-Paste action. */
	private static final ImageIcon ICON_PASTE = loadIcon("icons/paste.png");
	/** The icon for View-Statistics action. */
	private static final ImageIcon ICON_STATS = loadIcon("icons/stats.png");

	/** The local JTabbedPane component. */
	private JTabbedPane tabbedPane;

	/** The currently active document (in active tab). */
	private JNotepadDocument selectedDocument;

	/** The list of all documents. */
	private List<JNotepadDocument> documents = new ArrayList<>();

	/** The localization provider. */
	private ILocalizationProvider localizationProvider = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * The array of values stored in status bar. Indexes are: 0 - length 1 - ln
	 * 2 - col 3 - sel.
	 */
	private JLabel[] statusBarValues;

	/** The status bar panel. */
	private JPanel statusBar;

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JNotepadPP::new);
	}

	/**
	 * Loads the icon from the griven path and returns the {@link ImageIcon}
	 * object.
	 *
	 * @param path
	 *            the path to the icon in PNG format.
	 * @return the image icon
	 */
	private static ImageIcon loadIcon(String path) {
		try (InputStream is = JNotepadPP.class.getResourceAsStream(path)) {
			if (is == null) {
				return null;
			}
			byte[] bytes = readAllBytes(is);

			return new ImageIcon(bytes);
		} catch (IOException ignorable) {
			// error while loading icon, ignore
		}

		return null;
	}

	/**
	 * Reads all bytes from given {@link InputStream} stream.
	 *
	 * @param is
	 *            the {@link InputStream} object
	 * @return the byte[]
	 */
	private static byte[] readAllBytes(InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
		} catch (IOException ignorable) {
			// error with writing to buffer, ignore
		}

		return buffer.toByteArray();
	}

	/**
	 * Instantiates a new windows and initializes the whole program.
	 */
	public JNotepadPP() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException ignorable) {
		}

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);

		setSize(800, 600);

		initGUI();
		setVisible(true);
	}

	/**
	 * Initializes the GUI. Sets all panels and tabs. Sets all listeners.
	 * Initializes the documents. Magic.
	 */
	private void initGUI() {
		tabbedPane = new JTabbedPane();

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		createTab(null);

		statusBarValues = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			statusBarValues[i] = new JLabel("");
		}

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() < 0
						|| tabbedPane.getSelectedIndex() >= documents.size()) {
					return;
				}

				selectedDocument = documents.get(tabbedPane.getSelectedIndex());

				JNotepadPP.this.setTitle(
						selectedDocument.getTitle(true) + " - " + PROGRAM_NAME);

				flushSelectableOnlyActions();
			}
		});

		JNotepadPP.this.setTitle(
				selectedDocument.getTitle(true) + " - " + PROGRAM_NAME);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

		createActions();
		createMenus();
		createToolbar();
		createStatusBar();

		// we can do this because languages are loaded only once for the whole
		// program; there is no garbage collector problem
		LocalizationProvider.getInstance().addLocalizationListener(() -> {
			// used for "Untitled"
			documents.forEach(doc -> doc.triggerChange());
		});
	}

	/**
	 * The amount time that the message is visible in status bar. In
	 * milliseconds.
	 */
	private static final int MESSAGE_TIME = 3000;

	/**
	 * Outputs a message to the status bar. The message will be visible for
	 * MESSAGE_TIME milliseconds.
	 *
	 * @param message
	 *            the message
	 */
	private void statusBarMessage(String message) {
		Objects.requireNonNull(message);

		JPanel tempBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tempBar.setBackground(new Color(241, 237, 237));
		tempBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		JLabel label = new JLabel(message);
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

		tempBar.add(label);

		Thread t = new Thread(() -> {
			try {
				Thread.sleep(MESSAGE_TIME);
			} catch (Exception ignorable) {
			}

			SwingUtilities.invokeLater(() -> {
				JNotepadPP.this.remove(tempBar);
				JNotepadPP.this.add(statusBar, BorderLayout.SOUTH);
			});
		});
		t.setDaemon(true);
		t.start();

		JNotepadPP.this.remove(statusBar);
		JNotepadPP.this.add(tempBar, BorderLayout.SOUTH);
	}

	/**
	 * Closes the window. Before closing, checks if any documents are modified
	 * and asks user to save. If user fails to save them, operation is
	 * interrupted.
	 */
	private void closeWindow() {
		for (int i = 0; i < documents.size(); i++) {
			JNotepadDocument doc = documents.get(i);
			if (!doc.beforeCloseDocument()) {
				return;
			}

			// remove
			tabbedPane.remove(i);
			documents.remove(i--);
		}
		if (documents.isEmpty()) {
			dispose();
		}
	}

	/**
	 * Creates a new document tab from the given path. If the path is null, an
	 * empty tab is created.
	 *
	 * @param path
	 *            the path
	 */
	private void createTab(Path path) {
		JNotepadDocument document = new JNotepadDocument(this, path);

		JScrollPane scrollPane = new JScrollPane(document.getEditor());
		scrollPane.setBorder(null);

		scrollPane.getVerticalScrollBar().setUI(new GrayScrollBarUI());
		scrollPane.getVerticalScrollBar()
				.setPreferredSize(new Dimension(12, 0));

		scrollPane.getViewport().add(document.getEditor());

		document.initEditor();

		documents.add(document);
		tabbedPane.addTab(document.getTitle(false), scrollPane);

		int newTabIndex = tabbedPane.getTabCount() - 1;

		// focus
		tabbedPane.setSelectedComponent(scrollPane);
		selectedDocument = document;

		tabbedPane.setIconAt(newTabIndex, DISKETTE_GREEN);

		document.addChangeListener(e -> {
			ImageIcon icon = document.isTextChanged() ? DISKETTE_RED
					: DISKETTE_GREEN;
			if (!tabbedPane.getIconAt(newTabIndex).equals(icon)) {
				tabbedPane.setIconAt(newTabIndex, icon);
			}

			if (!tabbedPane.getTitleAt(newTabIndex)
					.equals(document.getTitle(false))) {
				tabbedPane.setTitleAt(newTabIndex, document.getTitle(false));
			}

			if (!JNotepadPP.this.getTitle().equals(document.getTitle(false))) {
				JNotepadPP.this.setTitle(
						document.getTitle(false) + " - " + PROGRAM_NAME);
			}
		});
		document.getEditor().addCaretListener(e -> {
			flushSelectableOnlyActions();
			updateStatusBar();
		});

		flushSelectableOnlyActions();
	}

	/**
	 * Sets all the metadata for all used actions. Metadata contains info such
	 * as accelerator key, mnemonic key, and icon.
	 */
	private void createActions() {
		setActionData(newDocumentAction, "control N", KeyEvent.VK_N, ICON_NEW);
		setActionData(openDocumentAction, "control O", KeyEvent.VK_0,
				ICON_OPEN);
		setActionData(saveDocumentAction, "control S", KeyEvent.VK_S,
				ICON_SAVE);
		setActionData(saveAsDocumentAction, "control shift S", KeyEvent.VK_A,
				ICON_SAVEAS);

		setActionData(closeTabAction, "control W", KeyEvent.VK_C, ICON_CLOSE);
		setActionData(exitAction, "control Q", KeyEvent.VK_X, ICON_EXIT);

		setActionData(copyAction, "control C", KeyEvent.VK_C, ICON_COPY);
		setActionData(cutAction, "control X", KeyEvent.VK_X, ICON_CUT);
		setActionData(pasteAction, "control V", KeyEvent.VK_V, ICON_PASTE);

		setActionData(wordWrapAction, "alt W", KeyEvent.VK_W, null);
		setActionData(statisticsAction, "alt S", KeyEvent.VK_S, ICON_STATS);

		setActionData(setLanguageEnglish, null, KeyEvent.VK_E, null);
		setLanguageEnglish.putValue(Action.NAME, "English");
		setActionData(setLanguageHrvatski, null, KeyEvent.VK_H, null);
		setLanguageHrvatski.putValue(Action.NAME, "Hrvatski");
		setActionData(setLanguageDeutsch, null, KeyEvent.VK_D, null);
		setLanguageDeutsch.putValue(Action.NAME, "Deutsch");
		setActionData(setLanguageRussian, null, KeyEvent.VK_H, null);
		setLanguageRussian.putValue(Action.NAME, "Русский");
	}

	/**
	 * Sets the action data for the given action.
	 *
	 * @param action
	 *            the action
	 * @param acceleratorKeyString
	 *            the accelerator key string. Can be null.
	 * @param mnemonicKey
	 *            the mnemonic key.
	 * @param icon
	 *            the icon. Can be null.
	 */
	private void setActionData(Action action, String acceleratorKeyString,
			int mnemonicKey, ImageIcon icon) {
		Objects.requireNonNull(action);

		if (acceleratorKeyString != null) {
			action.putValue(Action.ACCELERATOR_KEY,
					KeyStroke.getKeyStroke(acceleratorKeyString));
		}
		action.putValue(Action.MNEMONIC_KEY, mnemonicKey);

		if (icon != null) {
			action.putValue(Action.SMALL_ICON, icon);
		}
	}

	/**
	 * Creates the menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		// Menu: FILE
		JMenu fileMenu = new LJMenu(localizationProvider, "Menu.file");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(exitAction));

		// Menu: EDIT
		JMenu editMenu = new LJMenu(localizationProvider, "Menu.edit");
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));

		// Menu: VIEW
		JMenu viewMenu = new LJMenu(localizationProvider, "Menu.view");
		menuBar.add(viewMenu);

		JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem(wordWrapAction);
		viewMenu.add(wordWrap);
		wordWrap.setSelected(true);
		viewMenu.add(new JMenuItem(statisticsAction));

		// Menu: LANGUAGES
		JMenu langMenu = new LJMenu(localizationProvider, "Menu.languages");
		menuBar.add(langMenu);

		ButtonGroup langGroup = new ButtonGroup();
		JRadioButtonMenuItem hrButton = new JRadioButtonMenuItem(
				setLanguageHrvatski);
		hrButton.setSelected(true);
		JRadioButtonMenuItem enButton = new JRadioButtonMenuItem(
				setLanguageEnglish);
		JRadioButtonMenuItem deButton = new JRadioButtonMenuItem(
				setLanguageDeutsch);
		JRadioButtonMenuItem ruButton = new JRadioButtonMenuItem(
				setLanguageRussian);
		langGroup.add(hrButton);
		langGroup.add(enButton);
		langGroup.add(deButton);
		langGroup.add(ruButton);
		langMenu.add(hrButton);
		langMenu.add(enButton);
		langMenu.add(deButton);
		langMenu.add(ruButton);

		// Menu: TOOLS
		JMenu toolsMenu = new LJMenu(localizationProvider, "Menu.tools");
		menuBar.add(toolsMenu);

		// Submenu: CHANGE CASE
		JMenu caseSubMenu = new LJMenu(localizationProvider,
				"Menu.tools.changeCase");
		toolsMenu.add(caseSubMenu);
		caseSubMenu.add(new JMenuItem(toUppercaseAction));
		caseSubMenu.add(new JMenuItem(toLowercaseAction));
		caseSubMenu.add(new JMenuItem(toggleCaseAction));

		// Submenu: SORT
		JMenu sortSubMenu = new LJMenu(localizationProvider, "Menu.tools.sort");
		toolsMenu.add(sortSubMenu);
		sortSubMenu.add(new JMenuItem(sortLinesAscAction));
		sortSubMenu.add(new JMenuItem(sortLinesDescAction));

		toolsMenu.add(new JMenuItem(uniqueAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates the toolbar.
	 */
	private void createToolbar() {
		JToolBar toolBar = new LJToolBar(localizationProvider, "Toolbar.file");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(new TooltipActionDecorator(newDocumentAction)));
		toolBar.add(
				new JButton(new TooltipActionDecorator(openDocumentAction)));
		toolBar.add(
				new JButton(new TooltipActionDecorator(saveDocumentAction)));
		toolBar.add(
				new JButton(new TooltipActionDecorator(saveAsDocumentAction)));
		toolBar.add(new JButton(new TooltipActionDecorator(closeTabAction)));

		toolBar.addSeparator();

		toolBar.add(new JButton(new TooltipActionDecorator(copyAction)));
		toolBar.add(new JButton(new TooltipActionDecorator(cutAction)));
		toolBar.add(new JButton(new TooltipActionDecorator(pasteAction)));

		toolBar.addSeparator();

		toolBar.add(new JButton(new TooltipActionDecorator(statisticsAction)));

		toolBar.addSeparator();

		toolBar.add(new JButton(new TooltipActionDecorator(exitAction)));

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Creates the status bar.
	 */
	private void createStatusBar() {
		statusBar = new JPanel(new GridLayout(0, 4));
		statusBar.setBackground(new Color(241, 237, 237));
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		this.add(statusBar, BorderLayout.SOUTH);

		JPanel leftBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
		JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));

		Color gray = new Color(215, 215, 215);
		leftBar.setBorder(new MatteBorder(0, 1, 0, 1, gray));
		rightBar.setBorder(new MatteBorder(0, 0, 0, 1, gray));

		JLabel length = new LJLabel(localizationProvider, "Statusbar.length");
		length.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		leftBar.add(length);
		leftBar.add(statusBarValues[0]);

		JLabel lineNum = new JLabel("Ln : ");
		lineNum.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		JLabel colNum = new JLabel("Col : ");
		colNum.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		JLabel selNum = new JLabel("Sel : ");
		selNum.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		rightBar.add(lineNum);
		rightBar.add(statusBarValues[1]);
		rightBar.add(colNum);
		rightBar.add(statusBarValues[2]);
		rightBar.add(selNum);
		rightBar.add(statusBarValues[3]);

		statusBar.add(leftBar);
		statusBar.add(rightBar);
		statusBar.add(new JPanel());
		statusBar.add(addStatusBarClock());

		updateStatusBar();
	}

	/**
	 * Creates the clock used in status bar. Returns the JPanel element that
	 * should be added to the status bar.
	 *
	 * @return the clock element (type JPanel)
	 */
	private JPanel addStatusBarClock() {
		JPanel clockBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
		clockBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		JLabel clockLabel = new JLabel("");
		clockBar.add(clockLabel);

		// update clock
		Thread t = new Thread(() -> {
			DateTimeFormatter clockFormat = DateTimeFormatter
					.ofPattern("yyyy/MM/dd HH:mm:ss");
			while (true) {
				try {
					Thread.sleep(100);
				} catch (Exception ignorable) {
				}

				if (!JNotepadPP.this.isVisible()) {
					break;
				}

				SwingUtilities.invokeLater(() -> {
					clockLabel.setText(clockFormat.format(LocalDateTime.now()));
				});
			}
		});
		t.start();

		return clockBar;
	}

	/**
	 * Updates the status bar values.
	 */
	private void updateStatusBar() {
		/*
		 * values: 0 - length 1 - ln 2 - col 3 - sel
		 */
		int len = selectedDocument.getEditor().getText().length();
		statusBarValues[0].setText(Integer.toString(len));

		int caretPos = selectedDocument.getEditor().getCaretPosition();
		int ln = (caretPos == 0) ? 1 : 0;
		for (int offset = caretPos; offset > 0;) {
			try {
				offset = Utilities.getRowStart(selectedDocument.getEditor(),
						offset) - 1;
			} catch (BadLocationException e) {
				statusBarMessage(
						LocalizationProvider.getInstance().getString("error")
								+ e.getMessage());
			}
			ln++;
		}

		int col = 0;
		try {
			col = caretPos - Utilities.getRowStart(selectedDocument.getEditor(),
					caretPos) + 1;
		} catch (BadLocationException e) {
			statusBarMessage(
					LocalizationProvider.getInstance().getString("error")
							+ e.getMessage());
		}
		int sel = selectedDocument.getEditor().getSelectionEnd()
				- selectedDocument.getEditor().getSelectionStart();

		statusBarValues[1].setText(Integer.toString(ln));
		statusBarValues[2].setText(Integer.toString(col));
		statusBarValues[3].setText(Integer.toString(sel));
	}

	/** The new document action. */
	private Action newDocumentAction = new LocalizableAction(
			localizationProvider, "Menu.file.new") {

		/**
				 * 
				 */
		private static final long serialVersionUID = -6555478208647843175L;

		@Override
		public void actionPerformed(ActionEvent e) {
			createTab(null);
		}

	};

	/** The open document action. */
	private Action openDocumentAction = new LocalizableAction(
			localizationProvider, "Menu.file.open") {
		/**
				 * 
				 */
		private static final long serialVersionUID = -7935306800483126097L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(
					JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();

			// create new tab with given path
			// unless currently opened tab is EMPTY
			if (selectedDocument.getEditor().getText().isEmpty()) {
				// remove current tab
				closeCurrentTab();
			}
			createTab(filePath);
		}
	};

	/**
	 * Creates a file chooser dialog that is used for choosing the file save
	 * location. The dialog is summoned only if the given {@link Path} argument
	 * is null.
	 *
	 * @param path
	 *            the current path. If null, will return itself
	 * @return the chosen path
	 */
	public Path saveAsDialog(Path path) {
		if (path == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save as");
			if (fc.showSaveDialog(
					JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return null;
			}
			path = fc.getSelectedFile().toPath();
		}
		return path;
	}

	/** The save document action. */
	private Action saveDocumentAction = new LocalizableAction(
			localizationProvider, "Menu.file.save") {
		/**
				 * 
				 */
		private static final long serialVersionUID = -3533968872283633368L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String key;
			if (selectedDocument.save(false)) {
				key = "save.success";
			} else {
				key = "save.error";
			}
			statusBarMessage(LocalizationProvider.getInstance().getString(key));
		}
	};

	/** The save as document action. */
	private Action saveAsDocumentAction = new LocalizableAction(
			localizationProvider, "Menu.file.saveas") {
		/**
				 * 
				 */
		private static final long serialVersionUID = -8993597152622715729L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String key;
			if (selectedDocument.save(true)) {
				key = "save.success";
			} else {
				key = "save.error";
			}
			statusBarMessage(LocalizationProvider.getInstance().getString(key));
		}
	};

	/**
	 * Closes current tab. If the document has been modified, asks the user to
	 * save.
	 */
	private void closeCurrentTab() {
		if (!selectedDocument.beforeCloseDocument()) {
			return;
		}

		documents.remove(tabbedPane.getSelectedIndex());
		tabbedPane.remove(tabbedPane.getSelectedIndex());
	}

	/** The close tab action. */
	private Action closeTabAction = new LocalizableAction(localizationProvider,
			"Menu.file.closeTab") {
		/**
				 * 
				 */
		private static final long serialVersionUID = -2038302841243439609L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (documents.size() <= 1) {
				closeWindow();
				return;
			}

			closeCurrentTab();
		}
	};

	/** The exit action. */
	private Action exitAction = new LocalizableAction(localizationProvider,
			"Menu.file.exit") {

		/**
				 * 
				 */
		private static final long serialVersionUID = -2791122272415173795L;

		@Override
		public void actionPerformed(ActionEvent e) {
			closeWindow();
		}

	};

	/** The set language to croatian action. */
	private Action setLanguageHrvatski = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7628783364274402725L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	/** The set language to english action. */
	private Action setLanguageEnglish = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1623178307979843751L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};

	/** The set language to german action. */
	private Action setLanguageDeutsch = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1928901350688265998L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/** The set language to russian action. */
	private Action setLanguageRussian = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6919022162383895114L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("ru");
		}
	};

	/**
	 * Changes the case of the selected string in current document.
	 *
	 * @param changer
	 *            the function used for case change, applied to the selected
	 *            string
	 */
	private void changeCase(Function<String, String> changer) {
		JTextArea editor = selectedDocument.getEditor();
		Document doc = editor.getDocument();

		int dot = editor.getCaret().getDot();

		int len = Math.abs(dot - editor.getCaret().getMark());
		if (len == 0) {
			return;
		}

		int offset = 0;

		offset = Math.min(editor.getCaret().getDot(),
				editor.getCaret().getMark());

		try {
			String text = doc.getText(offset, len);
			text = changer.apply(text);

			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			// something bad happened
		}

		editor.getCaret().setDot(dot);
	}

	/** The to uppercase action. */
	private Action toUppercaseAction = new LocalizableAction(
			localizationProvider, "Menu.tools.toUppercase") {
		/**
				 * 
				 */
		private static final long serialVersionUID = 3747545936132258075L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(String::toUpperCase);
		}
	};

	/** The to lowercase action. */
	private Action toLowercaseAction = new LocalizableAction(
			localizationProvider, "Menu.tools.toLowercase") {
		/**
				 * 
				 */
		private static final long serialVersionUID = 2848633760337548930L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(String::toLowerCase);
		}
	};

	/** The toggle case action. */
	private Action toggleCaseAction = new LocalizableAction(
			localizationProvider, "Menu.tools.toggleCase") {
		/**
				 * 
				 */
		private static final long serialVersionUID = 7442516788301838364L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(text -> {
				char[] chars = text.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char c = chars[i];
					if (Character.isLowerCase(c)) {
						chars[i] = Character.toUpperCase(c);
					} else if (Character.isUpperCase(c)) {
						chars[i] = Character.toLowerCase(c);
					}
				}

				return new String(chars);
			});
		}
	};

	/** The copy action. */
	private Action copyAction = new LocalizableAction(localizationProvider,
			"Menu.edit.copy") {

		/**
				 * 
				 */
		private static final long serialVersionUID = -8812301835106867406L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedDocument.getEditor().copy();
		}

	};

	/** The cut action. */
	private Action cutAction = new LocalizableAction(localizationProvider,
			"Menu.edit.cut") {

		/**
				 * 
				 */
		private static final long serialVersionUID = 5690462277286700970L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedDocument.getEditor().cut();
		}

	};

	/** The paste action. */
	private Action pasteAction = new LocalizableAction(localizationProvider,
			"Menu.edit.paste") {

		/**
				 * 
				 */
		private static final long serialVersionUID = 1679513315328315534L;

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedDocument.getEditor().paste();
		}

	};

	/** The statistics action. */
	private Action statisticsAction = new LocalizableAction(
			localizationProvider, "Menu.view.stats") {

		/**
				 * 
				 */
		private static final long serialVersionUID = 160948867775241770L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = selectedDocument.getEditor().getText();

			// calculate stats
			int charCount = text.length();

			int nonBlankCount = 0;
			int lineCount = 1;

			char c;
			for (int i = 0; i < text.length(); i++) {
				c = text.charAt(i);

				// \r\n and \n
				if (c == '\r' && text.charAt(i + 1) == '\n') {
					lineCount++;
					i++;
				} else if (c == '\n') {
					lineCount++;
				}

				if (Character.isWhitespace(c)) {
					continue;
				}

				nonBlankCount++;
			}

			String message = String.format(
					LocalizationProvider.getInstance()
							.getString("Stats.message"),
					charCount, nonBlankCount, lineCount);

			JOptionPane.showMessageDialog(JNotepadPP.this, message,
					LocalizationProvider.getInstance()
							.getString("Menu.view.stats"),
					JOptionPane.INFORMATION_MESSAGE);
		}

	};

	/** The word wrap action. */
	private Action wordWrapAction = new LocalizableAction(localizationProvider,
			"Menu.view.wordWrap") {

		/**
				 * 
				 */
		private static final long serialVersionUID = -1369903312947795622L;

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean newWrap = !documents.get(0).getWordWrap();
			documents.forEach(doc -> {
				doc.setWordWrap(newWrap);
			});
		}

	};

	/**
	 * Parses the selected lines with the given {@link Function}.
	 *
	 * @param parseFunction
	 *            the parse function, applied to every line of selected part of
	 *            the current document.
	 */
	private void parseSelectedLines(
			Function<String[], String[]> parseFunction) {
		Document doc = selectedDocument.getEditor().getDocument();
		int selectionStart = selectedDocument.getEditor().getSelectionStart();
		int selectionEnd = selectedDocument.getEditor().getSelectionEnd();

		Element startLine = Utilities.getParagraphElement(
				selectedDocument.getEditor(),
				Math.min(selectionStart, selectionEnd));
		Element endLine = Utilities.getParagraphElement(
				selectedDocument.getEditor(),
				Math.max(selectionStart, selectionEnd));

		try {
			int offset = startLine.getStartOffset();
			int length = endLine.getEndOffset() - startLine.getStartOffset();
			String text = doc.getText(offset, length);

			String[] split = text.split("\\n");

			// Strategy design pattern
			split = parseFunction.apply(split);

			StringJoiner sj = new StringJoiner(System.lineSeparator());
			for (String s : split) {
				sj.add(s);
			}

			text = sj.toString();

			doc.remove(offset, length - 1);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/** The sort lines asc action. */
	private Action sortLinesAscAction = new LocalizableAction(
			localizationProvider, "Menu.tools.sortASC") {

		/**
				 * 
				 */
		private static final long serialVersionUID = -5095953710032257522L;

		@Override
		public void actionPerformed(ActionEvent e) {
			parseSelectedLines(lines -> {
				Arrays.sort(lines, (o1, o2) -> LocalizationProvider
						.getInstance().getCollator().compare(o1, o2));
				return lines;
			});
		}

	};

	/** The sort lines desc action. */
	private Action sortLinesDescAction = new LocalizableAction(
			localizationProvider, "Menu.tools.sortDESC") {

		/**
				 * 
				 */
		private static final long serialVersionUID = 3110552899698585958L;

		@Override
		public void actionPerformed(ActionEvent e) {
			parseSelectedLines(lines -> {
				Arrays.sort(lines, (o1, o2) -> -LocalizationProvider
						.getInstance().getCollator().compare(o1, o2));
				return lines;
			});
		}

	};

	/** The unique action. */
	private Action uniqueAction = new LocalizableAction(localizationProvider,
			"Menu.tools.unique") {
		/**
				 * 
				 */
		private static final long serialVersionUID = -8423488338943740536L;

		@Override
		public void actionPerformed(ActionEvent e) {
			parseSelectedLines(lines -> {
				Set<String> linesSet = new LinkedHashSet<>();
				for (String s : lines) {
					linesSet.add(s);
				}
				return linesSet.toArray(new String[linesSet.size()]);
			});
		}
	};

	/**
	 * Flushes the enabled state of actions that should be enabled only if text
	 * is selected. This method should be added as a listener to document Caret.
	 */
	private void flushSelectableOnlyActions() {
		Action[] actions = { toUppercaseAction, toLowercaseAction,
				toggleCaseAction, sortLinesAscAction, sortLinesDescAction,
				uniqueAction };
		boolean enabled = (selectedDocument.getEditor()
				.getSelectedText() != null);

		for (Action a : actions) {
			a.setEnabled(enabled);
		}
	}

	/**
	 * The gray UI class for the scrollbar. Provides the nice, round, gray UI
	 * for the scrollbar.
	 *
	 * @author Juraj Juričić
	 */
	private class GrayScrollBarUI extends BasicScrollBarUI {

		/**
		 * Instantiates a new gray scroll bar ui.
		 */
		public GrayScrollBarUI() {
			trackHighlightColor = COLOR_GRAY;

			thumbHighlightColor = COLOR_GRAY;
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c,
				Rectangle trackBounds) {
			g.setColor(COLOR_BACKGROUND);
			g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,
					trackBounds.height);

			if (trackHighlight == DECREASE_HIGHLIGHT) {
				paintDecreaseHighlight(g);
			} else if (trackHighlight == INCREASE_HIGHLIGHT) {
				paintIncreaseHighlight(g);
			}
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c,
				Rectangle thumbBounds) {
			if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
				return;
			}

			int w = thumbBounds.width;
			int h = thumbBounds.height;

			g.translate(thumbBounds.x, thumbBounds.y);

			Graphics2D g2d = (Graphics2D) g;

			GradientPaint gp = new GradientPaint(0, 0, new Color(80, 80, 80), w,
					0, new Color(64, 64, 64));
			g2d.setPaint(gp);
			g2d.fillRoundRect(1, 1, w - 2, h - 2, w, w);

			g.translate(-thumbBounds.x, -thumbBounds.y);
		}

		@Override
		protected JButton createDecreaseButton(int orientation) {
			return createZeroButton();
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			return createZeroButton();
		}

		/**
		 * Creates the almost invisible zero button.
		 *
		 * @return the button
		 */
		private JButton createZeroButton() {
			JButton jbutton = new JButton();

			Dimension dim = new Dimension(5, 0);
			jbutton.setPreferredSize(dim);
			jbutton.setMinimumSize(dim);
			jbutton.setMaximumSize(dim);

			jbutton.setBackground(COLOR_BACKGROUND);
			jbutton.setBorder(null);
			jbutton.setOpaque(true);

			return jbutton;
		}
	}
}
