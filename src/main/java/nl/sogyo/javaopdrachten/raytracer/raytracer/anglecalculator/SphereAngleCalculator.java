package nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.ParametricLine;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Sphere;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

public class SphereAngleCalculator {
    private final ParametricLine line;
    private final Sphere sphere;
    private Vector intersectionPoint;

    public SphereAngleCalculator(ParametricLine ray, Sphere sphere, Vector intersectionPoint) {
        this.line = ray;
        this.sphere = sphere;
        intersectionPoint = intersectionPoint;
    }

    public float calculateAngle() {

        return 0f;
    }
}
