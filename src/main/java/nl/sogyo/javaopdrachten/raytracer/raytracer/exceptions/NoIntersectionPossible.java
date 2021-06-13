package nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.ParametricLine;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;


public class NoIntersectionPossible extends Throwable {
    public NoIntersectionPossible(ParametricLine ray, Shape shape) {
        super("\n\nLine: - origin " + ray.getOrigin() + " - direction " + ray.direction() + "\ndoes not intersect \n" + shape + "\n");
    }
}
