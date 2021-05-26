package nl.sogyo.javaopdrachten.raytracer;

public class Intersection {
    private Vector intersectionPoint;
    private Float angleOfIntersection;

    public Intersection(Vector intersectionPoint, Float angleOfIntersection) {
        this.intersectionPoint = intersectionPoint;
        this.angleOfIntersection = angleOfIntersection;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "intersectionPoint=" + intersectionPoint +
                ", angleOfIntersection=" + angleOfIntersection +
                '}';
    }
}
