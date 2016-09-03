package hr.fer.zemris.jmbag0036489629.cmdapps;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.jmbag0036489629.cmdapps.components.JDrawingCanvas;

/**
 * The class represents a single JVD file. Stores path and related canvas.
 * Provides basic operations with files.
 *
 * @author Juraj Juričić
 */
public class JDrawingFile {

	/** The path to the file. Can be null (not saved yet). */
	private Path path;

	/** The canvas displaying the image. */
	private JDrawingCanvas canvas;

	/**
	 * Instantiates a new JDrawingFile.
	 *
	 * @param path
	 *            the path, can be null
	 * @param canvas
	 *            the canvas
	 */
	public JDrawingFile(Path path, JDrawingCanvas canvas) {
		this.path = path;
		this.canvas = canvas;
	}

	/**
	 * Checks if the drawing has been changed after last save.
	 *
	 * @return true, if changed
	 */
	public boolean isChanged() {
		return canvas.isChanged();
	}

	/**
	 * Asks the user for save if the file has been changed.
	 *
	 * @return @see JOptionPane
	 */
	public int askForSave() {
		if (!isChanged()) {
			return JOptionPane.NO_OPTION;
		}

		return JOptionPane.showConfirmDialog(canvas,
				"Do you want to save changes?", "Save...",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}

	/**
	 * Saves the file.
	 *
	 * @param forceNewPath
	 *            if true, a "save as" dialog will be opened.
	 */
	public void save(boolean forceNewPath) {
		if (path == null || forceNewPath) {
			// ask for path
			JFileChooser fc = new JFileChooser("");
			fc.setFileFilter(
					new FileNameExtensionFilter("JVDraw file (*.jvd)", "jvd"));

			int status = fc.showSaveDialog(null);
			if (status == JFileChooser.CANCEL_OPTION) {
				return;
			}

			if (status == JFileChooser.OPEN_DIALOG) {
				this.path = formatExtension(fc.getSelectedFile(), ".jvd");
			}
		}

		// write canvas to file
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(path.toFile())),
				StandardCharsets.UTF_8))) {
			writer.write(canvas.toString());
			canvas.setChanged(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(canvas,
					"An error occurred while writing to file: "
							+ e.getMessage());
			return;
		}
	}

	/**
	 * Formats the file path so it is guaranteed to have the provided extension.
	 *
	 * @param f the file
	 * @param extension the extension
	 * @return the formatted path
	 */
	private static Path formatExtension(File f, String extension) {
		if (f.getAbsolutePath().toLowerCase()
				.endsWith(extension.toLowerCase())) {
			return f.toPath();
		}

		return Paths.get(f.getAbsolutePath() + extension);
	}

	/**
	 * Opens the JVD file.
	 *
	 * @param canvas the canvas to load the image into
	 * @return the loaded file
	 */
	public static JDrawingFile open(JDrawingCanvas canvas) {
		// open dialog
		JFileChooser fc = new JFileChooser("");
		fc.setFileFilter(
				new FileNameExtensionFilter("JVDraw file (*.jvd)", "jvd"));

		int status = fc.showOpenDialog(null);

		if (status == JFileChooser.OPEN_DIALOG) {
			try {
				Path path = formatExtension(fc.getSelectedFile(), ".jvd");
				List<String> lines = Files.readAllLines(path,
						StandardCharsets.UTF_8);
				canvas.clear();
				canvas.loadFromLines(lines);

				JDrawingFile jdf = new JDrawingFile(path, canvas);
				return jdf;
			} catch (Exception e) {
				return null;
			}
		}

		return null;
	}
}
