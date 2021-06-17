package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Line;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;

public class Intersection {
    private final Vector intersectionPoint;
    private final float angleOfIntersection;
    private final Line line;
    private final Shape shape;

    public Intersection(Vector intersectionPoint, Float angleOfIntersection, Line line, Shape shape) {
        this.intersectionPoint = intersectionPoint;
        this.angleOfIntersection = angleOfIntersection;
        this.line = line;
        this.shape = shape;
    }

    public float getAngleOfIntersection() {
        return angleOfIntersection;
    }

    public Vector getPoint() {return intersectionPoint;}

    public Shape getShape() {return shape;}

    @Override
    public String toString() {
        return "Intersection{" +
                "\n\tintersectionPoint=" + intersectionPoint +
                ",\n\tangleOfIntersection=" + angleOfIntersection +
                "\n}";
    }

    public Vector getNormal() {
        return shape.getNormal(intersectionPoint);
    }
}
