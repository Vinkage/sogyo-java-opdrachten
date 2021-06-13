package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator.AngleCalculator;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;

public class Sphere implements Shape {
    private Vector origin;
    private float radius;
    private static final double EPSILON = 1e-6;

    public Sphere(Vector origin, float radius) {
        this.origin = origin;
        this.radius = radius;
    }

    @Override
    public Vector[] intersect(Line line) throws NoIntersectionPossible {
        ParametricLine ray = line.parametric();
        Vector Ro = ray.getOrigin();
        Vector Rd = ray.direction();

        Vector diffSandRo = this.origin.subtract(Ro);
        float lengthFromRoTo_t = diffSandRo.dotProduct(Rd);


        Vector t = ray.getVectorOfPointOnLine(lengthFromRoTo_t);

        // switching to circle interpretation
        Vector diffSand_t = this.origin.subtract(t);
        float y = diffSand_t.getModulus();

        if (y > radius) throw new NoIntersectionPossible(ray, this);

        if (Math.abs(this.radius - y) < EPSILON) {
            Vector[] intersections = new Vector[1];
            intersections[0] = t;
            return intersections;
        } else {
            Vector[] intersections = new Vector[2];
            float x = (float) Math.sqrt(this.radius * this.radius - y * y);

            Vector intersectionPointPositiveRoot = ray.getVectorOfPointOnLine((lengthFromRoTo_t + x));
            Vector intersectionPointNegativeRoot = ray.getVectorOfPointOnLine((lengthFromRoTo_t - x));
            intersections[0] = intersectionPointPositiveRoot;
            intersections[1] = intersectionPointNegativeRoot;
            return intersections;
        }

    }

    @Override
    public Float calculateAngle(Line line, Vector nearestIntersectionPoint) {
        AngleCalculator angleCalculator = new AngleCalculator();
        return angleCalculator.calculateAngle(line, this, nearestIntersectionPoint);
    }

    @Override
    public Vector getNormal(Vector intersectionPoint) {
        Vector sphereNormal = intersectionPoint.subtract(origin).toUnitLengthAndReturn();
        return sphereNormal;
    }

    public String toString() {
        return "sphere: " +
                "Center - " + origin +
                ", Radius - " + radius;
    }

    public Vector getOrigin() {
        return origin;
    }
}
