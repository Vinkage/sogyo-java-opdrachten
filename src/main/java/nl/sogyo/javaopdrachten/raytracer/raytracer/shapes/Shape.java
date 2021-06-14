package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;

public interface Shape {
    Vector[] intersect(Line line) throws NoIntersectionPossible;

    Float calculateAngle(Line line, Vector nearestIntersectionPoint);

    Vector getNormal(Vector intersectionPoint);

    Vector getOrigin();

    boolean inside(Vector point);
}
