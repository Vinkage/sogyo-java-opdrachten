package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Intersection;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator.AngleCalculator;
import nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator.LineLineAngleCalculator;

public class Line {
    private Vector origin;
    private Vector bindingPoint;
    private static final double EPSILON = 1e-8;

    public Line(Vector origin, Vector bindingPoint) {
        this.origin = origin;
        this.bindingPoint = bindingPoint;
    }

    public ParametricLine parametricRepresentation() {
        Vector diff = bindingPoint.subtract(origin);
        // System.out.println(diff);
        Vector directionUnitVec = diff.toUnitLengthAndReturn();
        return new ParametricLine(this.origin, directionUnitVec);
    }

    public Intersection intersect(Line otherLine) {
        Vector vectorBetween = origin.subtract(otherLine.origin);
        Vector direction = parametricRepresentation().getDirectionVec();
        Vector otherDirection = otherLine.parametricRepresentation().getDirectionVec();

        Vector betweenCrossproduct = otherDirection.crossProduct(vectorBetween);
        Vector directionsCrossproduct = otherDirection.crossProduct(direction);

        double crossProductDirectionsNorm = Math.abs(directionsCrossproduct.getModulus());
        double crossProductBetweenNorm = Math.abs(betweenCrossproduct.getModulus());

        Vector intersectionPoint;
        if (origin.samePoint(otherLine.origin))
            intersectionPoint = origin;
        else if (crossProductDirectionsNorm < EPSILON || crossProductBetweenNorm < EPSILON) {
            return null;
        } else
            intersectionPoint = parametricRepresentation().getVectorOfPointOnLine((float) (crossProductBetweenNorm / crossProductDirectionsNorm));

        AngleCalculator angleCalculator = new LineLineAngleCalculator(parametricRepresentation(), otherLine.parametricRepresentation());
        float angle = angleCalculator.calculateAngle();
        return new Intersection(intersectionPoint, angle);
    }
}
