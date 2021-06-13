package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

public class Line implements Shape {
    private Vector origin;
    private Vector bindingPoint;
    private static final double EPSILON = 1e-8;

    public Line(Vector origin, Vector bindingPoint) {
        this.origin = origin;
        this.bindingPoint = bindingPoint;
    }

    public ParametricLine parametric() {
        Vector diff = bindingPoint.subtract(origin);
        // System.out.println(diff);
        Vector directionUnitVec = diff.toUnitLengthAndReturn();
        return new ParametricLine(this.origin, directionUnitVec);
    }

    public Vector[] intersect(Line otherLine) {
        Vector vectorBetween = origin.subtract(otherLine.origin);
        Vector direction = parametric().direction();
        Vector otherDirection = otherLine.parametric().direction();

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
            intersectionPoint = parametric().getVectorOfPointOnLine((float) (crossProductBetweenNorm / crossProductDirectionsNorm));

        return new Vector[] {intersectionPoint};
    }

    @Override
    public Float calculateAngle(Line line, Vector nearestIntersectionPoint) {
        return null;
    }

    @Override
    public Vector getNormal(Vector intersectionPoint) {
        return null;
    }
}
