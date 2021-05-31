package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

public class Intersection {
    private final Vector intersectionPoint;
    private final float angleOfIntersection;

    public Intersection(Vector intersectionPoint, Float angleOfIntersection) {
        this.intersectionPoint = intersectionPoint;
        this.angleOfIntersection = angleOfIntersection;
    }

    public float getAngleOfIntersection() {
        return angleOfIntersection;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "intersectionPoint=" + intersectionPoint +
                ", angleOfIntersection=" + angleOfIntersection +
                '}';
    }
}
