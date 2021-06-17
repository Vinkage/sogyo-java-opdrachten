package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

public interface Shape {
    Vector[] intersect(Line line) throws NoIntersectionPossible;

    Float calculateAngle(Line line, Vector nearestIntersectionPoint);

    Vector getNormal(Vector intersectionPoint);

    Vector getOrigin();

    boolean inside(Vector point);

    float getDiffuseCoefficient();
}
