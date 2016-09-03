package hr.fer.zemris.java.raytracer.model;

/**
 * Represents a {@link GraphicalObject} sphere.
 *
 * @author Juraj Juričić
 */
public class Sphere extends GraphicalObject {

	/** The center of the sphere. */
	private Point3D center;

	/** The radius of the sphere. */
	private double radius;

	/** Red diffuse component. */
	private double kdr;

	/** Green diffuse component. */
	private double kdg;

	/** Blue diffuse component. */
	private double kdb;

	/** Red reflective component. */
	private double krr;

	/** Green reflective component. */
	private double krg;

	/** Blue reflective component. */
	private double krb;

	/** Lightning coefficient n for reflective component */
	private double krn;

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center
	 *            the center of the sphere
	 * @param radius
	 *            the radius of the sphere
	 * @param kdr
	 *            the red diffuse component
	 * @param kdg
	 *            the green diffuse component
	 * @param kdb
	 *            the blue diffuse component
	 * @param krr
	 *            the red reflective component
	 * @param krg
	 *            the green reflective component
	 * @param krb
	 *            the blue reflective component
	 * @param krn
	 *            the lightning coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Finds the closest intersection of the ray and the sphere. Uses the
	 * analytic solution found at <a href=
	 * "http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">
	 * http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-
	 * tracer-rendering-simple-shapes/ray-sphere-intersection</a>
	 *
	 * @param ray
	 *            the ray
	 * @return the ray intersection
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		// ray.start - O
		// (O - C)
		Point3D oSubCenter = ray.start.sub(center);

		double a = 1;
		// b = (O-C) * 2 * D
		double b = oSubCenter.scalarMultiply(2.0).scalarProduct(ray.direction);
		// c = |O-C|^2 - r^2
		double c = oSubCenter.scalarProduct(oSubCenter) - radius * radius;

		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0) {
			return null;
		}

		double discriminantRoot = Math.sqrt(discriminant);
		double lambda1 = (-b + discriminantRoot) / (2 * a);
		double lambda2 = (-b - discriminantRoot) / (2 * a);

		if (lambda1 <= 0 && lambda2 <= 0)
			return null;

		return rayIntersectionFactory(lambda1, lambda2, ray);
	}

	/**
	 * Creates a ray intersection based on given lambdas and ray. Lambdas are
	 * solutions of quadratic equation.
	 *
	 * @param lambda1
	 *            the first solution of quadratic equation
	 * @param lambda2
	 *            the second solution of quadratic equation
	 * @param ray
	 *            the ray
	 * @return the ray intersection
	 */
	private RayIntersection rayIntersectionFactory(double lambda1,
			double lambda2, Ray ray) {
		Point3D i1 = ray.start.add(ray.direction.scalarMultiply(lambda1));
		Point3D i2 = ray.start.add(ray.direction.scalarMultiply(lambda2));

		double diff1 = i1.sub(ray.start).norm();
		double diff2 = i2.sub(ray.start).norm();

		Point3D point = (diff1 < diff2) ? i1 : i2;

		boolean outer = (point.sub(center).norm() - radius) > 0;

		return new SphereRayIntersection(point, Math.min(diff1, diff2), outer);
	}

	/**
	 * The @link {@link RayIntersection} of sphere and a ray.
	 *
	 * @author Juraj Juričić
	 */
	private class SphereRayIntersection extends RayIntersection {

		/**
		 * Instantiates a new sphere ray intersection.
		 *
		 * @param point
		 *            the point
		 * @param distance
		 *            the distance
		 * @param outer
		 *            the outer
		 */
		private SphereRayIntersection(Point3D point, double distance,
				boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}
	}

}
