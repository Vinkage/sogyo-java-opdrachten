package nl.sogyo.javaopdrachten.raytracer;

public class Sphere implements Shape {
    private Vector origin;
    private float radius;
    private static final double EPSILON = 1e-6;

    public Sphere(Vector origin, float radius) {
        this.origin = origin;
        this.radius = radius;
    }

    @Override
    public Vector[] intersect(Line line) {
        ParametricLine ray = line.parametricRepresentation();
        Vector Ro = ray.getOrigin();
        Vector Rd = ray.getDirectionVec();

        Vector diffSandRo = this.origin.subtraction(Ro);
        float lengthFromRoTo_t = diffSandRo.dotProduct(Rd);

        Vector t = ray.getVectorOfPointOnLine(lengthFromRoTo_t);

        // switching to circle interpretation
        Vector diffSand_t = this.origin.subtraction(t);
        float y = diffSand_t.getModulus();

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

    public String toString() {
        return "sphere: " +
                "Center - " + origin +
                ", Radius - " + radius;
    }
}
