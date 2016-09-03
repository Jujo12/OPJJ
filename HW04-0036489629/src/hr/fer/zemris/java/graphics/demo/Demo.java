package hr.fer.zemris.java.graphics.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.ShapeType;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.RasterViewException;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Demonstration class.
 * 
 * @author Juraj Juričić
 *
 */
public class Demo {

	/**
	 * Raster used for drawing shapes.
	 */
	private static BWRaster raster;
	/**
	 * Array of {@link GeometricShape} or nulls (null stands for FLIP command).
	 */
	private static GeometricShape[] shapes;

	/**
	 * Current index of the shapes array
	 */
	private static int shapeArrayIndex = 0;

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			createRaster(args);

			int shapeCounter = 0;
			try {
				shapeCounter = sc.nextInt();
			} catch (InputMismatchException e) {
				throw new RasterViewException("Invalid parameter type. Number of shapes expected.");
			}
			sc.nextLine();
			shapes = new GeometricShape[shapeCounter];

			for (shapeArrayIndex = 0; shapeArrayIndex < shapeCounter; shapeArrayIndex++) {
				String line = null;
				while ((line = sc.nextLine()).isEmpty()) {
					// loop
				}

				parseAndCreateShape(line);
			}

			drawShapes();

			RasterView view = new SimpleRasterView();
			view.produce(raster);
		} catch (RasterViewException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Fatal error: " + e.getMessage());
		}
	}

	/**
	 * Creates raster with width and height specified in the array.
	 * 
	 * @param args
	 *            Dimensions of the raster (one or two arguments).
	 * @throws RasterViewException
	 *             thrown if there is an invalid number of arguments or they
	 *             aren't integers.
	 */
	private static void createRaster(String[] args) throws RasterViewException {
		if (args.length < 1 || args.length > 2) {
			throw new RasterViewException("Invalid number of arguments.");
		}

		int width;
		int height;
		if (args.length == 1) {
			try {
				width = height = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				throw new RasterViewException("Invalid argument type; must be int");
			}
		} else {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new RasterViewException("Invalid argument type; must be int");
			}
		}

		raster = new BWRasterMem(width, height);
	}

	/**
	 * Parses user commands (lines) and creates the appropriate shape, inserting
	 * them into static shapes array.
	 * 
	 * @param line
	 *            User input which should be parsed.
	 * @throws RasterViewException
	 *             thrown if the command is unknown or the arguments are invalid
	 */
	private static void parseAndCreateShape(String line) throws RasterViewException {
		String[] lineArgs = line.split(" ");
		ShapeType shapeType = parseType(lineArgs);
		int params[] = parseParams(lineArgs);

		GeometricShape shape = createShape(shapeType, params);
		shapes[shapeArrayIndex] = shape;
	}

	/**
	 * Creates a GeometricShape from given parameters
	 * 
	 * @param shapeType
	 *            the shapeType of the shape to construct
	 * @param params
	 *            the array of shape parameters
	 * @return the constructed {@link GeometricShape}
	 * @throws RasterViewException
	 *             thrown if the parameter count is invalid, or a fatal error
	 *             occurs
	 */
	private static GeometricShape createShape(ShapeType shapeType, int[] params) throws RasterViewException {
		if (shapeType.getParamCount() != params.length) {
			throw new RasterViewException(
					"Invalid number of arguments (" + params.length + ") for type " + shapeType.toString());
		}

		@SuppressWarnings("unchecked")
		Class<Integer>[] constructorParamClasses = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			constructorParamClasses[i] = int.class;
		}

		GeometricShape shape = null;
		try {
			Constructor<? extends GeometricShape> constructor = shapeType.getShapeClass()
					.getDeclaredConstructor(constructorParamClasses);

			// possible argument count is 3 and 4:
			if (params.length == 3) {
				shape = (GeometricShape) constructor.newInstance(params[0], params[1], params[2]);
			} else if (params.length == 4) {
				shape = (GeometricShape) constructor.newInstance(params[0], params[1], params[2], params[3]);
			} else {
				throw new RasterViewException("Wrong setup of argument count.");
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RasterViewException("Fatal error occured during shape instantiation");
		}

		return shape;
	}

	/**
	 * Draws the shapes on raster. When encountering a null entry, switches the
	 * flip mode.
	 */
	private static void drawShapes() {
		boolean flip = false;
		for (GeometricShape shape : shapes) {
			if (shape == null) {
				if (flip) {
					raster.disableFlipMode();
				} else {
					raster.enableFlipMode();
				}
				flip = !flip;
			} else {
				shape.draw(raster);
			}
		}
	}

	/**
	 * Parses (extracts) the parameters from the line input. The arguments must
	 * be integers.
	 * 
	 * @param lineArray
	 *            the separated user line (split at space)
	 * @return int[] array containing extracted parameters
	 * @throws RasterViewException
	 *             thrown if a command parameter is not an integer.
	 */
	private static int[] parseParams(String[] lineArray) throws RasterViewException {
		int[] params = new int[lineArray.length - 1];

		for (int i = 1; i < lineArray.length; i++) {
			String s = lineArray[i];
			try {
				params[i - 1] = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				throw new RasterViewException("The shape parameters must be integers.");
			}
		}

		return params;
	}

	/**
	 * Parses (extracts) the shape type form the line input.
	 * 
	 * @param lineArray
	 *            the separated user line (split at space)
	 * @return Type of the command (enum ShapeTypes).
	 * @throws RasterViewException
	 *             thrown if the command syntax is invalid, or the type is
	 *             invalid
	 */
	private static ShapeType parseType(String[] lineArray) throws RasterViewException {
		if (lineArray.length < 1) {
			throw new RasterViewException("Invalid command syntax.");
		}
		if (lineArray[0].toUpperCase().equals("FLIP")) {
			return null;
		} else {
			try {
				return ShapeType.valueOf(lineArray[0].toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new RasterViewException("Invalid shape type.");
			}
		}
	}

}