package nl.sogyo.javaopdrachten.raytracer;

public interface Shape {
    public Vector[] intersect(Line line) throws NoIntersectionPossible;
}
