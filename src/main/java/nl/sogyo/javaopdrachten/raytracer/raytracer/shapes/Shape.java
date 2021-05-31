package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;

public interface Shape {
    public Vector[] intersect(Line line) throws NoIntersectionPossible;
}
