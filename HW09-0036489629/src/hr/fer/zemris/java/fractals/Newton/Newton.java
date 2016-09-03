package hr.fer.zemris.java.fractals.Newton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * The Class Newton. The main program for the Newton-Raphson fractal viewer.<br>
 * Asks the user to enter roots in a+ib format, and starts the fractal viewer
 * multithreadedly.
 *
 * @author Juraj Juričić
 */
public class Newton {

	/** The polynomial. */
	private static ComplexRootedPolynomial polynomial;

	/** The derived polynomial. */
	private static ComplexPolynomial derived;

	/** The convergence threshold. */
	private static final double CONVERGENCE_THRESHOLD = 0.002;

	/** The root threshold. */
	private static final double ROOT_THRESHOLD = 0.002;

	/** The max number of iterations. */
	private static final int MAX_ITER = 10_000_000;

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		System.out.println(
				"Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println(
				"Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<>();
		try(Scanner sc = new Scanner(System.in)){
			int i = 1;
			while (true) {
				System.out.print("Root " + i++ + "> ");
				String line = sc.nextLine();
	
				if (line.toLowerCase().equals("done")) {
					if (roots.size() < 2) {
						System.err
								.println("At least two roots needed, please enter "
										+ (2 - roots.size()) + " more roots.");
						continue;
					}
	
					break;
				}
	
				try {
					Complex c = new Complex(line);
	
					roots.add(c);
				} catch (Exception e) {
					System.err.println(
							"An error occured while parsing the input. Please try again.");
				}
			}
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");

		polynomial = new ComplexRootedPolynomial(
				roots.toArray(new Complex[roots.size()]));
		derived = polynomial.toComplexPolynom().derive();

		FractalViewer.show(new NewtonRapshonProducer());
	}

	/**
	 * The computation class for Newton-Rapshon iteration-based fractals.
	 *
	 * @author Juraj Juričić
	 */
	private static class NewtonRapshonComputation implements Callable<Void> {

		/** The minimum real value. */
		double reMin;

		/** The maximum real value. */
		double reMax;

		/** The minimum imaginary value. */
		double imMin;

		/** The maximum imaginary value. */
		double imMax;

		/** The width of the image. */
		int width;

		/** The height of the image. */
		int height;

		/** The lower y bound. */
		int lowBound;

		/** The higher y bound. */
		int highBound;

		/** The data array. */
		short[] data;

		/**
		 * Instantiates a new newton rapshon computation.
		 *
		 * @param reMin
		 *            the minimum real value
		 * @param reMax
		 *            the maximum real value
		 * @param imMin
		 *            the minimum imaginary value
		 * @param imMax
		 *            the maximum imaginary value
		 * @param width
		 *            the width of the image
		 * @param height
		 *            the height of the image
		 * @param lowBound
		 *            the lower y bound
		 * @param highBound
		 *            the higher y bound
		 * @param data
		 *            the data array
		 */
		public NewtonRapshonComputation(double reMin, double reMax,
				double imMin, double imMax, int width, int height, int lowBound,
				int highBound, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.lowBound = lowBound;
			this.highBound = highBound;
			this.data = data;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Void call() throws Exception {
			int offset = lowBound * width;

			for (int y = lowBound; y <= highBound; y++) {
				for (int x = 0; x < width; x++) {

					Complex c = new Complex(
							x / (width - 1.0) * (reMax - reMin) + reMin,
							(height - 1 - y) / (height - 1.0) * (imMax - imMin)
									+ imMin);
					Complex zn = c;
					Complex zn1;

					int iter = 0;
					double delta;

					do {
						// zn1 = zn - f(zn)/f'(zn)
						zn1 = zn.sub(
								polynomial.apply(zn).divide(derived.apply(zn)));

						// |zn1-zn|
						delta = zn1.sub(zn).module();
						zn = zn1;

						iter++;
					} while (delta > CONVERGENCE_THRESHOLD && iter < MAX_ITER);

					short index = (short) polynomial.indexOfClosestRootFor(zn1,
							ROOT_THRESHOLD);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index + 1);
					}
				}
			}

			return null;
		}

	}
	

	/**
	 * The Fractal Producer that produces Newton-Raphson iteration-based
	 * fractals.
	 *
	 * @author Juraj Juričić
	 */
	private static class NewtonRapshonProducer implements IFractalProducer {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.fractals.viewer.IFractalProducer#produce(double,
		 * double, double, double, int, int, long,
		 * hr.fer.zemris.java.fractals.viewer.IFractalResultObserver)
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			int processorCount = Runtime.getRuntime().availableProcessors();
			ExecutorService pool = Executors.newFixedThreadPool(processorCount);

			List<Future<Void>> futures = new ArrayList<>();

			short[] data = new short[width * height];
			int threadCount = height / 8 * processorCount;
			int threadHeight = height / threadCount;

			for (int i = 0; i < threadCount; i++) {
				int lowBound = i * threadHeight;
				int highBound = (i + 1) * threadHeight - 1;

				if (i == threadCount - 1) {
					highBound = height - 1;
				}

				Future<Void> future = pool.submit(
						new NewtonRapshonComputation(reMin, reMax, imMin, imMax,
								width, height, lowBound, highBound, data));
				futures.add(future);
			}

			for (Future<Void> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ignorable) {
				} catch (ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			pool.shutdown();
			observer.acceptResult(data,
					(short) (polynomial.toComplexPolynom().order() + 1),
					requestNo);
		}
	}

}
