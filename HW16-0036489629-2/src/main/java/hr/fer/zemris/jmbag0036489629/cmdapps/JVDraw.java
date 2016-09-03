package hr.fer.zemris.jmbag0036489629.cmdapps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.DrawingModel;
import hr.fer.zemris.jmbag0036489629.cmdapps.components.DrawingModelImpl;
import hr.fer.zemris.jmbag0036489629.cmdapps.components.DrawingObjectListModel;
import hr.fer.zemris.jmbag0036489629.cmdapps.components.JColorArea;
import hr.fer.zemris.jmbag0036489629.cmdapps.components.JDrawingCanvas;
import hr.fer.zemris.jmbag0036489629.cmdapps.geometry.GeometricalObject;
import hr.fer.zemris.jmbag0036489629.cmdapps.listeners.ColorChangeListener;
import hr.fer.zemris.jmbag0036489629.cmdapps.painters.CirclePainter;
import hr.fer.zemris.jmbag0036489629.cmdapps.painters.FilledCirclePainter;
import hr.fer.zemris.jmbag0036489629.cmdapps.painters.LinePainter;

/**
 * The JVDraw application. Provides GUI for drawing basic vector objects using
 * lines and circles.
 *
 * @author Juraj Juričić
 */
public class JVDraw extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3670405441685514497L;

	/** The foreground color picker. */
	private JColorArea foregroundColor;

	/** The background color picker. */
	private JColorArea backgroundColor;

	/** The drawing model. */
	private DrawingModel drawingModel;

	/** The drawing canvas. */
	private JDrawingCanvas canvas;

	/** The loaded file. */
	private JDrawingFile file;

	/**
	 * Instantiates a new JVDraw window.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		initGUI();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		setVisible(true);
	}

	/**
	 * Initializes user interface (GUI).
	 */
	private void initGUI() {
		setSize(800, 600);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignorable) {
		}

		setLayout(new BorderLayout());

		addMenus();
		addToolbar();

		addCanvas();
		addObjectsList();

		addBottomComponent();
	}

	/**
	 * Adds the menus to the frame.
	 */
	private void addMenus() {
		JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("File");

		JMenuItem menuFileOpen = new JMenuItem("Open");
		JMenuItem menuFileSave = new JMenuItem("Save");
		JMenuItem menuFileSaveAs = new JMenuItem("Save As...");

		JMenuItem menuFileExport = new JMenuItem("Export");

		JMenuItem menuFileExit = new JMenuItem("Exit");

		menuFile.add(menuFileOpen);
		menuFile.add(menuFileSave);
		menuFile.add(menuFileSaveAs);
		menuFile.addSeparator();
		menuFile.add(menuFileExport);
		menuFile.addSeparator();
		menuFile.add(menuFileExit);

		menubar.add(menuFile);
		setJMenuBar(menubar);

		menuFileOpen.addActionListener(e -> {
			int response = file.askForSave();
			if (response == JOptionPane.CANCEL_OPTION) {
				return;
			}
			if (response == JOptionPane.YES_OPTION) {
				file.save(false);
			}

			JDrawingFile newFile = JDrawingFile.open(canvas);
			if (newFile != null) {
				file = newFile;
			}
		});

		menuFileSave.addActionListener(e -> file.save(false));
		menuFileSaveAs.addActionListener(e -> file.save(true));

		menuFileExport.addActionListener(e -> export());

		menuFileExit.addActionListener(e -> exit());
	}

	/**
	 * Exports the drawing as image (PNG, GIF, or JPG).
	 */
	private void export() {
		JFileChooser fc = new JFileChooser("Export...");
		fc.setFileFilter(new FileNameExtensionFilter(
				"Images (*.jpg, *.gif, *.png)", "jpg", "gif", "png"));

		int status = fc.showSaveDialog(null);
		if (status == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File file = fc.getSelectedFile();
		String extension = file.getAbsolutePath()
				.substring(file.getAbsolutePath().lastIndexOf('.') + 1);
		if (!extension.equals("jpg") && !extension.equals("gif")
				&& !extension.equals("png")) {
			JOptionPane.showMessageDialog(this,
					"Unsupported extension: " + extension);
		}

		int box_height = drawingModel.getBoundingBox().height,
				box_width = drawingModel.getBoundingBox().width;
		if (box_height == 0 || box_width == 0) {
			return;
		}
		BufferedImage image = new BufferedImage(box_width, box_height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		drawingModel.paintModel(g, drawingModel.getTopLeft(),
				drawingModel.getBoundingBox());
		g.dispose();

		try {
			ImageIO.write(image, extension, file);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"An error occurred while exporting image.");
		}
	}

	/**
	 * Exits the application. Asks the user for save before quitting.
	 */
	private void exit() {
		int response = file.askForSave();
		if (response == JOptionPane.CANCEL_OPTION) {
			return;
		}
		if (response == JOptionPane.YES_OPTION) {
			file.save(false);
		}

		System.exit(0);
	}

	/**
	 * Adds the toolbar to the frame.
	 */
	private void addToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

		foregroundColor = new JColorArea("foreground color", Color.BLACK);
		backgroundColor = new JColorArea("background color", Color.WHITE);

		JButton buttonLine = new JButton("Line");
		buttonLine.addActionListener(e -> {
			canvas.setPainter(new LinePainter());
		});

		JButton buttonCircle = new JButton("Circle");
		buttonCircle.addActionListener(e -> {
			canvas.setPainter(new CirclePainter());
		});

		JButton buttonFilledCircle = new JButton("Filled circle");
		buttonFilledCircle.addActionListener(e -> {
			canvas.setPainter(new FilledCirclePainter());
		});

		toolbar.add(foregroundColor);
		toolbar.add(backgroundColor);
		toolbar.addSeparator();
		toolbar.add(buttonLine);
		toolbar.add(buttonCircle);
		toolbar.add(buttonFilledCircle);

		ButtonGroup btGroup = new ButtonGroup();
		btGroup.add(buttonLine);
		btGroup.add(buttonCircle);
		btGroup.add(buttonFilledCircle);

		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Adds the canvas to the window.
	 */
	private void addCanvas() {
		drawingModel = new DrawingModelImpl();

		canvas = new JDrawingCanvas(foregroundColor, backgroundColor,
				drawingModel);
		drawingModel.addDrawingModelListener(canvas);

		canvas.setPainter(new LinePainter());

		add(canvas, BorderLayout.CENTER);

		file = new JDrawingFile(null, canvas);
	}

	/**
	 * Adds the objects' list to the frame.
	 */
	private void addObjectsList() {
		DrawingObjectListModel listModel = new DrawingObjectListModel(
				drawingModel);
		drawingModel.addDrawingModelListener(listModel);

		JList<GeometricalObject> objectList = new JList<>(listModel);

		objectList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					drawingModel
							.openPropertiesBox(objectList.getSelectedIndex());
					objectList.clearSelection();
				}
			}
		});
		objectList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_DELETE) {
					return;
				}

				int[] selectedIndices = objectList.getSelectedIndices();
				if (selectedIndices.length == 0) {
					return;
				}

				for (int i = selectedIndices.length - 1; i >= 0; i--) {
					listModel.removeElementAt(selectedIndices[i]);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(objectList);
		scrollPane.setPreferredSize(new Dimension(220, 0));
		add(scrollPane, BorderLayout.EAST);
	}

	/**
	 * Adds the bottom component to the frame.
	 */
	private void addBottomComponent() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel();

		ColorChangeListener labelListener = (source, oldColor, newColor) -> {
			String foregroundRGB = foregroundColor.getCurrentColorRGBString();
			String backgroundRGB = backgroundColor.getCurrentColorRGBString();

			label.setText("Foreground color: " + foregroundRGB
					+ ", background color: " + backgroundRGB);
		};

		backgroundColor.addColorChangeListener(labelListener);
		foregroundColor.addColorChangeListener(labelListener);
		labelListener.newColorSelected(null, null, null);

		panel.add(label);
		getContentPane().add(panel, BorderLayout.SOUTH);
	}

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments (accepts none)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JVDraw::new);
	}
}