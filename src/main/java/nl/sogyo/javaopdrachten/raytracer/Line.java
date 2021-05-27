package nl.sogyo.javaopdrachten.raytracer;

public class Line {
    private Vector origin;
    private Vector bindingPoint;
    private static final double EPSILON = 1e-8;

    public Line(Vector origin, Vector bindingPoint) {
        this.origin = origin;
        this.bindingPoint = bindingPoint;
    }

    public ParametricLine parametricRepresentation() {
        Vector directionUnitVec = origin.subtraction(bindingPoint).toUnitLengthAndReturn();
        // System.out.println(directionUnitVec.toString());
        return new ParametricLine(this.origin, directionUnitVec);
    }

    public Intersection intersect(Line otherLine) {
        Vector vectorBetween = origin.subtraction(otherLine.origin);
        Vector direction = parametricRepresentation().getDirectionVec();
        Vector otherDirection = otherLine.parametricRepresentation().getDirectionVec();

        Vector betweenCrossproduct = otherDirection.crossProduct(vectorBetween);
        Vector directionsCrossproduct = otherDirection.crossProduct(direction);

        double crossProductDirectionsNorm = Math.abs(directionsCrossproduct.getModulus());
        double crossProductBetweenNorm = Math.abs(betweenCrossproduct.getModulus());

        // https://math.stackexchange.com/questions/270767/find-intersection-of-two-3d-lines/271366

        Vector intersectionPoint;
        if (origin.samePoint(otherLine.origin))
            intersectionPoint = origin;
        else if (crossProductDirectionsNorm < EPSILON || crossProductBetweenNorm < EPSILON) {
            return null;
        } else if (Math.abs(directionsCrossproduct.crossProduct(betweenCrossproduct).getModulus()) < EPSILON) {
            intersectionPoint = parametricRepresentation().getVectorOfPointOnLine((float) (crossProductBetweenNorm / crossProductDirectionsNorm));
        } else
            intersectionPoint = parametricRepresentation().getVectorOfPointOnLine((float) (-crossProductBetweenNorm / crossProductDirectionsNorm));

        AngleCalculator angleCalculator = new AngleCalculator(parametricRepresentation(), otherLine.parametricRepresentation());
        Float angle = angleCalculator.calculateAngle();
        return new Intersection(intersectionPoint, angle);
    }
}
