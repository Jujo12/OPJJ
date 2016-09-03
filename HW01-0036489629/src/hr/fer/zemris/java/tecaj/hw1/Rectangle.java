package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Calculates and writes rectangle's area and perimeter. The width and height
 * can be provided as either command line arguments or through the standard
 * input.
 */
public class Rectangle {

	/**
	 * The main method that is executed when the program is run.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             File IO exception
	 */
	public static void main(String[] args) throws IOException {

		// will contain width and height
		float[] dimensions = new float[2];

		if (args.length != 2) {
			// read width and height from standard input.
			dimensions = readRectangleDimensions();
		} else {
			// got two command line arguments; let's assume they are numbers
			dimensions[0] = Float.parseFloat(args[0]);
			dimensions[1] = Float.parseFloat(args[1]);
		}

		System.out.printf(
				"You have specified a rectangle width width %f and height %f. Its area is %f and its perimeter is %f.",
				dimensions[0], dimensions[1], calculateRectangleArea(dimensions[0], dimensions[1]),
				calculateRectanglePerimeter(dimensions[0], dimensions[1]));

	}

	/**
	 * Reads the width and height of a rectangle from standard input.
	 * 
	 * @return Array containing width and height of the rectangle [width,
	 *         height]
	 * @throws IOException
	 *             File IO exception
	 */
	private static float[] readRectangleDimensions() throws IOException {
		float[] dimensions = new float[2];
		String[] names = { "Width", "Height" };

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		float num = 0;
		for (int i = 0; i < 2; i++) {
			do {
				System.out.printf(names[i] + ": ");

				line = reader.readLine();
				if (line == null) {
					break;
				}

				line = line.trim();

				if (line.isEmpty()) {
					System.err.printf("Nothing was given.%n");
				} else {
					// convert to float
					num = Float.parseFloat(line);
					if (num < 0) {
						System.err.printf("%s is negative.%n", names[i]);
					} else {
						break;
					}
				}
			} while (true);
			dimensions[i] = num;
		}

		reader.close();

		return dimensions;
	}

	/**
	 * Calculates the area of a rectangle
	 * 
	 * @param width
	 *            the width of rectangle
	 * @param height
	 *            the height of rectangle
	 * @return the rectangle's area
	 */
	private static float calculateRectangleArea(float width, float height) {
		return width * height;
	}

	/**
	 * Calculates the perimeter of a rectangle
	 * 
	 * @param width
	 *            the width of rectangle
	 * @param height
	 *            the height of rectangle
	 * @return the rectangle's perimeter
	 */
	private static float calculateRectanglePerimeter(float width, float height) {
		return width * 2 + height * 2;
	}

}