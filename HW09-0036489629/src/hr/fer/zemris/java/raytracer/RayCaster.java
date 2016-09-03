package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * The main program RayCaster. Example of a ray caster that displays two 3D
 * Spheres.
 *
 * @author Juraj Juričić
 */
public class RayCaster {

	/** The EPS for comparing double numbers. */
	private static final double EPS = 1E-6;

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Produces image data using sequential iteration.
	 *
	 * @return the @link {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D vuv = viewUp.normalize();
				Point3D eyeView = view.sub(eye).modifyNormalize();

				//zAxis not used
				Point3D yAxis = vuv
						.sub(eyeView.scalarMultiply(vuv.scalarProduct(eyeView)))
						.normalize();
				Point3D xAxis = eyeView.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view
						.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
								.sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));

						tracer(scene, Ray.fromPoints(eye, screenPoint), rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

	/**
	 * Tracer that determines the intersection of the ray and the closest object
	 * in the scene, and determines the colors for the point, if found.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray
	 * @param rgb
	 *            the rgb
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		// Default ambient light (R,G,B) = (15,15,15)
		double[] colors = { 15, 15, 15 };

		determineColorFor(scene, ray, colors,
				findClosestIntersection(scene, ray));

		rgb[0] = (short) colors[0];
		rgb[1] = (short) colors[1];
		rgb[2] = (short) colors[2];
	}

	/**
	 * Finds the closest intersection of the ray and the closest object in the
	 * scene.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray
	 * @return the intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene,
			Ray ray) {
		RayIntersection closest = null;

		for (GraphicalObject s : scene.getObjects()) {
			RayIntersection local = s.findClosestRayIntersection(ray);

			if (local == null)
				continue;
			if (closest != null
					&& closest.getDistance() < local.getDistance()) {
				continue;
			}

			closest = local;
		}

		return closest;
	}

	/**
	 * Determines the color (RGB) for the point of intersection of scene and ray.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray
	 * @param colors
	 *            the colors
	 * @param s
	 *            the intersection
	 */
	private static void determineColorFor(Scene scene, Ray ray, double[] colors,
			RayIntersection s) {
		if (s == null) {
			colors[0] = colors[1] = colors[2] = 0;
			return;
		}

		List<LightSource> lights = scene.getLights();

		for (LightSource ls : lights) {
			Point3D lightPoint = ls.getPoint();

			Ray r = Ray.fromPoints(lightPoint, s.getPoint());
			RayIntersection s2 = findClosestIntersection(scene, r);

			if (s2 == null)
				continue;

			if (lightPoint.sub(s2.getPoint()).norm() + EPS <= lightPoint
					.sub(s.getPoint()).norm()) {
				continue;
			}

			// diffusive component
			double lightNorm = lightPoint.sub(s2.getPoint()).normalize()
					.scalarProduct(s2.getNormal());
			lightNorm = (lightNorm > 0) ? lightNorm : 0;

			colors[0] += ls.getR() * s2.getKdr() * lightNorm;
			colors[1] += ls.getG() * s2.getKdg() * lightNorm;
			colors[2] += ls.getB() * s2.getKdb() * lightNorm;

			// reflective component
			Point3D normal = s2.getNormal();
			Point3D l = lightPoint.sub(s2.getPoint());
			Point3D lightProjection = normal
					.scalarMultiply(l.scalarProduct(normal));

			Point3D rP = lightProjection
					.add(lightProjection.negate().add(l).scalarMultiply(-1));
			Point3D v = ray.start.sub(s2.getPoint());
			double ang = rP.normalize().scalarProduct(v.normalize());

			if (Double.compare(ang, 0) < 0) {
				continue;
			}

			double coef = Math.pow(ang, s2.getKrn());
			colors[0] += ls.getR() * s2.getKrr() * coef;
			colors[1] += ls.getG() * s2.getKrg() * coef;
			colors[2] += ls.getB() * s2.getKrb() * coef;
		}
	}

}
